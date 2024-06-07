package functions.meta;

import functions.Function;

public class Composition implements Function {

    private Function superFunction, compFunction;

    public Composition(Function superFunction, Function compFunction){
        this.superFunction = superFunction;
        this.compFunction = compFunction;
    }

    @Override
    public double getLeftDomainBorder() {
        return superFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return superFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return superFunction.getFunctionValue(compFunction.getFunctionValue(x));
    }
}
