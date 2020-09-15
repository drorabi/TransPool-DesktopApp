package desktopApp.liveMap.Stations;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedList;

public class stationController {

    private StationNode stationNode;
    private String name;
    private int x;
    private int y;


    @FXML
    private AnchorPane stationAnchorPane;

    @FXML
    private ImageView redStationButton;

    @FXML
    private ImageView yellowStationButton;

    @FXML
    private ImageView greenStationButton;

    private LinkedList<String> poolers=new LinkedList<>();

    @FXML
    private ImageView blackStationButton;

    @FXML
    public Label stationName;

    private Tooltip tooltip;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void setName( String name, StationNode stationNode) {

        this.name = name;
        this.stationNode = stationNode;
        yellowStationButton.setVisible(false);
        blackStationButton.setVisible(false);
        greenStationButton.setVisible(false);
        tooltip = new Tooltip();
        Tooltip.install(redStationButton,tooltip);
        tooltip.setStyle("-fx-text-fill: black;\n" + "-fx-background-color: gold;");
        tooltip.setText(createTooltipText());
        stationName.setText(name);
    }

    private String createTooltipText() {
        StringBuilder details= new StringBuilder(name + "\nPoolers in station:\n");

        for(String pool : poolers)
            details.append(pool).append("\n");

        return details.toString();
    }


    public ImageView getRedStationButton() {
        return redStationButton;
    }


    public void redStationButtonOnAction(MouseEvent mouseEvent) {
        stationNode.stationButtonOnAction();
    }

    public void onMouseOver(MouseDragEvent mouseDragEvent) {
        redStationButton.setCursor(Cursor.HAND);
    }

    public ImageView getYellowStationButton() {
        return yellowStationButton;
    }

    public ImageView getBlackStationButton() {
        return blackStationButton;
    }

    public ImageView getGreenStationButton() {
        return greenStationButton;
    }

    public void tooltipUpdate(String name) {
        if (!poolers.contains(name)) {
            poolers.add(name);
            tooltip.setText(createTooltipText());
        }
    }
}
