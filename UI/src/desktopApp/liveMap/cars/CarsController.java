package desktopApp.liveMap.cars;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;

public class CarsController {

    @FXML private AnchorPane carAnchorPane;

    @FXML private ImageView carPane;

    @FXML private ImageView rickAndMorty;

    @FXML private Tooltip tooltip;

    private double x;

    private double y;

    private LinkedList<String> poolers;

    private String name;

    private int capacity;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void setName( CarNode carNode) {
        poolers=new LinkedList<>();
        rickAndMorty.setVisible(false);
        x = carNode.getX();
        y = carNode.getY();
        name = carNode.getName();
        capacity = carNode.getTrip().getCapacity();
        tooltip = new Tooltip(createTooltipString());
        Tooltip.install(carAnchorPane, tooltip);
    }

    public void tooltipUpdateOUT(String pooler) {
        if(poolers.contains(pooler)) {
            poolers.remove(pooler);
            tooltip.setText(createTooltipString());
        }
    }

    public void tooltipUpdateIN(String pooler) {
        if(!poolers.contains(pooler)) {
            poolers.add(pooler);
            tooltip.setText(createTooltipString());
        }
    }

    private String createTooltipString() {
        StringBuilder toString = new StringBuilder(name + "\nCurrent poolers in the car:\n");
        for(String pooler : poolers)
            toString.append(pooler).append("\n");
        toString.append("Capacity: " + (capacity-(poolers.size())));
        return toString.toString();
    }
}