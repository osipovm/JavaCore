package threads;

import functions.Functions;
import functions.InappropriateFunctionPointException;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {

    private Task taskObj;
    Semaphore semaphoreIntegrator;


    public Integrator(Task taskObj, Semaphore semaphoreIntegrator){
        this.taskObj = taskObj;
        this.semaphoreIntegrator = semaphoreIntegrator;
    }

    public void setTaskObj(Task taskObj) {
        this.taskObj = taskObj;
    }

    @Override
    public void run() {
        for(int i = 0; i < taskObj.getCountTask(); i++){
            try {

                semaphoreIntegrator.acquire();
                System.out.println("Results left: "+taskObj.getLeftIn()+", right: "+taskObj.getRightIn()+", " +
                        "step: "+taskObj.getStep()+", integral: "+
                        Functions.integralOfFunction(taskObj.getIntegrableFunction(),taskObj.getLeftIn(),taskObj.getRightIn(),taskObj.getStep()));

                semaphoreIntegrator.release();

            } catch (InappropriateFunctionPointException | InterruptedException e) {
                e.printStackTrace();
            }



        }
    }
}
