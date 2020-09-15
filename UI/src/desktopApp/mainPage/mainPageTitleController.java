package desktopApp.mainPage;


import desktopApp.liveMap.roads.ArrowedEdge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.Map;


public class mainPageTitleController {

    @FXML private ProgressBar barProgrees;

    @FXML private ProgressIndicator precentProgress;

    @FXML private Button applyFileDataButton;

    @FXML private ImageView usaFlag;

    @FXML private ImageView nasaFlag;

    @FXML private ImageView egyptFlag;

    @FXML private GridPane titlePane;


    @FXML private Label titleLabel;

@FXML private ComboBox<String> themeColorsCB;

    private mainPageController mainController;


    public void setMainController(mainPageController mainController){
        this.mainController =mainController;}

    public void backgroundThemeButton(javafx.event.ActionEvent actionEvent) {

       changeTheme(themeColorsCB.getValue());
    }

    private void changeTheme(String value) {

        mainController.getBodyComponentController().getAddTripComponentController().startOverOnAction(new ActionEvent());
        hideAllFlags();
        if(value==null)
            value="Washington, USA";
        switch (value) {
            case "Washington, USA":
                mainController.getMainComponent().getStylesheets().clear();
                mainController.getMainComponent().getStylesheets().add(getClass().getResource("style/usa.css").toExternalForm());
                mainController.getMainComponent().setId("background");
                mainController.getBodyComponentController().getLiveMapComponentController().getGraphMap().setId("washingtonBG");
                for (Map.Entry<String, ArrowedEdge> entry : mainController.getBodyComponentController().getLiveMapComponentController().getEdgeManager().entrySet()) {
                    entry.getValue().getLine().getStyleClass().remove(0);
                    entry.getValue().getLine().getStyleClass().add("line2");
                }
                usaFlag.setVisible(true);
                break;
            case "Sinai, Egypt":
                mainController.getMainComponent().getStylesheets().clear();
                mainController.getMainComponent().getStylesheets().add(getClass().getResource("style/sinai.css").toExternalForm());
                mainController.getMainComponent().setId("background");
                mainController.getBodyComponentController().getLiveMapComponentController().getGraphMap().setId("sinaiBG");
                for (Map.Entry<String, ArrowedEdge> entry : mainController.getBodyComponentController().getLiveMapComponentController().getEdgeManager().entrySet()) {
                    entry.getValue().getLine().getStyleClass().remove(0);
                    entry.getValue().getLine().getStyleClass().add("line2");
                }
                egyptFlag.setVisible(true);

                break;
            default:
                mainController.getMainComponent().getStylesheets().clear();
                mainController.getMainComponent().getStylesheets().add(getClass().getResource("style/space.css").toExternalForm());
                mainController.getMainComponent().setId("background");
                mainController.getBodyComponentController().getLiveMapComponentController().getGraphMap().setId("spaceBG");
                for (Map.Entry<String, ArrowedEdge> entry : mainController.getBodyComponentController().getLiveMapComponentController().getEdgeManager().entrySet()) {
                    entry.getValue().getLine().getStyleClass().remove(0);
                    entry.getValue().getLine().getStyleClass().add("line3");
                }


                nasaFlag.setVisible(true);
                break;
        }
    }

    private void hideAllFlags() {
        usaFlag.setVisible(false);
        egyptFlag.setVisible(false);
        nasaFlag.setVisible(false);
    }

    @FXML
    public void initialize(){
        themeColorsCB.getItems().addAll("Washington, USA","Sinai, Egypt","MilkyWay, Space");
        hideAllFlags();
        usaFlag.setVisible(true);
        themeColorsCB.setDisable(true);
        applyFileDataButton.setDisable(true);

    }

    public ComboBox<String> getBackgroundColorPickerButton() {
        return themeColorsCB;
    }


    public void applyFileDataButtonOnAction(ActionEvent actionEvent) {
        if(mainPageController.getEngine().getLoader()==null)
            return;
        if (mainPageController.getEngine().getLoader().isDone()) {
            if (mainPageController.getEngine().getLoader().IsFileUploadSuccessfully()) {
                mainController.getMainComponent().getStylesheets().clear();
                mainController.getMainComponent().getStylesheets().add(getClass().getResource("style/usa.css").toExternalForm());
                mainController.getMainComponent().setId("background");
                themeColorsCB.getSelectionModel().clearSelection();
                mainController.getBodyComponentController().setSystem();
                mainController.getBodyComponentController().initCmponents();
                mainController.getBodyComponentController().getAddRequestComponentController().updateRequestCB();
                themeColorsCB.setDisable(false);
                applyFileDataButton.setDisable(true);
                mainController.getSuccessLabel().setText("FILE LOADED SUCCESSFULLY!");
                mainController.getSuccessLabel().setVisible(true);

            } else {
                mainController.getErrorLabel().setText(mainPageController.getEngine().getLoader().getMassage());
                mainController.getErrorLabel().setVisible(true);

                applyFileDataButton.setVisible(true);
                precentProgress.setVisible(true);
                barProgrees.setVisible(true);
            }
        }
    }

    public ProgressBar getBarProgrees() {
        return barProgrees;
    }

    public ProgressIndicator getPrecentProgress() {
        return precentProgress;
    }

    public Button getApplyFileDataButton() {
        return applyFileDataButton;
    }
}


