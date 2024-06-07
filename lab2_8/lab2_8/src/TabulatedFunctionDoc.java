import functions.*;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static functions.TabulatedFunctions.tabulate;

public class TabulatedFunctionDoc implements TabulatedFunction {

    private TabulatedFunction tabulatedFunction;
    private String fileTabulatedFunction;
    private boolean isChanged;
    FXMLMainFormController controller;


    TabulatedFunctionDoc(){

    }

    TabulatedFunctionDoc(double leftX,double rightX,int pointsCount){
        try {
            tabulatedFunction = new LinkedListTabulatedFunction(leftX,rightX,pointsCount);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    public void newFunction(double leftX, double rightX, int pointsCount) throws IllegalAccessException {
        tabulatedFunction = new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        isChanged = true;

    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        tabulatedFunction = tabulate(function, leftX, rightX, pointsCount);
        isChanged = true;
    }

    public void saveFunctionAs(String fileName){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", tabulatedFunction.getPointsCount());
        jsonObject.put("left", tabulatedFunction.getLeftDomainBorder());
        jsonObject.put("right", tabulatedFunction.getRightDomainBorder());

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonObject.toString());
            fileTabulatedFunction = fileName;
            isChanged = false;
        } catch(Exception e){
            System.out.println(e);
        }


    }

    public void loadFunction(String fileName) throws IOException, ParseException, IllegalAccessException {
        FileReader reader = new FileReader(fileName);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);



        tabulatedFunction = new LinkedListTabulatedFunction((double) jsonObject.get("left"),
                (double) jsonObject.get("right"), (int) jsonObject.get("count"));
    }


    public void saveFunction(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", tabulatedFunction.getPointsCount());
        jsonObject.put("left", tabulatedFunction.getLeftDomainBorder());
        jsonObject.put("right", tabulatedFunction.getRightDomainBorder());

        try (FileWriter file = new FileWriter(fileTabulatedFunction)) {
            file.write(jsonObject.toString());
            isChanged = false;
        } catch(Exception e){
            System.out.println(e);
        }

    }

    public void CallRedraw(){
        controller.redraw();

    }


    public void registerRedrawFunctionController(FXMLMainFormController controller){

        this.controller = controller;
        CallRedraw();

    }

    @Override
    public double getLeftDomainBorder() {
        return tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return tabulatedFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return tabulatedFunction.getFunctionValue(x);
    }

    @Override
    public int getPointsCount() {
        return tabulatedFunction.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        return tabulatedFunction.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        tabulatedFunction.setPoint(index,point);
        CallRedraw();
    }

    @Override
    public double getPointX(int index) {
        return this.tabulatedFunction.getPointX(index);

    }

    public TabulatedFunction getTb() {
        return this.tabulatedFunction;
    }
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        tabulatedFunction.setPointX(index,x);
        CallRedraw();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        tabulatedFunction.addPoint(point);
        CallRedraw();
    }

    @Override
    public void deletePoint(int index) {
        tabulatedFunction.deletePoint(index);
        CallRedraw();
    }

    @Override
    public double getPointY(int index) {
        return this.tabulatedFunction.getPointY(index);
    }

    @Override
    public void setPointY(int index, double y) {
        tabulatedFunction.setPointY(index,y);
        CallRedraw();
    }

    @Override
    public void printAll() {
        tabulatedFunction.printAll();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return tabulatedFunction.clone();
    }


    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < getPointsCount();
            }

            @Override
            public FunctionPoint next() {
                if(i < getPointsCount())
                    return getPoint(i++);
                else {
                    i = 0;
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
