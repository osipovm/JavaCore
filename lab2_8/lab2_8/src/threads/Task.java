package threads;

import functions.Function;

public class Task {

    private Function integrableFunction;
    private double leftIn, rightIn;
    private double step;

    private int countTask;

    public Task(int countTask) {
        this.countTask = countTask;
    }


    public Function getIntegrableFunction() {
        return integrableFunction;
    }

    public void setIntegrableFunction(Function integrableFunction) {
        this.integrableFunction = integrableFunction;
    }


    public double getLeftIn() {
        return leftIn;
    }

    public void setLeftIn(double leftIn) {
        this.leftIn = leftIn;
    }

    public double getRightIn() {
        return rightIn;
    }

    public void setRightIn(double rightIn) {
        this.rightIn = rightIn;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public int getCountTask() {
        return countTask;
    }

    public void setCountTask(int countTask) {
        this.countTask = countTask;
    }






}
