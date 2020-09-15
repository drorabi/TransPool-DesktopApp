package desktopApp.liveMap.cars;

import com.fxgraph.cells.AbstractCell;
import com.fxgraph.graph.Graph;
import desktopApp.liveMap.liveMapController;
import engine.converted.classes.MatchedRide;
import engine.converted.classes.Request;
import engine.converted.classes.Station;
import engine.converted.classes.Trip;
import engine.ui.Engine;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.Map;

public class CarNode extends AbstractCell {

    private CarsController controller;
    private liveMapController mapController;
    private double x;
    private double y;
    private String name;
    private String details;
    private Trip trip;

    public CarNode(Trip trip, liveMapController mainController) {
        this.x = trip.getRide()[0].getCoordinate().getX() * 100;
        this.y = trip.getRide()[0].getCoordinate().getY() * 100;
        this.name = trip.getSerialNumber() + " " + trip.getOwner();
        mapController = mainController;
        this.trip=trip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    /*
    Creates the graphical representation of the station.
     */
    public Region getGraphic(Graph graph) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("carPane.fxml");
            fxmlLoader.setLocation(url);
            AnchorPane root = fxmlLoader.load(url.openStream());

            // updates information on the actual node's controller
            controller = fxmlLoader.getController();
            controller.setX(x);
            controller.setY(y);
            controller.setName(this);
            // controller.setDetailsDTOSupplier(detailsSupplier);

            return root;
        } catch (Exception e) {
            return new Label("Error when tried to create graphic node !");
        }
    }

    public void setX(double x) {
        this.x = x*100;
        controller.setX(x*100);
    }

    public void setY(double y) {
        this.y = y*100;
        controller.setY(y*100);
    }

    public CarsController getController() {
        return controller;
    }

    public void tooltipUpdate(int day, int hour, int minutes) {
        for(Map.Entry<Integer,Request> entry : Engine.getData().getRequests().entrySet()){
            if(entry.getValue().getMatchedRide()==null)
                return;
            for(MatchedRide mr : entry.getValue().getMatchedRide().getTrip()){
                if(mr.getTrip().getSerialNumber()==trip.getSerialNumber()) {
                    int end = (mr.getRoute().getLast().getDay()*24*60)+(mr.getRoute().getLast().getHour()*60)+(mr.getRoute().getLast().getMinutes());
                    int start = (mr.getRoute().getFirst().getDay()*24*60)+(mr.getRoute().getFirst().getHour()*60)+(mr.getRoute().getFirst().getMinutes());
                    int current = (day*24*60) + (hour*60) + (minutes);
                    if(start <= current && current < end) {
                        controller.tooltipUpdateIN(entry.getValue().getName() + " " + entry.getValue().getSerialNumber());
                        break;
                    } else
                        controller.tooltipUpdateOUT(entry.getValue().getName() + " " + entry.getValue().getSerialNumber());
                }
            }

        }
    }

    public Trip getTrip() {
        return trip;
    }
}
