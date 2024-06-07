package functions;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import static java.io.StreamTokenizer.TT_EOF;

public abstract class TabulatedFunctions {

    private TabulatedFunctions(){}

    private static TabulatedFunctionFactory tabulatedFunctionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();



    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if(rightX > function.getRightDomainBorder() && leftX < function.getLeftDomainBorder()) throw new IllegalArgumentException();
        double sector = (rightX - leftX)/(pointsCount-1);
        double value[] = new double[pointsCount];
        double t = leftX+sector;
        int i = 0;
        while(i < pointsCount){

            value[i] = function.getFunctionValue(t);

            t += sector;
            i++;
        }


        try {
            return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
        } catch (IllegalAccessException | InappropriateFunctionPointException e) {
            e.printStackTrace();
            return null;
        }
    }





    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> taulatedFunctionClass) throws InappropriateFunctionPointException {


        if(rightX > function.getRightDomainBorder() && leftX < function.getLeftDomainBorder()) throw new IllegalArgumentException();
        double sector = (rightX - leftX)/(pointsCount-1);
        double value[] = new double[pointsCount];
        double t = leftX+sector;
        int i = 0;
        while(i < pointsCount){

            value[i] = function.getFunctionValue(t);

            t += sector;
            i++;
        }

        return  createTabulatedFunction(leftX, rightX,value , taulatedFunctionClass);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);

        //dataOut.writeUTF("str");

        dataOut.writeDouble(function.getLeftDomainBorder());
        dataOut.writeDouble(function.getRightDomainBorder());
        dataOut.writeUTF(function.getPointsCount()+"\n");
        int i = 0;
        while (i < function.getPointsCount()){
            dataOut.writeUTF(function.getPointY(i)+"\n");
            i++;
        }


        out.close();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException, IllegalAccessException {

        DataInputStream dataIn = new DataInputStream(in);
        double leftX = dataIn.readDouble();
        double rightX = dataIn.readDouble();
        int pointsCount = (int) Double.parseDouble(dataIn.readUTF());

        double value[] = new double[pointsCount];
        int i = 0;
        while (i < pointsCount){
            value[i] = Double.parseDouble(dataIn.readUTF());
            i++;
        }

        return new ArrayTabulatedFunction(leftX,rightX,value);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        out.write(""+function.getLeftDomainBorder()+"\n");
        out.write(""+function.getRightDomainBorder()+"\n");
        out.write(""+function.getPointsCount()+"\n");
        int i = 0;
        while (i < function.getPointsCount()){
            out.write(""+function.getPointY(i)+"\n");
            i++;
        }
        out.close();
    }

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory newTabulatedFunctionFactory) {
        tabulatedFunctionFactory = newTabulatedFunctionFactory;
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException, IllegalAccessException {
        int pointsCount = 0;

        int leftX = 0;
        int rightX = 0;
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        if(streamTokenizer.nextToken() != TT_EOF){
            leftX = (int) streamTokenizer.nval;
            streamTokenizer.nextToken();
            rightX = (int) streamTokenizer.nval;
            streamTokenizer.nextToken();
            pointsCount = (int) streamTokenizer.nval;


        }

        double[] value = new double[pointsCount];

        int i = 0;
        while(streamTokenizer.nextToken() != 	TT_EOF){
            value[i] = streamTokenizer.nval;

            i++;

           // streamTokenizer.nextToken();
        }
        in.close();

        return new ArrayTabulatedFunction(leftX,rightX,value);
    }

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount)
            throws InappropriateFunctionPointException, IllegalAccessException {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values)
            throws InappropriateFunctionPointException, IllegalAccessException {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    TabulatedFunction createTabulatedFunction(FunctionPoint[] functionPoints) throws IllegalAccessException {
        return tabulatedFunctionFactory.createTabulatedFunction(functionPoints);
    }

    public static TabulatedFunction createTabulatedFunction(
            double leftX,
            double rightX,
            int pointsCount,
            Class<? extends TabulatedFunction> tabulatedFunctionClass)
            throws InappropriateFunctionPointException {
        try {
            return tabulatedFunctionClass.getConstructor(double.class, double.class, int.class).
                    newInstance(leftX, rightX, pointsCount);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(
            double leftX,
            double rightX,
            double[] values,
            Class<? extends TabulatedFunction> tabulatedFunctionClass)
            throws InappropriateFunctionPointException {
        try {


            return tabulatedFunctionClass.getConstructor(double.class, double.class, double[].class).
                    newInstance(leftX, rightX, values);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] functionPoints,
                                                            Class<? extends TabulatedFunction> tabulatedFunctionClass) {
        try {

            Object parameters = functionPoints;
            return tabulatedFunctionClass.getConstructor(FunctionPoint[].class).
                    newInstance(parameters);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

