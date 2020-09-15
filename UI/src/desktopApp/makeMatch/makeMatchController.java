package desktopApp.makeMatch;

import desktopApp.mainPage.mainPageBodyController;
import engine.converted.classes.MatchedRide;
import engine.exceptions.MustBePositiveNumber;
import engine.exceptions.NoMatchingRides;
import engine.ui.Engine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class makeMatchController {

    @FXML
    private Button showResultsButton;

    @FXML
    private mainPageBodyController mainPageBodyController;

    @FXML
    private CheckBox oneTripOfferCB;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private GridPane rideGridPane;

    @FXML
    private Label rideTitleLabel;

    @FXML
    private Label rideContentLabel;

    @FXML
    private Accordion matchedRidesAccordion;
    @FXML
    private ComboBox<Integer> requestSerialNumberCB;

    @FXML
    private TextField numberOfOffersTF;

    @FXML
    private Button poolRideButton;

    @FXML
    void showResultsOnAction(ActionEvent event) {
        mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
        matchedRidesAccordion.getPanes().clear();
        try {
            Integer size = Integer.parseInt(numberOfOffersTF.getText());
            if(size<=0)
                throw new MustBePositiveNumber();
            Engine.getData().makeAMatch(Engine.getData().getRequests().get(requestSerialNumberCB.getValue()),size, oneTripOfferCB.isSelected());
            if(Engine.getData().getAllOptions().size()==0){
                throw new NoMatchingRides();
            }
            int index, i = 0;
            for (index = 0; index < Engine.getData().getAllOptions().size() ; index++, i++) {
                TitledPane titledPane = new TitledPane("" + (index + 1), new TextArea((Engine.getData().getAllOptions().get(index).toString())));
                titledPane.setOnMouseClicked(event1 -> matchedRideAccordionOnAction());
                matchedRidesAccordion.getPanes().add(titledPane);
            }
        }catch (NumberFormatException | MustBePositiveNumber e){
            mainPageBodyController.getMainController().getErrorLabel().setText("Number of offers must be a positive number");
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        }catch (NoMatchingRides e){
            mainPageBodyController.getMainController().getErrorLabel().setText(e.getMessage());
            mainPageBodyController.getMainController().getErrorLabel().setVisible(true);
        }
    }

    @FXML
    void poolRideOnAction(ActionEvent event) {
        Integer index=Integer.parseInt(matchedRidesAccordion.getExpandedPane().getText());
      Engine.getData().setMatchedRide(requestSerialNumberCB.getValue(),Engine.getData().getAllOptions().get(index-1));
      mainPageBodyController.updateRequestDataInAccordion(Engine.getData().getRequests().get(requestSerialNumberCB.getValue()));
      mainPageBodyController.stationTooltipUpdate(Engine.getData().getRequests().get(requestSerialNumberCB.getValue()).getMatchedRide(),Engine.getData().getRequests().get(requestSerialNumberCB.getValue()).getName() + " " + Engine.getData().getRequests().get(requestSerialNumberCB.getValue()).getSerialNumber());
      resetComponent();
      poolRideButton.setDisable(true);
      mainPageBodyController.liveMapOnAction(event);
    }

    @FXML
    void requestSerialNumberOnAction(ActionEvent event) {
        numberOfOffersTF.setDisable(false);
        oneTripOfferCB.setDisable(false);
        showResultsButton.setDisable(false);
    }


    public void setMainController(mainPageBodyController mainPageBodyController){this.mainPageBodyController=mainPageBodyController;}


    @FXML
    public void initialize() {
        poolRideButton.setDisable(true);
        numberOfOffersTF.setDisable(true);
        oneTripOfferCB.setDisable(true);
        showResultsButton.setDisable(true);
    }


    public void matchedRideAccordionOnAction() {
        if (matchedRidesAccordion.getExpandedPane() == null)
            poolRideButton.setDisable(true);
        else
            poolRideButton.setDisable(false);
    }

    public Accordion getMatchedRidesAccordion() {
        return matchedRidesAccordion;
    }

    public ComboBox<Integer> getRequestSerialNumberCB() {
        return requestSerialNumberCB;
    }

    public void resetComponent(){
        showResultsButton.setDisable(true);
        oneTripOfferCB.setSelected(false);
        requestSerialNumberCB.getItems().clear();
        numberOfOffersTF.clear();
        numberOfOffersTF.setDisable(true);
        oneTripOfferCB.setDisable(true);
        mainPageBodyController.getMainController().getErrorLabel().setVisible(false);
        matchedRidesAccordion.getPanes().clear();
    }
}
