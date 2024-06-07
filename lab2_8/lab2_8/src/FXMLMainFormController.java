import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FXMLMainFormController {

    @FXML
    private TextField edX, edY;

    @FXML
    private TableView<FunctionPointT> tableTabulated;

    @FXML
    private TableColumn<FunctionPointT,Double> columnX, columnY;

    ObservableList<FunctionPointT> points;

    @FXML
    Label lblPoints;

    private Stage stage;



    @FXML
    private void btNewClick(ActionEvent av) {

        FunctionPoint functionPoint = new FunctionPoint(Double.parseDouble(edX.getText()), Double.parseDouble(edY.getText()));
        try {
            Main.tabFDoc.addPoint(functionPoint);


        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }


    }


    @FXML
    public void btnDel(ActionEvent event) throws InappropriateFunctionPointException {
        int deletedPointIndex = this.tableTabulated.getSelectionModel().getSelectedIndex();
        System.out.println(deletedPointIndex);
        Main.tabFDoc.deletePoint(deletedPointIndex);
    }


    public void create(ActionEvent actionEvent) throws IOException, IllegalAccessException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLWindow.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setResizable(false);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Передаём адресата в контроллер.
        FXMLWindowController controller = loader.getController();
        controller.setStage(dialogStage);
        int dialogResult = controller.showDialog();


        // Отображаем диалоговое окно и
        //ждём, пока пользователь его не закроет
        //dialogStage.showAndWait();

        if(dialogResult == 1) {
            double newFunctionLeftDomainBorder = controller.tabulatedFunction.getLeftDomainBorder();
            double newFunctionRightDomainBorder = controller.tabulatedFunction.getRightDomainBorder();
            int newFunctionPointsCount = controller.tabulatedFunction.getPointsCount();
            Main.tabFDoc.newFunction(newFunctionLeftDomainBorder, newFunctionRightDomainBorder, newFunctionPointsCount);
            redraw();
        }
    }




    @FXML
    void initialize(){

        columnX.setCellValueFactory(new PropertyValueFactory<FunctionPointT,Double>("tabX"));
        columnY.setCellValueFactory(new PropertyValueFactory<FunctionPointT,Double>("tabY"));

        ObservableList<FunctionPointT> list = FXCollections.observableArrayList();
        for (int i = 0; i < Main.tabFDoc.getPointsCount(); i++){
            list.addAll(new FunctionPointT(Main.tabFDoc.getTb().getPointX(i),Main.tabFDoc.getTb().getPointY(i)));
        }
        tableTabulated.setItems(list);
    }



    private void setLabelText(int selectedNumber) {
        this.lblPoints.setText("Point " + (selectedNumber >= 0 ? selectedNumber + 1: "...") +
                " of " + this.points.size());
    }




    public void redraw(){


        points = FXCollections.observableArrayList();
        for(int i = 0; i < Main.tabFDoc.getPointsCount(); i++) {
            FunctionPointT temp =
                    new FunctionPointT(Main.tabFDoc.getPointX(i), Main.tabFDoc.getPointY(i));
            System.out.print(Main.tabFDoc.getPointX(i)+" ");
            points.add(temp);
        }
        tableTabulated.setItems(points);
        this.setLabelText(-1);
        tableTabulated.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<FunctionPointT>() {
                    @Override
                    public void changed(ObservableValue<? extends FunctionPointT> observableValue,
                                        FunctionPointT functionPointT, FunctionPointT t1) {
                        setLabelText(tableTabulated.getSelectionModel().getSelectedIndex());
                    }
                }
        );


    }

}

