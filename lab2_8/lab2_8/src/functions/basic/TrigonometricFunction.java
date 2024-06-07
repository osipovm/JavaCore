package functions.basic;

import functions.Function;

public class TrigonometricFunction implements Function {


    @Override
    public double getLeftDomainBorder() {
        return Double.MIN_VALUE;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getFunctionValue(double x) {
        return 0;
    }
}

