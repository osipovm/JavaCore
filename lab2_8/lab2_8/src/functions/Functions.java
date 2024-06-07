package functions;

import functions.meta.*;

public class Functions {




    private Functions(){

    }

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f,shiftX,shiftY);
    }

	public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f,scaleX,scaleY);
    }

	public static Function power(Function f, double power){
        return new Power(f,power);
    }
	public static Function sum(Function f1, Function f2){
        return new Sum(f1,f2);
    }

	public static Function mult(Function f1, Function f2){
        return new Mult(f1,f2);
    }

	public static Function composition(Function f1, Function f2){
        return new Composition(f1,f2);
    }

    public static double integralOfFunction(Function f,double leftIn, double rightIn, double step) throws InappropriateFunctionPointException {
        if(leftIn < f.getLeftDomainBorder() || rightIn>f.getRightDomainBorder()) throw new InappropriateFunctionPointException();

        double integralValue = 0;

        double stepValue = leftIn;
        while(stepValue + step < rightIn){

             integralValue += (step)*(f.getFunctionValue(stepValue) + f.getFunctionValue(stepValue+step))/2;
             stepValue += step;
        }
        integralValue += (rightIn-stepValue)*(f.getFunctionValue(stepValue) + f.getFunctionValue(rightIn))/2;
        return integralValue;
    }
}
