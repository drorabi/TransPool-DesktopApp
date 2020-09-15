package desktopApp.liveMap.cars;

import desktopApp.liveMap.liveMapController;
import engine.converted.classes.Station;
import engine.converted.classes.Trip;
import engine.ui.Engine;

import java.util.HashMap;
import java.util.Map;

public class CarsManager {

    private liveMapController mainController;
    private Map<Trip, CarNode> carByTrip;


    public CarsManager(liveMapController maoController){
        carByTrip=new HashMap<>();
        mainController=maoController;
    }

    public void addCar(Trip trip,CarNode car){
        carByTrip.put(trip,car);
    }

    public void calculateCoordinatesForCar(Map<String, CarNode> carByCoordinates, Trip trip, int minutes, int hour, int day, CarNode car) {



        int tripStart=stationToTimeInteger(trip.getRide()[0]),
                tripEnd=stationToTimeInteger(trip.getRide()[trip.getRide().length-1]),
                currentTime=dateToTimeInteger(day,hour,minutes),
                tempStartTime=tripStart, tempEndTime=tripEnd;

        while(tempEndTime < currentTime )
            tempEndTime+=((trip.getSchedule().recurrencesToInteger())*60*24);

        if(tripStart+(tempEndTime-tripEnd) <= currentTime && currentTime <= tempEndTime)
            calculateNewCoordinates(carByCoordinates, trip.getRide(),minutes,hour,day,car,tempEndTime-tripEnd);
        else
            mainController.getGraph().getGraphic(car).setVisible(false);
    }


    private void calculateNewCoordinates(Map<String, CarNode> carByCoordinates, Station[] ride, int minutes, int hour, int day, CarNode car, int timeDif) {


        car.tooltipUpdate(day,hour,minutes);

        int index = 0, length = 0;
        double  currentTime=dateToTimeInteger(day,hour,minutes), tempHour, percentage = 0;
        
        if (currentTime == stationToTimeInteger(ride[ride.length-1])+timeDif) {
            car.setX(ride[ride.length - 1].getCoordinate().getX());
            car.setY(ride[ride.length - 1].getCoordinate().getY());
            mainController.getGraph().getGraphic(car).setVisible(true);
            return;
        }

        while (( currentTime >= stationToTimeInteger(ride[index])+timeDif ))
            index++;

       double time= currentTime - (stationToTimeInteger(ride[index-1])+timeDif);
        if (Engine.getData().getMapData().getTrails().getTrails().containsKey(ride[index - 1].getName() + ride[index].getName()))
            length = Engine.getData().getMapData().getTrails().getTrails().get(ride[index - 1].getName() + ride[index].getName()).getHowMuchTime();

        else
            length = Engine.getData().getMapData().getTrails().getTrails().get(ride[index].getName() + ride[index - 1].getName()).getHowMuchTime();
        tempHour = (length / 60) * 100;
        length = length % 60;
        length += tempHour;
        percentage = time / length;
        
        double x= (ride[index - 1].getCoordinate().getX() + (percentage * (ride[index].getCoordinate().getX() - ride[index - 1].getCoordinate().getX())));
        double y= (ride[index - 1].getCoordinate().getY() + (percentage * (ride[index].getCoordinate().getY() - ride[index - 1].getCoordinate().getY())));

        if(carByCoordinates.containsKey(""+x+y)) {
            x += 0.31;
            if (carByCoordinates.containsKey("" + x + y)) {
                y += 0.31;
                if (carByCoordinates.containsKey("" + x + y))
                    x -= 0.31;
            }
        }
        carByCoordinates.put(""+x+y,car);
        car.setX(x);
        car.setY(y);
        mainController.getGraph().getGraphic(car).setVisible(true);
    }


    private int dateToTimeInteger(int day, int hour, int minutes){
        return (((day)*24*60) + (hour*60) + (minutes));
    }

    private int stationToTimeInteger(Station station){
        return dateToTimeInteger(station.getDay(),station.getHour(),station.getMinutes());
    }

    public Map<Trip, CarNode> getCarByTrip() {
        return carByTrip;
    }


}
