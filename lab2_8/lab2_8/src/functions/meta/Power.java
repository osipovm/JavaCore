package functions.meta;

import functions.Function;

public class Power implements Function {


    private Function function;
    private double pow;

    public Power(Function function, double pow){
        this.function = function;
        this.pow = pow;
    }



    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(function.getFunctionValue(x),pow);
    }
}
