package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {

    private double x, y;


    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString(){
        return "("+getX()+";"+getY()+")";
    }

    public boolean equals(Object o){
        if( o instanceof FunctionPoint){
            FunctionPoint temp = (FunctionPoint) o;
            if((Math.abs(temp.getX() - this.getX())<0.001) && (Math.abs(temp.getY() - this.getY())<0.001)) return true;
        }
        return false;
    }

    public int hashCode(){
        int result;
        long doubleAsLongX = Double.doubleToLongBits(x);
        long doubleAsLongY = Double.doubleToLongBits(y);
        result = (int) (doubleAsLongX ^ (doubleAsLongX>>>32));
        result = 31*result + (int) (doubleAsLongY ^ (doubleAsLongY>>>32));
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

