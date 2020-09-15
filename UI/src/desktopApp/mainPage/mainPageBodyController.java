package desktopApp.mainPage;

import desktopApp.addRequest.uploadNewRequestController;
import desktopApp.addTrip.uploadNewTripController;
import desktopApp.driverReview.driverReviewController;
import desktopApp.liveMap.liveMapController;
import engine.converted.classes.CombinedTrip;
import engine.converted.classes.Request;
import engine.converted.classes.Trip;
import engine.exceptions.*;
import engine.ui.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import desktopApp.makeMatch.makeMatchController;

public class mainPageBodyController {

    private boolean isAddTripVisible;

    private mainPageController mainController;

    private boolean firstRequest=true;

    private boolean firstTrip=true;


    @FXML
    private AnchorPane makeMatchComponent;

    @FXML
    private AnchorPane driverReviewComponent;

    @FXML
    private Button rankYourDriverButton;

    @FXML
    private driverReviewController driverReviewComponentController;

    @FXML
    private makeMatchController makeMatchComponentController;

    @FXML
    private AnchorPane addRequestComponent;

    @FXML
    private uploadNewRequestController addRequestComponentController;

    @FXML
    private AnchorPane addTripComponent;

    @FXML
    private uploadNewTripController addTripComponentController;

    @FXML
    private GridPane liveMapComponent;

    @FXML
    private liveMapController liveMapComponentController;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button addTripButton;

    @FXML
    private Button addRequestButton;

    @FXML
    private Button makeMatchButton;

    @FXML
    private Accordion tripsAccordion;

    @FXML
    private Accordion requestsAccordion;

    @FXML
    private Button liveMapButton;
    @FXML
    private GridPane timePane;

    @FXML
    private GridPane titlePane;

    @FXML
    private GridPane requestsAccordionPane;

    @FXML
    private GridPane tripsAccordionPane;


    @FXML
    private GridPane mainButtonsPane;


    public void setMainController(mainPageController mainController) {
        this.mainController = mainController;
    }


