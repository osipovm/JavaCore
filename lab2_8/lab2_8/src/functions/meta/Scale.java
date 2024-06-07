package functions.meta;

import functions.Function;

public class Scale implements Function {

    private Function function;
    private double coefX, coefY;

    public Scale(Function function, double coefX, double coefY){
        this.function = function;
        this.coefX = coefX;
        this.coefY = coefY;
    }


    @Override
    public double getLeftDomainBorder() {
        return coefX*function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return coefX*function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return coefY*function.getFunctionValue(x*coefX);
    }
}
