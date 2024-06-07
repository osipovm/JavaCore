package threads;

import functions.Function;
import functions.basic.Log;

import java.util.concurrent.Semaphore;

public class Generator extends Thread{

    private Task taskObj;
    Semaphore semaphoreGenerator;


    public Task getTaskObj() {
        return taskObj;
    }

    public Generator(Task task, Semaphore semaphore){
        this.taskObj = task;
        this.semaphoreGenerator = semaphore;
    }

    @Override
    public void run() {

        taskObj.setIntegrableFunction(new Log((int) (Math.random() * 9) + 1));
        for(int i = 0; i < taskObj.getCountTask(); i++){

            try {
                semaphoreGenerator.acquire();


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            taskObj.setLeftIn( (Math.random()*100));
            taskObj.setRightIn(100 + (Math.random()*200));
            taskObj.setStep(Math.random());
            System.out.println("Source left: "+taskObj.getLeftIn()+", right: "+taskObj.getRightIn()+", step: "+taskObj.getStep());
            semaphoreGenerator.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
