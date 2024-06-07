package functions;

public interface TabulatedFunction extends Function, Iterable<FunctionPoint>{



    int getPointsCount();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException ;

    double getPointX(int index);

    void setPointX(int index, double x) throws InappropriateFunctionPointException ;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    void deletePoint(int index);

    double getPointY(int index);

    void setPointY(int index, double y);

    void printAll();

    Object clone() throws CloneNotSupportedException;

}