    public void addRequestOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        makeMatchComponentController.resetComponent();
        setVisible("trip", false);
        setVisible("map",true);
        setVisible("makeMatch", false);
        setVisible("driverReview",false);
        setVisible("request", true);

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);

        liveMapComponentController.getTimeToolBarComponent().setVisible(false);
        isAddTripVisible=false;
        addRequestComponentController.startOverOnAction(actionEvent);
        addTripComponentController.startOverOnAction(actionEvent);

    }

    public void addTripOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        makeMatchComponentController.resetComponent();

        setVisible("request", false);
        setVisible("map",true);
        setVisible("makeMatch", false);
        setVisible("driverReview",false);
        setVisible("trip", true);

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);

        liveMapComponentController.getTimeToolBarComponent().setVisible(false);
        isAddTripVisible=true;
        addRequestComponentController.startOverOnAction(actionEvent);
        addTripComponentController.startOverOnAction(actionEvent);
    }

    public void makeMatchOnAction(ActionEvent actionEvent) {
        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);
        try {
            for (Map.Entry<Integer, Request> entry : Engine.getData().getRequests().entrySet()) {
                if (!entry.getValue().isMatched() && !makeMatchComponentController.getRequestSerialNumberCB().getItems().contains(entry.getValue().getSerialNumber()))
                    makeMatchComponentController.getRequestSerialNumberCB().getItems().add(entry.getValue().getSerialNumber());
            }
            if(makeMatchComponentController.getRequestSerialNumberCB().getItems().isEmpty())
                throw new NoAvailableRequests();


            setVisible("request", false);
            setVisible("map", false);
            setVisible("trip", false);
            setVisible("driverReview", false);
            setVisible("makeMatch", true);
            makeMatchComponentController.getMatchedRidesAccordion().getPanes().clear();
            isAddTripVisible = false;

            addRequestComponentController.startOverOnAction(actionEvent);
            addTripComponentController.startOverOnAction(actionEvent);

       }catch(NoAvailableRequests e){
            mainController.getErrorLabel().setText(e.getMessage());
            mainController.getErrorLabel().setVisible(true);
       }

    }

    public void loadFileOnAction(ActionEvent actionEvent) {
        mainController.getTitleComponentController().getApplyFileDataButton().setDisable(false);
        mainController.getTitleComponentController().getPrecentProgress().setVisible(true);
        mainController.getTitleComponentController().getBarProgrees().setVisible(true);

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile==null)
            return;
        mainPageController.getEngine().loadData(selectedFile.getPath());
        mainController.getTitleComponentController().getBarProgrees().progressProperty().bind(mainController.getEngine().getLoader().progressProperty());
        mainController.getTitleComponentController().getPrecentProgress().progressProperty().bind(mainController.getEngine().getLoader().progressProperty());
    }


    public void liveMapOnAction(ActionEvent actionEvent) {
        makeMatchComponentController.resetComponent();
        setVisible("trip", false);
        setVisible("map",true);
        setVisible("makeMatch", false);
        setVisible("driverReview",false);
        setVisible("request", false);
        liveMapComponentController.getTimeToolBarComponent().setVisible(true);

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);

        isAddTripVisible=false;
        addRequestComponentController.startOverOnAction(actionEvent);
        addTripComponentController.startOverOnAction(actionEvent);
    }


    public void rankYourDriverOnAction(ActionEvent actionEvent) {
        makeMatchComponentController.resetComponent();

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);

        for (Map.Entry<Integer, Request> entry : Engine.getData().getRequests().entrySet()) {
            if (entry.getValue().isMatched() &&  !driverReviewComponentController.getRequestCB().getItems().contains(entry.getValue().getSerialNumber()))
                driverReviewComponentController.getRequestCB().getItems().add(entry.getValue().getSerialNumber());
        }
        if (driverReviewComponentController.getRequestCB().getItems().isEmpty()) {
            mainController.getErrorLabel().setText("There are no available trips to rank");
            mainController.getErrorLabel().setVisible(true);
        } else {
            mainController.getErrorLabel().setVisible(false);
            driverReviewComponentController.makeBlackStarsVisible(1);
            setVisible("trip", false);
            setVisible("map", false);
            setVisible("makeMatch", false);
            setVisible("driverReview", true);
            setVisible("request", false);
        }
    }




    public void setSystem() {
        if(tripsAccordion.getPanes()!=null)
            tripsAccordion.getPanes().clear();
        if(requestsAccordion.getPanes()!=null)
            requestsAccordion.getPanes().clear();

        for(Map.Entry<Integer, Trip> entry : Engine.getData().getPlannedTrips().getTrips().entrySet()) {
            TitledPane title = new TitledPane(entry.getValue().getOwner() + " " + entry.getValue().getSerialNumber(), new TextArea(entry.getValue().toString()));
            title.setOnMouseClicked(event-> tripsAccordionOnAction());
            tripsAccordion.getPanes().add(0, title);
        }

        if(!Engine.getData().getPlannedTrips().getTrips().isEmpty()) {
            firstTrip=false;
            rankYourDriverButton.setDisable(false);
        }

        for(Map.Entry<Integer, Request> entry : Engine.getData().getRequests().entrySet())
            requestsAccordion.getPanes().add(0, new TitledPane(entry.getValue().getName() + " " + entry.getValue().getSerialNumber(), new TextArea(entry.getValue().toString())));

        if(!Engine.getData().getRequests().isEmpty()) {
            firstRequest = false;
            rankYourDriverButton.setDisable(false);
        }

        disableButtons(false);

        liveMapComponentController.setLiveMap();

    }

    private void disableButtons(boolean b) {
        addRequestButton.setDisable(b);
        addTripButton.setDisable(b);
        makeMatchButton.setDisable(b);
        liveMapButton.setDisable(b);
        rankYourDriverButton.setDisable(b);
    }

    public uploadNewTripController getAddTripComponentController() {
        return addTripComponentController;
    }

    public GridPane getLiveMapComponent() {
        return liveMapComponent;
    }

    public liveMapController getLiveMapComponentController() {
        return liveMapComponentController;
    }

    @FXML
    public void initialize() {
        if(addRequestComponentController!=null || addTripComponentController!=null || makeMatchComponentController!=null || liveMapComponentController!=null || driverReviewComponentController!=null){
            addRequestComponentController.setMainController(this);
            addTripComponentController.setMainController(this);
            makeMatchComponentController.setMainController(this);
            liveMapComponentController.setMainController(this);
            driverReviewComponentController.setMainController(this);
        }


        isAddTripVisible=false;
        rankYourDriverButton.setDisable(false);
        addRequestComponent.setVisible(false);
        addTripComponent.setVisible(false);
        makeMatchComponent.setVisible(false);
        liveMapComponent.setVisible(false);
        driverReviewComponent.setVisible(false);

        tripsAccordion.getPanes().add(3, new TitledPane("No trips in the system", new TextArea("--------------")));
        tripsAccordion.getPanes().remove(0);
        tripsAccordion.getPanes().remove(0);
        tripsAccordion.getPanes().remove(0);

        requestsAccordion.getPanes().add(3, new TitledPane("No requests in the system", new TextArea("--------------")));
        requestsAccordion.getPanes().remove(0);
        requestsAccordion.getPanes().remove(0);
        requestsAccordion.getPanes().remove(0);

        disableButtons(true);
    }

    public boolean getIsAddTripVisible(){
        return isAddTripVisible;
    }

    public void updateSystemTrips(Trip trip) {
        TitledPane title = new TitledPane(trip.getOwner() + " " + trip.getSerialNumber(), new TextArea(trip.toString()));
        title.setOnMouseClicked(event-> tripsAccordionOnAction());
        if(firstTrip) {
             tripsAccordion.getPanes().add(0,title);
            firstTrip=false;
        }else
            tripsAccordion.getPanes().add(0,title);

        liveMapComponentController.updateTripOnMap(trip);

        }

    public void updateSystemRequests(Request request) {
        if(firstRequest) {
            requestsAccordion.getPanes().add(0, new TitledPane(request.getName() + " " + request.getSerialNumber(), new TextArea(request.toString())));
            firstRequest=false;

        }else
            requestsAccordion.getPanes().add(0, new TitledPane(request.getName() + " " + request.getSerialNumber(), new TextArea(request.toString())));
    }

    public AnchorPane getAddRequestComponent() {
        return addRequestComponent;
    }

    public uploadNewRequestController getAddRequestComponentController() {
        return addRequestComponentController;
    }


    public mainPageController getMainController() {
        return mainController;
    }

    public Accordion getTripsAccordion(){ return tripsAccordion;}

    public void setVisible(String component, boolean b){
        if(component.equals("request"))
            addRequestComponent.setVisible(b);

        if(component.equals("trip"))
            addTripComponent.setVisible(b);

        if(component.equals("makeMatch"))
            makeMatchComponent.setVisible(b);

        if(component.equals("map"))
            liveMapComponent.setVisible(b);

        if(component.equals("driverReview"))
            driverReviewComponent.setVisible(b);
    }

    public void updateTripDataInAccordion(Trip tripToUpdate) {
        for(TitledPane str: tripsAccordion.getPanes()){
            if(Integer.parseInt(str.getText().substring(str.getText().length()-4)) == tripToUpdate.getSerialNumber()) {
                str.setContent(new TextArea(tripToUpdate.toString()));
                break;
            }
        }
    }

    public void updateRequestDataInAccordion(Request requestToUpdate) {
        for(TitledPane str: requestsAccordion.getPanes()){
            if(Integer.parseInt(str.getText().substring(str.getText().length()-4)) == requestToUpdate.getSerialNumber()) {
                str.setContent(new TextArea(requestToUpdate.toString()));
                break;
            }
        }
    }

    public void tripsAccordionOnAction() {

        mainController.getErrorLabel().setVisible(false);
        mainController.getSuccessLabel().setVisible(false);
        liveMapOnAction(new ActionEvent());
        if(tripsAccordion.getExpandedPane()==null)
            return;
        else{
            Integer serialNumber= Integer.parseInt(tripsAccordion.getExpandedPane().getText().substring(tripsAccordion.getExpandedPane().getText().length()-4));
            liveMapComponentController.makeRouteYellow(Engine.getData().getPlannedTrips().getTrips().get(serialNumber).getRoute());
        }
    }

    public void stationTooltipUpdate(CombinedTrip matchedRide, String name) {
        liveMapComponentController.stationTooltipUpdate(matchedRide,name);
    }

    public void initCmponents() {
        addTripComponent.setVisible(false);
        addRequestComponent.setVisible(false);
        driverReviewComponent.setVisible(false);
        makeMatchComponent.setVisible(false);
        liveMapComponent.setVisible(true);
    }
}