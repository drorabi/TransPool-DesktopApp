package desktopApp.mainPage;

import engine.ui.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Scanner;

public class mainPageController {

    static private boolean validInput;
    static private Scanner scanner;
    static private Engine engine;
    static private boolean loaded=false;

    @FXML
     private Label errorLabel;
    @FXML
     private Label successLabel;

    @FXML
    private GridPane mainComponent;

    @FXML
    private GridPane titleComponent;

    @FXML
    private GridPane bodyComponent;

    @FXML
    private mainPageBodyController bodyComponentController;

    @FXML
    private mainPageTitleController titleComponentController;

    @FXML
    public void initialize() {
        if(bodyComponentController!=null || titleComponentController!=null){
            bodyComponentController.setMainController(this);
            titleComponentController.setMainController(this);

            engine= new Engine();
        }

        mainComponent.getStylesheets().add(getClass().getResource("style/usa.css").toExternalForm());
        mainComponent.setId("background");
        successLabel.setVisible(false);
        errorLabel.setVisible(false);

    }

    public mainPageBodyController getBodyComponentController() {
        return bodyComponentController;
    }

    public static Engine getEngine() {
        return engine;
    }

    public mainPageTitleController getTitleComponentController() {
        return titleComponentController;
    }

    public GridPane getTitleComponent() {
        return titleComponent;
    }

    public GridPane getBodyComponent() {
        return bodyComponent;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Label getSuccessLabel() {
        return successLabel;
    }

    public GridPane getMainComponent() {
        return mainComponent;
    }
}
