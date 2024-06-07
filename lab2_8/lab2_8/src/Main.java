
import functions.*;
import functions.basic.*;
import functions.meta.Power;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import threads.*;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static functions.TabulatedFunctions.*;

public class Main extends Application {

    public static TabulatedFunctionDoc tabFDoc;

    public static void main(String args[]) throws InappropriateFunctionPointException, IOException, IllegalAccessException, InterruptedException {



       // nonThread();

        System.out.println("--------------------------------------------Lab7---------------------------------------------------------");
        simpleThreads();
        System.out.println("--------------------------------------------Lab8---------------------------------------------------------");

        TabulatedFunction f;

        f = TabulatedFunctions.createTabulatedFunction(0, 10, 3, ArrayTabulatedFunction.class);
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                0, 10, new double[] {0, 10}, ArrayTabulatedFunction.class);
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                new FunctionPoint[] {
                                new FunctionPoint(0, 0),
                                new FunctionPoint(10, 10)
                        },
                LinkedListTabulatedFunction.class
                );
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, 11, LinkedListTabulatedFunction.class);
        System.out.println(f.getClass());
        System.out.println(f);



    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLMainForm.fxml"));
        Parent root = loader.load();
        FXMLMainFormController ctrl= loader.getController();
        tabFDoc.registerRedrawFunctionController(ctrl);
        Scene scene = new Scene(root);
        stage.setTitle("Впишите сюда заголовок главного окна");
        stage.setScene(scene);
        stage.show();



    }

    public static void nonThread() throws InappropriateFunctionPointException {
        Task task = new Task(100);

        for(int i = 0; i < task.getCountTask(); i++){
            Function log = new Log(1 + (int) (Math.random()*10));
            task.setIntegrableFunction(log);
            task.setLeftIn( (Math.random()*100));
            task.setRightIn(100 + (Math.random()*200));
            task.setStep(Math.random());
            System.out.println("Source left: "+task.getLeftIn()+", right: "+task.getRightIn()+", step: "+task.getStep());
            System.out.println("Results left: "+task.getLeftIn()+", right: "+task.getRightIn()+", step: "+task.getStep()+", integral: "+Functions.integralOfFunction(task.getIntegrableFunction(),task.getLeftIn(),task.getRightIn(),task.getStep()));

        }


    }

    public static void simpleThreads() throws InterruptedException {
        Task task = new Task(100);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SimpleGenerator simpleGenerator = new SimpleGenerator(task);
        SimpleIntegrator simpleIntegrator = new SimpleIntegrator(simpleGenerator);
        executor.execute(simpleGenerator);
        executor.execute(simpleIntegrator);

        Thread.sleep(10000);
        executor.shutdownNow();



    }

    public static void complicatedThreads() {
        Task task = new Task(100);
        Semaphore semaphore = new Semaphore(100);
        Generator generator = new Generator(task, semaphore);

        generator.start();
        Integrator integrator = new Integrator(task,semaphore);

        integrator.start();



    }
}



