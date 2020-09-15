package desktopApp.addTrip;


import desktopApp.mainPage.mainPageBodyController;
import desktopApp.mainPage.mainPageController;
import engine.exceptions.*;
import engine.ui.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class uploadNewTripController {

    private Tooltip ppkTooltip;

    private Tooltip capacityTooltip;

    private Tooltip nameTooltip;

    private Tooltip errorTooltip;


    @FXML
    private  Label routeLabel;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private mainPageBodyController mainPageBodyController;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField ppkTF;

    @FXML
    private TextField capacityTF;

    @FXML
    private TextField dayTF;

    @FXML
    private ComboBox<String> recurrencesCB;

    @FXML
    private ComboBox<Integer> hoursCB;

    @FXML
    private ComboBox<Integer> minutesCB;

    @FXML
    private Button startOverButton;

    @FXML
    private Button submitButton;

    public void submitOnAction(ActionEvent actionEvent) {

        try {

            mainPageController.getEngine().addTrip(nameTF.getText(), hoursCB.getValue(),minutesCB.getValue(), routeLabel.getText(), Integer.parseInt(ppkTF.getText()), Integer.parseInt(capacityTF.getText()),Integer.parseInt(dayTF.getText()),recurrencesCB.getValue());
            mainPageBodyController.getLiveMapComponentController().updateTripOnMap(Engine.getData().getPlannedTrips().getTrips().get(mainPageBodyController.getMainController().getEngine().getData().getSerialNumberCounter()));
            mainPageBodyController.setVisible("trip",false);
            mainPageBodyController.updateSystemTrips(Engine.getData().getPlannedTrips().getTrips().get(mainPageBodyController.getMainController().getEngine().getData().getSerialNumberCounter()));
            mainPageBodyController.liveMapOnAction(actionEvent);
            mainPageBodyController.getLiveMapComponentController().getTimeToolBarComponent().setVisible(true);
            mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
            startOverOnAction(actionEvent);

        } catch (NumberFormatException | InvalidStartDayValue e) {
            mainPageBodyController.getMainController().getErrorLabel().setText("Day, Capacity and PPK must be positive numbers");
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        } catch (invalidCapacityValue | MissingNameField | InvalidPpkValue | RouteIsEmpty | MissingTimeValue | NameExsitInSystem e) {
            mainPageBodyController.getMainController().getErrorLabel().setText(e.getMessage());
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        }

    }

    public void setMainController(mainPageBodyController mainPageBodyController) { this.mainPageBodyController=mainPageBodyController;}

    @FXML
    public void initialize() {
        for (Integer i = 0; i < 24; i++) hoursCB.getItems().add(i, i);

        for (Integer i = 0; i < 12; i++) minutesCB.getItems().add(i, (i*5));

        capacityTooltip=new Tooltip();
        capacityTooltip.setText("Capacity must be a positive number");
        capacityTF.setTooltip(capacityTooltip);

        ppkTooltip=new Tooltip();
        ppkTooltip.setText("PPK must be a positive number");
        ppkTF.setTooltip(ppkTooltip);

        nameTooltip=new Tooltip();
        nameTooltip.setText("Must enter a name");
        nameTF.setTooltip(nameTooltip);

        nameTooltip=new Tooltip();
        nameTooltip.setText("Day number must be bigger then 1");
        nameTF.setTooltip(nameTooltip);

        recurrencesCB.getItems().addAll("One Time", "Daily", "BiDaily", "Weekly", "Monthly");

    }

    public void startOverOnAction(ActionEvent actionEvent) {
        resetData();

        mainPageBodyController.getLiveMapComponentController().getStationsManager().backToRedButtons();
        mainPageBodyController.getLiveMapComponentController().getEdgeManager().forEach(((s, arrowedEdge) -> arrowedEdge.getLine().getStyleClass().remove(0)));
        if (mainPageBodyController.getLiveMapComponentController().getGraphMap().getId().equals("spaceBG"))
            mainPageBodyController.getLiveMapComponentController().getEdgeManager().forEach(((s, arrowedEdge) -> arrowedEdge.getLine().getStyleClass().add("line3")));
        else
            mainPageBodyController.getLiveMapComponentController().getEdgeManager().forEach(((s, arrowedEdge) -> arrowedEdge.getLine().getStyleClass().add("line2")));

    }

    private void resetData() {
        routeLabel.setText(" ");
        nameTF.clear();
        capacityTF.clear();
        ppkTF.clear();
        dayTF.clear();
        minutesCB.getSelectionModel().clearSelection();
        hoursCB.getSelectionModel().clearSelection();
        recurrencesCB.getSelectionModel().clearSelection();
    }

    public void  updateRouteLabel(String station) {
        String route = routeLabel.getText();
        if (route.equals(" "))
            route = station;
        else
            route = route + "," + station;

            routeLabel.setText(route);
            setStationsButtons(station,route);
        }

        private void setStationsButtons(String station, String route)  {
           String optionalStations= mainPageController.getEngine().displayOptionalStations(station,route);
           String[] optionalNextStations=optionalStations.split(",");
           mainPageBodyController.getLiveMapComponentController().setOptionalStations(optionalNextStations, route.split(","));
        }
    }

