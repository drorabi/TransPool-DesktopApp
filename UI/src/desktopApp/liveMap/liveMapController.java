package desktopApp.liveMap;

import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.graph.PannableCanvas;
import desktopApp.liveMap.Stations.StationNode;
import desktopApp.liveMap.Stations.StationsManager;
import desktopApp.liveMap.cars.CarNode;
import desktopApp.liveMap.cars.CarsManager;
import desktopApp.liveMap.roads.ArrowedEdge;
import desktopApp.liveMap.timeToolBar.timeToolBarController;
import desktopApp.mainPage.mainPageBodyController;
import engine.converted.classes.*;
import engine.ui.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.util.HashMap;
import java.util.Map;

public class liveMapController {

    private CarsManager carsManager;

    private Map<String, CarNode> carByCoordinates;

    @FXML private AnchorPane mapAnchorPane;

    private PannableCanvas graphMap;

    private Map<String,ArrowedEdge> edgeManager;

    private StationsManager stationsManager;

    private Graph graph;

    private Model model;
    @FXML
    private ScrollPane scrollPaneComponent;

    @FXML
    private ImageView  mapImg;

    @FXML
    private AnchorPane timeToolBarComponent;

    @FXML
    private timeToolBarController timeToolBarComponentController;

    private mainPageBodyController mainBodyController;


    @FXML
    public void initialize() {

        if (timeToolBarComponentController != null)
            timeToolBarComponentController.setMainController(this);


    }

    public void setMainController(mainPageBodyController mainBodyController) {
        this.mainBodyController = mainBodyController;
    }

    public void setLiveMap() {
        carsManager=new CarsManager(this);
        edgeManager=new HashMap<>();
        graph = new Graph();
        model = graph.getModel();
        stationsManager = new StationsManager();
        graph.beginUpdate();

        for(Map.Entry<String, Station> entry : Engine.getData().getMapData().getStations().getStations().entrySet()) {
           StationNode st=new StationNode(entry.getValue(),this);
           model.addCell(st);
           graph.getGraphic(st).relocate(st.getX(),st.getY());
           stationsManager.addStationNode(st);
        }
        for(Map.Entry<String, Trail> entry : Engine.getData().getMapData().getTrails().getTrails().entrySet()) {
            ArrowedEdge newRoad = new ArrowedEdge(stationsManager.getStationNode(entry.getValue().getFrom()), stationsManager.getStationNode(entry.getValue().getTo()));
            newRoad.textProperty().set(" ");
            model.addEdge(newRoad);
            edgeManager.put(entry.getKey(),newRoad);
        }

        for(Map.Entry<Integer, Trip> entry : Engine.getData().getPlannedTrips().getTrips().entrySet()){
            int i;
            for(i=0 ; i<entry.getValue().getRide().length;i++)
                stationsManager.getStationNode(entry.getValue().getRide()[i].getName()).updateStationDetails(entry.getValue(),entry.getValue().getRide()[i]);

            CarNode car = new CarNode(entry.getValue(), this);
            model.addCell(car);
            graph.getGraphic(car).relocate(car.getX(), car.getY());
            graph.getGraphic(car).setVisible(false);
            carsManager.addCar(entry.getValue(), car);
        }

        graph.endUpdate();;
        graphMap=graph.getCanvas();
        scrollPaneComponent.setContent(graphMap);
        graphMap.setId("washingtonBG");


        for(Map.Entry<String,ArrowedEdge> entry: edgeManager.entrySet())
            entry.getValue().getLine().getStyleClass().add("line2");
        graph.getUseViewportGestures().set(false);
        graph.getUseNodeGestures().set(false);

        mapAnchorPane.getStyleClass().add("washington");
    }

    public timeToolBarController getTimeToolBarComponentController() {
        return timeToolBarComponentController;
    }

    public AnchorPane getTimeToolBarComponent() {
        return timeToolBarComponent;
    }

    public mainPageBodyController getMainBodyController() {
        return mainBodyController;
    }

    public StationsManager getStationsManager() {
        return stationsManager;
    }

    public void stationButtonOnAction(StationNode stationNode){
        mainBodyController.getAddTripComponentController().updateRouteLabel(stationNode.getName());
    }

    public void setOptionalStations(String[] optionalNextStations, String[] route) {
        stationsManager.setOptionalStationsButtons(optionalNextStations, route);
        makeRouteYellow(route);
    }

    public void makeRouteYellow(String[] route) {
        if (route.length > 1) {
            for (int index = 0; index < route.length - 1; index++)
                if (edgeManager.containsKey(route[index] + route[index + 1])) {
                    edgeManager.get(route[index] + route[index + 1]).getLine().getStyleClass().remove(0);
                    edgeManager.get(route[index] + route[index + 1]).getLine().getStyleClass().add("line1");
                }
                else {
                    edgeManager.get(route[index + 1] + route[index]).getLine().getStyleClass().remove(0);
                    edgeManager.get(route[index + 1] + route[index]).getLine().getStyleClass().add("line1");
                }
        }
    }

    public void disableStationsButtons(boolean b) {
        stationsManager.disableRedStationsButtons(b);
    }

    public Graph getGraph() {
        return graph;
    }

    public PannableCanvas getGraphMap() {
        return graphMap;
    }

    public Map<String, ArrowedEdge> getEdgeManager() {
        return edgeManager;
    }


    public void updateGraphCanvas(int minutes,int hour,int day) {

        carByCoordinates =new HashMap<>();

        for(Map.Entry<Trip,CarNode> entry : carsManager.getCarByTrip().entrySet()) {
            carsManager.calculateCoordinatesForCar(carByCoordinates, entry.getKey(), minutes, hour,day, entry.getValue());
            graph.getGraphic(entry.getValue()).relocate(entry.getValue().getX(), entry.getValue().getY());
        }
        graph.endUpdate();
        graphMap = graph.getCanvas();
        scrollPaneComponent.setContent(graphMap);

    }

    public void updateTripOnMap(Trip trip){
        int i;
        for(i=0 ; i<trip.getRide().length;i++)
            stationsManager.getStationNode(trip.getRide()[i].getName()).updateStationDetails(trip,trip.getRide()[i]);
        CarNode car = new CarNode(trip, this);
        model.addCell(car);
        graph.getGraphic(car).relocate(car.getX(), car.getY());
        graph.getGraphic(car).setVisible(false);
        carsManager.addCar(trip, car);
        graph.endUpdate();;
        graphMap=graph.getCanvas();
        scrollPaneComponent.setContent(graphMap);
    }

    public void stationTooltipUpdate(CombinedTrip matchedRide, String name) {
        for(MatchedRide mr : matchedRide.getTrip()){
            String tool= name;
            stationsManager.getStationsByName().get(mr.getRoute().getFirst().getName()).tooltipUpdate(name + " is picked up at " + mr.getRoute().getFirst().getDateAndTime() );
            stationsManager.getStationsByName().get(mr.getRoute().getLast().getName()).tooltipUpdate(name + " is dropped off at " + mr.getRoute().getLast().getDateAndTime() );

        }
    }
}
