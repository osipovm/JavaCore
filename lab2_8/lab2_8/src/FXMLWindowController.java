import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLWindowController {



    public TabulatedFunctionDoc tabulatedFunction;
    private Stage primaryStage;

    private final int OK = 11;
    private final int CANCEL = 10;

    private int STATUS;

    private int isNorm = -1;

    @FXML
    TextField leftBorder;

    @FXML
    TextField rightBorder;

    @FXML
    Spinner<Integer> spinnerPointsCount;
    SpinnerValueFactory<Integer> Spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 100, 3);

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            System.out.println("Не соответствует применяемому типу");
            return false;
        }
    }

    private void showAlertWithHeaderText(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(str);
        alert.showAndWait();
    }


    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public int showDialog() {
        this.primaryStage.showAndWait();
        return isNorm;
    }


    public void btnCancel(ActionEvent actionEvent) {
        STATUS = CANCEL;
        primaryStage.close();
        isNorm = -1;

    }



    public void btnOk(ActionEvent actionEvent) throws IllegalAccessException {

        if(isDouble(leftBorder.getText())&& isDouble(rightBorder.getText())){
            if(Double.parseDouble(leftBorder.getText()) < Double.parseDouble(rightBorder.getText()) || spinnerPointsCount.getValue()<3) {
                STATUS = OK;
                tabulatedFunction = new TabulatedFunctionDoc();
                tabulatedFunction.newFunction(Double.parseDouble(leftBorder.getText()), Double.parseDouble(rightBorder.getText()), (Integer) spinnerPointsCount.getValue());
                primaryStage.hide();
            }
            else showAlertWithHeaderText("Неправильное расположение границ");
        }
        else showAlertWithHeaderText("Не соответсвует введенному типу");

        isNorm = 1;
    }

    public void initialize() {
        this.spinnerPointsCount.setValueFactory(this.Spinner);
    }
}

