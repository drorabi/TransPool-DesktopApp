package desktopApp.addRequest;

import desktopApp.mainPage.mainPageBodyController;
import engine.converted.classes.Station;
import engine.exceptions.*;
import engine.ui.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class uploadNewRequestController {

    @FXML private mainPageBodyController mainPageBodyController;

    @FXML private TextField nameTF;

    @FXML private TextField dayTF;

    @FXML private RadioButton byDepartureButton;

    @FXML private RadioButton byArrivalButton;

    @FXML private ComboBox<Integer> hoursCB;

    @FXML private ComboBox<Integer> minutesCB;

    @FXML private Button submitButton;

    @FXML private ComboBox<String> fromCB;

    @FXML private ComboBox<String> toCB;

    public void submitOnAction(javafx.event.ActionEvent actionEvent) {
        boolean byDeparture = byDepartureButton.isSelected();
        try {
            Engine.getData().addRequest(nameTF.getText(), fromCB.getValue(), toCB.getValue(), Integer.parseInt(dayTF.getText()), hoursCB.getValue(), minutesCB.getValue(), byDeparture);
            mainPageBodyController.liveMapOnAction(actionEvent);
            mainPageBodyController.updateSystemRequests(Engine.getData().getRequests().get(Engine.getData().getSerialNumberCounter()));
            mainPageBodyController.setVisible("request", false);
            mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
            resetData();
        } catch (InvalidRideStartHour | InvalidRideStartDay | InvalidRequestDepartureDestination | InvalidRideStartMinutes | MissingFromToValue | MissingNameField | MissingTimeValue e) {
            mainPageBodyController.getMainController().getErrorLabel().setText(e.getMessage());
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        }catch (NumberFormatException e){
            mainPageBodyController.getMainController().getErrorLabel().setText("Day must be a positive number");
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        }

    }

public void updateRequestCB(){
        fromCB.getItems().clear();
        toCB.getItems().clear();
        for(Map.Entry<String, Station> entry : Engine.getData().getMapData().getStations().getStations().entrySet()) {
            fromCB.getItems().add(entry.getValue().getName());
            toCB.getItems().add(entry.getValue().getName());
        }
    }

    @FXML public void initialize() {
        for (Integer i = 0; i < 24; i++) hoursCB.getItems().add(i, i);

        for (Integer i = 0; i < 12; i++) minutesCB.getItems().add(i, i * 5);

        ToggleGroup group = new ToggleGroup();
        byArrivalButton.setToggleGroup(group);
        byDepartureButton.setToggleGroup(group);
        byDepartureButton.setSelected(true);
    }
    public void setMainController(mainPageBodyController mainPageBodyController) { this.mainPageBodyController=mainPageBodyController;}


    public void fromButtonOnAction(ActionEvent actionEvent) {
         if(fromCB.getValue()==toCB.getValue()) {
             mainPageBodyController.getMainController().getErrorLabel().setText("You chose the same station as departure and destination");
             mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
             submitButton.setDisable(true);
         } else {
             mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
             submitButton.setDisable(false);
         }
    }

    public void toButtonOnAction(ActionEvent actionEvent) {
        if(fromCB.getValue()==toCB.getValue()) {
            mainPageBodyController.getMainController().getErrorLabel().setText("You chose the same station as departure and destination");
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
            submitButton.setDisable(true);
        } else {
            mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
            submitButton.setDisable(false);
        }
    }

    public void startOverOnAction(ActionEvent actionEvent) {
        resetData();
    }

    public void resetData(){
        dayTF.clear();
        nameTF.clear();
        mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
        toCB.getSelectionModel().clearSelection();
        fromCB.getSelectionModel().clearSelection();
        hoursCB.getSelectionModel().clearSelection();
        minutesCB.getSelectionModel().clearSelection();
    }
}
