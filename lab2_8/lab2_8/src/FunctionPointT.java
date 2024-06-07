import javafx.beans.property.SimpleDoubleProperty;

public class FunctionPointT {


    private double tabX, tabY;

    public FunctionPointT(double x,double y){
        this.tabX = x;
        tabY = y;
    }

    public double getTabX() {
        return tabX;
    }

    public void setTabX(double tabX) {
        this.tabX = tabX;
    }

    public double getTabY() {
        return tabY;
    }

    public void setTabY(double tabY) {
        this.tabY = tabY;
    }

}
