package functions;

public interface TabulatedFunctionFactory {

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount)
            throws InappropriateFunctionPointException, IllegalAccessException;
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values)
            throws InappropriateFunctionPointException, IllegalAccessException;
    TabulatedFunction createTabulatedFunction(FunctionPoint[] functionPoints) throws IllegalAccessException;

}

