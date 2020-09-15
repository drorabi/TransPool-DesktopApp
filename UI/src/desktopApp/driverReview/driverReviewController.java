package desktopApp.driverReview;

import desktopApp.mainPage.mainPageBodyController;
import desktopApp.mainPage.mainPageController;
import engine.converted.classes.MatchedRide;
import engine.ui.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class driverReviewController {

    @FXML
    private ComboBox<Integer> requestCB;

    @FXML
    private ComboBox<Integer> tripsCB;


    @FXML
    private ImageView fourBlackStar;

    @FXML
    private ImageView fiveBlackStar;

    @FXML
    private ImageView threeBlackStar;

    @FXML
    private ImageView oneBlackStar;

    @FXML
    private ImageView twoBlackStar;

    @FXML
    public TextArea feedbackTA;
    @FXML
    public Label rankLabel;
    @FXML
    public Button submitButton;

    @FXML
    private ImageView fourStarButton;

    @FXML
    private ImageView threeStarButton;

    @FXML
    private ImageView twoStarButton;

    @FXML
    private ImageView oneStarButton;

    @FXML
    private ImageView fiveStartButton;

    private mainPageBodyController mainBodyController;

    @FXML
    public void initialize() {
        makeBlackStarsVisible(1);
        tripsCB.setDisable(true);
        feedbackTA.setDisable(true);
        submitButton.setDisable(true);
    }

    public void setMainController(mainPageBodyController mainPageBodyController) {
        mainBodyController = mainPageBodyController;
    }

    public void fiveStarOnAction(MouseEvent mouseEvent) {
        makeBlackStarsVisible(5);
    }

    public void fourStarOnAction(MouseEvent mouseEvent) {
        makeBlackStarsVisible(4);

    }

    public void threeStarOnAction(MouseEvent mouseEvent) {
        makeBlackStarsVisible(3);

    }

    public void twoStarOnAction(MouseEvent mouseEvent) {
        makeBlackStarsVisible(2);

    }

    public void oneStarOnAction(MouseEvent mouseEvent) {
        makeBlackStarsVisible(1);

    }


    public void submitOnAction(ActionEvent actionEvent) {


        int rank = Integer.parseInt(rankLabel.getText().substring(0, 1));
        mainPageController.getEngine().addFeedbackToTrip(Engine.getData().getPlannedTrips().getTrips().get(tripsCB.getValue()), rank, feedbackTA.getText());
        mainBodyController.liveMapOnAction(actionEvent);
        mainBodyController.updateTripDataInAccordion(Engine.getData().getPlannedTrips().getTrips().get(tripsCB.getValue()));
        submitButton.setDisable(true);
        feedbackTA.clear();
        feedbackTA.setDisable(true);
        requestCB.getItems().clear();
        tripsCB.setDisable(true);


    }

    public void requestCBOnAction(ActionEvent actionEvent) {
        tripsCB.getItems().clear();
        tripsCB.setDisable(false);
        if(requestCB.getValue()!=null) {
            for (MatchedRide mr : Engine.getData().getRequests().get(requestCB.getValue()).getMatchedRide().getTrip()) {
                if (!tripsCB.getItems().contains(mr.getTrip().getSerialNumber()))
                    tripsCB.getItems().add(mr.getTrip().getSerialNumber());
            }
        }
    }

    public void tripsCBOnAction(ActionEvent actionEvent) {
        feedbackTA.setDisable(false);
        submitButton.setDisable(false);
    }

    public ComboBox<Integer> getRequestCB() {
        return requestCB;
    }

    public ComboBox<Integer> getTripsCB() {
        return tripsCB;
    }

    public void makeBlackStarsVisible(int rank){

        if(rank==1){
            rankLabel.setText("1.0");
            oneBlackStar.setVisible(true);
            twoBlackStar.setVisible(false);
            threeBlackStar.setVisible(false);
            fourBlackStar.setVisible(false);
            fiveBlackStar.setVisible(false);
        }
        else if(rank==2){
            rankLabel.setText("2.0");
            oneBlackStar.setVisible(true);
            twoBlackStar.setVisible(true);
            threeBlackStar.setVisible(false);
            fourBlackStar.setVisible(false);
            fiveBlackStar.setVisible(false);
        }
        else if(rank==3){
            rankLabel.setText("3.0");
            oneBlackStar.setVisible(true);
            twoBlackStar.setVisible(true);
            threeBlackStar.setVisible(true);
            fourBlackStar.setVisible(false);
            fiveBlackStar.setVisible(false);
        }
        else if(rank==4){
            rankLabel.setText("4.0");
            oneBlackStar.setVisible(true);
            twoBlackStar.setVisible(true);
            threeBlackStar.setVisible(true);
            fourBlackStar.setVisible(true);
            fiveBlackStar.setVisible(false);
        }
        else{
            rankLabel.setText("5.0");
            oneBlackStar.setVisible(true);
            twoBlackStar.setVisible(true);
            threeBlackStar.setVisible(true);
            fourBlackStar.setVisible(true);
            fiveBlackStar.setVisible(true);
        }
    }

}
