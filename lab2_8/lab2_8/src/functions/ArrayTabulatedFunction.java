package functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {


    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < pointMassive.length;
            }

            @Override
            public FunctionPoint next() {
                if(i < pointMassive.length)
                    return pointMassive[i++];
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

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {
        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount)
                throws InappropriateFunctionPointException, IllegalAccessException {
            return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values)
                throws InappropriateFunctionPointException, IllegalAccessException {
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] functionPoints) throws IllegalAccessException {
            return new ArrayTabulatedFunction(functionPoints);
        }
    }



    private FunctionPoint[] pointMassive;
    private static final long serialVersionUID = 1L;



   public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalAccessException {
       if(leftX >= rightX && pointsCount<2) throw new IllegalAccessException();
        pointMassive = new FunctionPoint[pointsCount];
        this.leftX = leftX;
        this.rightX = rightX;
        double t = leftX;
        int i = 0;
        while(i < pointsCount){

           t += (rightX - leftX) / (pointsCount+1);
           pointMassive[i] = new FunctionPoint(t,0);
           System.out.print(pointMassive[i].getX()+" ");
           i++;
        }
       System.out.println();
    }

   public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalAccessException {
       if(leftX >= rightX && values.length<2) throw new IllegalAccessException();
       pointMassive = new FunctionPoint[values.length];
       this.leftX = leftX;
       this.rightX = rightX;
       double t = leftX;
       int i = 0;
       while(i < values.length){

           pointMassive[i] = new FunctionPoint(t,values[i]);
           //System.out.println(pointMassive[i].getY());
           t += (rightX - leftX) / (values.length-1);

           i++;
           //System.out.print(t+ " ");

       }

       System.out.println();


    }

    public ArrayTabulatedFunction(FunctionPoint[] pointMassive) throws IllegalAccessException{
        if(pointMassive.length < 2) throw new IllegalArgumentException();

        int i = 1;
        while(i < pointMassive.length){
            if(pointMassive[i].getX() < pointMassive[i-1].getX()) throw new IllegalArgumentException();
            System.out.print(pointMassive[i-1].getX()+" ");
            i++;
        }
        System.out.print(pointMassive[i-1].getX()+" ");
        this.pointMassive = pointMassive;
    }



   private double leftX, rightX;

    public double getLeftDomainBorder(){
      return pointMassive[0].getX();
    }

    public double getRightDomainBorder(){
        return pointMassive[pointMassive.length-1].getX();
    }

    public double getFunctionValue(double x) {

        if (x > leftX && x < rightX && x != leftX) {
            for (int i = 1; i < pointMassive.length; i++) {
                if ((x < pointMassive[i].getX()) && (x > pointMassive[i-1].getX())) {
                    return pointMassive[i-1].getY() + ((x - pointMassive[i-1].getX())*(pointMassive[i].getY() - pointMassive[i-1].getY())/
                            (pointMassive[i].getX() - pointMassive[i-1].getX()) );
                } else if (x == pointMassive[i-1].getX()) {
                    return pointMassive[i-1].getY();
                }

            }
        }
        else if(x == leftX){
            return pointMassive[0].getY();
        }
        else if(x== rightX){
            return pointMassive[pointMassive.length-1].getY();
        }

        return  Double.NaN;
    }

    public int getPointsCount(){
        return pointMassive.length;
    }


    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index > (pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();

        return pointMassive[index];
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        if(point.getX() > leftX && point.getX() < rightX){
            pointMassive[index] = point;
        }
        else throw new InappropriateFunctionPointException();
    }

    public double getPointX(int index){
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        return pointMassive[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        if(x > leftX && x < rightX){
            pointMassive[index].setX(x);
        }
        else throw new InappropriateFunctionPointException();
    }

    public double getPointY(int index){
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        return pointMassive[index].getY();
    }

    public void setPointY(int index, double y){
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        pointMassive[index].setY(y);
    }

    @Override
    public void printAll() {
        int i = 0;
        while(i < pointMassive.length){
            System.out.print(pointMassive[i]+ " ");
        }
    }

    public void deletePoint(int index){
        if(index >(pointMassive.length) || index < 0 ) throw new FunctionPointIndexOutOfBoundsException();
        if(pointMassive.length < 3) throw new IllegalStateException();
        FunctionPoint[] tempMassive = pointMassive;
        pointMassive = new FunctionPoint[pointMassive.length- 1];
        int j = 0;
        for(int i = 0; i < pointMassive.length; i++){
            if(i != index){
                pointMassive[i] = tempMassive[j];
            }
            else{
                j++;
            }
            j++;
        }
        tempMassive = null;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {


        if (point.getX() > leftX && point.getX() < rightX) {
            for (int i = 1; i < pointMassive.length; i++) {

                if ((point.getX() < pointMassive[i].getX()) && (point.getX() > pointMassive[i - 1].getX())) {
                    FunctionPoint[] tempMassive = pointMassive;
                    pointMassive = new FunctionPoint[pointMassive.length+ 1];

                    Arrays.fill(pointMassive, new FunctionPoint());
                    System.arraycopy(tempMassive,0, pointMassive,0, i);
                    pointMassive[i] = point;
                    System.arraycopy(tempMassive, i,pointMassive,i+1,tempMassive.length-i);
                    tempMassive = null;
                    break;



                } else if (point.getX() == pointMassive[i - 1].getX()) {
                    throw new InappropriateFunctionPointException();
                    //break;


                }

            }
        }


    }


    public String toString(){
        String str = "{";
        for (FunctionPoint i : pointMassive){
            str += "("+i.getX()+"; "+i.getY()+"), ";
        }
        str = str.substring(0,str.length()-1);
        str += "}";
        return str;
    }

    public boolean equals(Object o) {

        if (o instanceof ArrayTabulatedFunction){
            ArrayTabulatedFunction arrayTabulatedFunction = (ArrayTabulatedFunction) o;
            return arrayTabulatedFunction.equals(this);
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
        for (FunctionPoint i : pointMassive){
            long doubleAsLongX = Double.doubleToLongBits(i.getX());
            long doubleAsLongY = Double.doubleToLongBits(i.getY());
            result = 31*result + (int) (doubleAsLongX ^ (doubleAsLongX>>>32));
            result = 31*result + (int) (doubleAsLongY ^ (doubleAsLongY>>>32));
        }
        result += 31*result + pointMassive.length;
        return result;
    }

    public Object clone() throws CloneNotSupportedException{
        ArrayTabulatedFunction newArrTab = (ArrayTabulatedFunction) super.clone();
        newArrTab.pointMassive = new FunctionPoint[getPointsCount()];
        int j = 0;
        for(FunctionPoint i : pointMassive){
            newArrTab.pointMassive[j] = i;
            j++;
        }
        newArrTab.rightX = this.rightX;
        newArrTab.leftX = this.leftX;
        return newArrTab;

    }


}
