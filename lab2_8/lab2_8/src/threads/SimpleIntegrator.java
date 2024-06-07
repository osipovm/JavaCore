package threads;

import functions.Function;
import functions.Functions;
import functions.InappropriateFunctionPointException;
import functions.basic.Log;

public class SimpleIntegrator implements Runnable{


    private Task task;
    private SimpleGenerator simpleGenerator;

    public SimpleIntegrator(SimpleGenerator simpleGenerator) {
        this.simpleGenerator = simpleGenerator;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for(int i = 0; i < simpleGenerator.getTaskObj().getCountTask(); i++){

            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                return;
            }

            try {
                System.out.println("Results left: "+simpleGenerator.getTaskObj().getLeftIn()+", right: "+simpleGenerator.getTaskObj().getRightIn()+", step: "+simpleGenerator.getTaskObj().getStep()+", integral: "+ Functions.integralOfFunction(simpleGenerator.getTaskObj().getIntegrableFunction(),simpleGenerator.getTaskObj().getLeftIn(),simpleGenerator.getTaskObj().getRightIn(),simpleGenerator.getTaskObj().getStep()));
            } catch (InappropriateFunctionPointException e) {
                e.printStackTrace();
            }

        }

    }
}
