package desktopApp.liveMap.Stations;

import com.fxgraph.cells.AbstractCell;
import com.fxgraph.graph.Graph;
import desktopApp.liveMap.liveMapController;
import engine.converted.classes.Station;
import engine.converted.classes.Trip;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import java.net.URL;
import java.util.function.Supplier;

/*
Represents a node (called 'cell' in the fxGraph library) that is the station
Station has a coordinate (x,y), a name and a supplier of additional data to be shown when user clicks on it
Station is created from an independent fxml file along with its controller and style sheet
 */
   public class StationNode extends AbstractCell {

        private stationController controller;
        private liveMapController mapController;
        private int x;
        private int y;
        private String name;

        public StationNode(Station station, liveMapController mainController){
            this.x=station.getCoordinate().getX()*100;
            this.y=station.getCoordinate().getY()*100;
            this.name=station.getName();
            mapController=mainController;
        }

        public StationNode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setName(String name) {
            this.name = name;
        }


        public void updateStationDetails(Trip trip, Station station){


            controller.setName(name,this);

        }

       public String getName(){return name;}

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
    /*
    Creates the graphical representation of the station.
     */
        public Region getGraphic(Graph graph) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource("stationPane.fxml");
                fxmlLoader.setLocation(url);
                AnchorPane root = fxmlLoader.load(url.openStream());

                // updates information on the actual node's controller
                controller = fxmlLoader.getController();
                controller.setX(x);
                controller.setY(y);
                controller.setName(name,this);
               // controller.setDetailsDTOSupplier(detailsSupplier);

                return root;
            } catch (Exception e) {
                return new Label("Error when tried to create graphic node !");
            }
        }

    public stationController getController() {
        return controller;
    }

    public void stationButtonOnAction() {
        if (mapController.getMainBodyController().getIsAddTripVisible())
            mapController.stationButtonOnAction(this);
    }

    public void tooltipUpdate(String name){
            controller.tooltipUpdate(name);
    }
}