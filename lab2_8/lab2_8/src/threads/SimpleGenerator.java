package threads;

import functions.Function;
import functions.Functions;
import functions.InappropriateFunctionPointException;
import functions.basic.Log;

public class SimpleGenerator implements Runnable {


    private Task taskObj;

    public SimpleGenerator(Task taskObj) {
        this.taskObj = taskObj;
    }


    public synchronized Task getTaskObj() {
        return taskObj;
    }

    public void setTaskObj(Task taskObj) {
        this.taskObj = taskObj;
    }

    @Override
    public void run() {

        for(int i = 0; i < taskObj.getCountTask(); i++){
            Function log = new Log(1 + (int) (Math.random()*10));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            taskObj.setLeftIn( (Math.random()*100));
            taskObj.setRightIn(100 + (Math.random()*200));
            taskObj.setStep(Math.random());
            taskObj.setIntegrableFunction(log);
            System.out.println("Source left: "+taskObj.getLeftIn()+", right: "+taskObj.getRightIn()+", step: "+taskObj.getStep());


        }
    }

}
