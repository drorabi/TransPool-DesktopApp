package desktopApp.liveMap.Stations;

import java.util.HashMap;
import java.util.Map;

public class StationsManager {

    private Map<String, StationNode> stationsByCoordinates;
    private Map<String, StationNode> stationsByName;

    public StationsManager() {
        stationsByCoordinates = new HashMap<>();
        stationsByName = new HashMap<>();
    }

    public void addStationNode(StationNode station) {
        stationsByCoordinates.put(station.getX() + "," + station.getY(), station);
        stationsByName.put(station.getName(), station);
    }

    public StationNode getStationNode(String name) {
        return stationsByName.get(name);
    }

    public Map<String, StationNode> getStationsByName() {
        return stationsByName;
    }

    public void setOptionalStationsButtons(String[] optionalNextStations, String[] route) {

        disableRedStationsButtons(false);

        int index;
        for (index = 0; index < optionalNextStations.length; index++) {
            if (stationsByName.containsKey(optionalNextStations[index])) {
                stationsByName.get(optionalNextStations[index]).getController().getGreenStationButton().setVisible(true);
                stationsByName.get(optionalNextStations[index]).getController().getBlackStationButton().setVisible(false);
            }
        }
        for (index=0;index<route.length;index++)
            stationsByName.get(route[index].trim()).getController().getYellowStationButton().setVisible(true);
    }

    public void disableRedStationsButtons(boolean b) {
        for (Map.Entry<String, StationNode> entry : stationsByName.entrySet()) {
            entry.getValue().getController().getRedStationButton().setVisible(b);
            entry.getValue().getController().getYellowStationButton().setVisible(b);
            entry.getValue().getController().getGreenStationButton().setVisible(b);
            entry.getValue().getController().getBlackStationButton().setVisible(true);
        }
    }

    public void backToRedButtons() {
        for (Map.Entry<String, StationNode> entry : stationsByName.entrySet()) {
            entry.getValue().getController().getRedStationButton().setVisible(true);
            entry.getValue().getController().getYellowStationButton().setVisible(false);
            entry.getValue().getController().getGreenStationButton().setVisible(false);
            entry.getValue().getController().getBlackStationButton().setVisible(false);
        }
    }
}