package functions.basic;

import functions.Function;

public class Log implements Function {

   private double logBase;

    public Log(double logBase){
        this.logBase = logBase;
    }


    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x)/Math.log(logBase);
    }
}

