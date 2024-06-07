package functions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkedListTabulatedFunction implements TabulatedFunction, Externalizable {


    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalAccessException {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalAccessException {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] functionPoints) throws IllegalAccessException {
            return new LinkedListTabulatedFunction(functionPoints);
        }
    }

    private double rightX;
    private double leftX;
    private FunctionNode head;
    private FunctionPoint[] pointsMassive;

    private int size = 0;

    public LinkedListTabulatedFunction() {
        head = new FunctionNode();
        head.setNext(head);
        head.setPrev(head);
    }


    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalAccessException {
        if (leftX >= rightX && pointsCount < 2) throw new IllegalAccessException();
        head = new FunctionNode();
        FunctionNode temp = new FunctionNode();
        head.setNext(temp);
        temp.setPrev(head);
        this.leftX = leftX;
        this.rightX = rightX;
        double t = leftX;
        int i = 0;
        while (i < pointsCount) {
            t += (rightX - leftX) / (pointsCount + 1);
            temp.setPoint(new FunctionPoint(t, 0));
            temp.setNext(new FunctionNode());
            temp.getNext().setPrev(temp);
            System.out.print(temp.getPoint().getX() + " ");
            temp = temp.getNext();

            size++;
            i++;
        }


        System.out.print(temp.getPoint().getX() + " ");
        temp.getPrev().setNext(head);
        head.setPrev(temp.getPrev());

        System.out.println(head.getPoint().getX());
    }


    public LinkedListTabulatedFunction(FunctionPoint[] pointsMassive) throws IllegalAccessException{
        if(pointsMassive.length < 2) throw new IllegalArgumentException();




        head = new FunctionNode();
        FunctionNode temp = new FunctionNode();
        head.setNext(temp);
        temp.setPrev(head);
        this.leftX = pointsMassive[0].getX();
        this.rightX = pointsMassive[pointsMassive.length-1].getX();


        int i = 0;
        while (i < pointsMassive.length) {

            temp.setPoint(new FunctionPoint(pointsMassive[i].getX(), pointsMassive[i].getY()));
            temp.setNext(new FunctionNode());
            temp.getNext().setPrev(temp);
            temp = temp.getNext();
            size++;
            i++;
        }
        temp.getPrev().setNext(head);
        head.setPrev(temp.getPrev());


    }







    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalAccessException {

        if (leftX >= rightX && values.length < 2) throw new IllegalAccessException();
        //pointMassive = new FunctionPoint[values.length];

        this.leftX = leftX;
        this.rightX = rightX;
        head = new FunctionNode();
        FunctionNode temp = new FunctionNode();
        head.setNext(temp);
        temp.setPrev(head);
        double t = leftX;
        int i = 0;
        while (i < values.length) {
            t += (rightX - leftX) / (values.length + 1);
             //pointsMassive[i] = new FunctionPoint(t,values[i]);
            //i++;
            //System.out.print(t+ " ");

            temp.setPoint(new FunctionPoint(t, values[i]));
            temp.setNext(new FunctionNode());
            temp.getNext().setPrev(temp);
           // System.out.print(temp.getPoint().getX() + " ");
            temp = temp.getNext();

            size++;
            i++;

        }
        temp.getPrev().setNext(head);
        head.setPrev(temp.getPrev());

    }


    public double getLeftDomainBorder() {
        return head.getNext().getPoint().getX();
    }

    public double getRightDomainBorder() {
        return head.getPrev().getPoint().getX();
    }


    public double getFunctionValue(double x) {

        FunctionNode cur = head.getNext();
        if (x > leftX && x < rightX && x != leftX) {
            for (int i = 1; i < size; i++) {
                cur = cur.getNext();
                FunctionPoint functionPoint = cur.getPoint();
                FunctionPoint preFunctionPoint = cur.getPrev().getPoint();
                if ((x < functionPoint.getX()) && (x > preFunctionPoint.getX())) {
                    return preFunctionPoint.getY() + ((x - preFunctionPoint.getX())
                            * (functionPoint.getY() - preFunctionPoint.getY()) /
                            (functionPoint.getX() - preFunctionPoint.getX()));
                } else if (x == preFunctionPoint.getX()) {
                    return preFunctionPoint.getY();
                }
            }
        } else if (x == leftX) {
            return leftX;
        }

        return Double.NaN;
    }

    public int getPointsCount() {
        return size;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();

        return getNodeByIndex(index).getPoint();
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();
        if (point.getX() > leftX && point.getX() < rightX) {
            getNodeByIndex(index).setPoint(point);
        } else throw new InappropriateFunctionPointException();
    }


    public double getPointX(int index) {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).getPoint().getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();
        if (x > leftX && x < rightX) {
            getNodeByIndex(index).getPoint().setX(x);
        } else throw new InappropriateFunctionPointException();
    }

    public double getPointY(int index) {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).getPoint().getY();
    }

    public void setPointY(int index, double y) {
        if (index > (size) || index < 0) throw new FunctionPointIndexOutOfBoundsException();
        getNodeByIndex(index).getPoint().setY(y);
    }


    public FunctionNode addNodeToTail() {

        FunctionNode temp = new FunctionNode();


        if (head.getNext() == head) {
            temp.setPrev(head);
            temp.setNext(head);
            head.setNext(temp);
            head.setPrev(temp);
        } else {
            temp.setNext(head);
            temp.setPrev(head.getPrev());
            head.getPrev().setNext(temp);
            head.setPrev(temp);
        }
        size++;


        return temp;
    }


    public FunctionNode addNodeByIndex(int index) {

        FunctionNode cur = head.getNext();
        int i = 0;

        if (size >= index) {
            while (cur.getNext() != head && i != index) {
                cur = cur.getNext();
                i++;
            }


            FunctionNode temp = new FunctionNode();
            temp.setNext(cur);
            temp.setPrev(cur.getPrev());
            cur.getPrev().setNext(temp);
            cur.setPrev(temp);
            size++;
            return temp;
        }

        return null;
    }


    FunctionNode getNodeByIndex(int index) {
        FunctionNode cur = head.getNext();
        int i = 0;

        while (cur.getNext() != head && i != index) {
            cur = cur.getNext();
            i++;
        }

        return cur;
    }

    public FunctionNode deleteNodeByIndex(int index) {

        FunctionNode cur = getNodeByIndex(index);
        FunctionNode temp = cur;
        cur.getPrev().setNext(cur.getNext());
        cur.getNext().setPrev(cur.getPrev());
        cur.setNext(null);
        cur.setPrev(null);

        return temp;
    }

    public void printAll() {
        FunctionNode cur = head;

        System.out.println();
        while (cur.getNext() != head) {

            System.out.print(cur.getNext().getPoint().getX() + " ");
            cur = cur.getNext();

        }

    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {

        System.out.println(point.getX()+" ");
        if (point.getX() > leftX && point.getX() < rightX) {

            FunctionNode cur = head.getNext();
            for (int i = 1; i < size; i++) {

                cur = cur.getNext();
                if ((point.getX() < cur.getPoint().getX()) && (point.getX() > cur.getPrev().getPoint().getX())) {
                    FunctionNode temp = new FunctionNode(point);
                    temp.setPrev(cur.getPrev());
                    temp.setNext(cur);
                    cur.getPrev().setNext(temp);
                    cur.setPrev(temp);
                    break;

                } else if (point.getX() == cur.getPrev().getPoint().getX()) {
                    throw new InappropriateFunctionPointException();

                }

            }
        }
        else throw  new FunctionPointIndexOutOfBoundsException();

    }

    @Override
    public void deletePoint(int index) {
        if(index >(size) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        if(size < 3) throw new IllegalStateException();
        deleteNodeByIndex(index);
        size--;
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.pointsMassive);
        out.writeObject(this.getLeftDomainBorder());
        out.writeObject(this.getRightDomainBorder());
        out.writeObject(this.getPointsCount());


    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        pointsMassive = (FunctionPoint[]) in.readObject();
        leftX = (double) in.readObject();
        rightX = (double) in.readObject();
        size = (int) in.readObject();

    }

    public String toString(){

        FunctionNode cur = head;

        String str = "{";

        while (cur.getNext() != head) {

            str +="("+ cur.getNext().getPoint().getX() + "; "+cur.getNext().getPoint().getY()+"), ";
            cur = cur.getNext();

        }

        str = str.substring(0,str.length()-1);
        str += "}";
        return str;
    }

    public boolean equals(Object o) {

        if (o instanceof LinkedListTabulatedFunction){
            LinkedListTabulatedFunction linkedListTabulatedFunction = (LinkedListTabulatedFunction) o;
            return linkedListTabulatedFunction.equals(this);
        }
        else if (o instanceof TabulatedFunction) {
            TabulatedFunction tabulatedFunction = (TabulatedFunction) o;
            for (int i = 0; i < tabulatedFunction.getPointsCount(); i++) {
                if ((Math.abs(tabulatedFunction.getPointX(i) - getPointX(i)) > 0.01)
                        || (Math.abs(tabulatedFunction.getPointY(i) - this.getPointY(i)) > 0.01)) return false;
            }
            return true;
        }
        return false;
    }


    public int hashCode(){
        int result = 0;
        for (FunctionPoint i : pointsMassive){
            long doubleAsLongX = Double.doubleToLongBits(i.getX());
            long doubleAsLongY = Double.doubleToLongBits(i.getY());
            result = 31*result + (int) (doubleAsLongX ^ (doubleAsLongX>>>32));
            result = 31*result + (int) (doubleAsLongY ^ (doubleAsLongY>>>32));
        }
        result += 31*result + pointsMassive.length;
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LinkedListTabulatedFunction newLinked = (LinkedListTabulatedFunction) super.clone();

        newLinked.head = new FunctionNode();
        FunctionNode curClone = new FunctionNode();

        if(head.getNext() != head) {
            newLinked.head.setNext(curClone);
            curClone.setPrev(newLinked.head);
        }

        FunctionNode cur = head;
        while (cur.getNext() != head) {

            FunctionNode temp;
            curClone.setNext(new FunctionNode(cur.getNext().getPoint()));
            temp = curClone;
            curClone = curClone.getNext();
            curClone.setPrev(temp);

            cur = cur.getNext();
        }

        curClone.setNext(newLinked.head);


        return newLinked;
    }


    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < pointsMassive.length;
            }

            @Override
            public FunctionPoint next() {
                if(i < pointsMassive.length)
                    return pointsMassive[i++];
                else {
                    i = 0;
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public void forEach(Consumer<? super FunctionPoint> action) {
        TabulatedFunction.super.forEach(action);

    }

    @Override
    public Spliterator<FunctionPoint> spliterator() {
        return TabulatedFunction.super.spliterator();
    }
}

