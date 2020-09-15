package desktopApp.liveMap.timeToolBar;


import desktopApp.liveMap.liveMapController;
import engine.exceptions.MissingPushedTouggleButton;
import engine.exceptions.UndefineTimeBC;
import engine.ui.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;



public class timeToolBarController {

    @FXML
    private Label dateLabel;

    @FXML
    private Button forwardButton;

    @FXML
    private Button backwardButton;

    @FXML
    private ToggleButton fiveMinutesButton;

    @FXML
    private ToggleButton halfAnHourButton;

    @FXML
    private ToggleButton oneHourButton;

    @FXML
    private ToggleButton twoHoursButton;

    @FXML
    private ToggleButton dayButton;

    private liveMapController mainMapController;


    @FXML
    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        fiveMinutesButton.setToggleGroup(group);
        halfAnHourButton.setToggleGroup(group);
        oneHourButton.setToggleGroup(group);
        twoHoursButton.setToggleGroup(group);
        dayButton.setToggleGroup(group);

      dateLabel.setText("Day# 1 Monday 00:00");
    }

    public void setMainController(liveMapController mapController) {
        this.mainMapController = mapController;
    }

    public void forwardOnAction(javafx.event.ActionEvent actionEvent) {
        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);
        mainMapController.getMainBodyController().getMainController().getSuccessLabel().setVisible(false);
        try {
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);
            if (fiveMinutesButton.isSelected())
                forwardFiveMinutes();
            else if (halfAnHourButton.isSelected())
                forward30Minutes();
            else if (oneHourButton.isSelected())
                forwardOneHour();
            else if (twoHoursButton.isSelected())
                forwardTwoHours();
            else if (dayButton.isSelected())
                forwardDay();
            else
                throw new MissingPushedTouggleButton();
        }catch (MissingPushedTouggleButton e){
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setText(e.getMessage());
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(true);
        }
       mainMapController.updateGraphCanvas(Engine.getData().getCalendar().getMinutes(),
                                            Engine.getData().getCalendar().getHour(),
                                            Engine.getData().getCalendar().getDay());
    }

    private void forwardDay() {
        Engine.getData().getCalendar().forwardDay();
        dateLabel.setText(Engine.getData().getCalendar().toString());
    }

    private void forwardTwoHours() {
        Engine.getData().getCalendar().forwardTwoHours();
        dateLabel.setText(Engine.getData().getCalendar().toString());
    }

    private void forwardOneHour() {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().forwardOneHour();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void forward30Minutes() {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().forward30Minutes();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void forwardFiveMinutes() {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().forwardFiveMinutes();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    public void backwardOnAction(javafx.event.ActionEvent actionEvent) {
        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);
        mainMapController.getMainBodyController().getMainController().getSuccessLabel().setVisible(false);

        try {
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);
            if (fiveMinutesButton.isSelected())
                backwardFiveMinutes();
            else if (halfAnHourButton.isSelected())
                backward30Minutes();
            else if (oneHourButton.isSelected())
                backwardOneHour();
            else if (twoHoursButton.isSelected())
                backwardTwoHours();
            else if (dayButton.isSelected())
                backwardDay();
            else
               throw new MissingPushedTouggleButton();
        }catch (UndefineTimeBC | MissingPushedTouggleButton e){
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setText(e.getMessage());
            mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(true);
        }

        mainMapController.updateGraphCanvas(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().getMinutes(),
                mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().getHour(),
                mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().getDay());
    }

    private void backwardDay() throws UndefineTimeBC {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().backwardDay();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void backwardTwoHours() throws UndefineTimeBC {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().backwardTwoHours();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void backwardOneHour() throws UndefineTimeBC {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().backwardOneHour();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void backward30Minutes() throws UndefineTimeBC {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().backward30Minutes();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    private void backwardFiveMinutes() throws UndefineTimeBC {
        mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().backwardFiveMinutes();
        dateLabel.setText(mainMapController.getMainBodyController().getMainController().getEngine().getData().getCalendar().toString());
    }

    public void fiveMinutesOnAction(javafx.event.ActionEvent actionEvent) {        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);

    }

    public void halfAnHourOnAction(javafx.event.ActionEvent actionEvent) {        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);

    }

    public void oneHourOnAction(javafx.event.ActionEvent actionEvent) {        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);

    }

    public void twoHoursOnAction(javafx.event.ActionEvent actionEvent) {        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);

    }

    public void dayOnAction(javafx.event.ActionEvent actionEvent) {        mainMapController.getMainBodyController().getMainController().getErrorLabel().setVisible(false);

    }
}