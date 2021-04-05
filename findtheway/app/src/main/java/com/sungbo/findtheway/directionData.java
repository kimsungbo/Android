package com.sungbo.findtheway;

import android.util.Log;

import com.sungbo.findtheway.data.Direction;
import com.sungbo.findtheway.data.Step;
import com.sungbo.findtheway.data.Step__1;
import com.sungbo.findtheway.routeData.RouteTransit;
import com.sungbo.findtheway.routeData.RouteWalk;

import java.util.List;

public class directionData {

    public static String getRoutes(Direction direction){
        String return_string = "";
        List<Step> step = direction.getRoutes().get(0).getLegs().get(0).getSteps();

        for(int i = 0 ; i < step.size(); i++){
            Step s = step.get(i);

            if (step.get(i).getTravelMode().equals("WALKING")){
                RouteWalk routeWalk = new RouteWalk();
                routeWalk.setDistance(s.getDistance().getText());
                routeWalk.setDuration(s.getDuration().getText().split(" ")[0]);
                routeWalk.setTravel_mode(s.getTravelMode());
                routeWalk.setInstruction(s.getHtmlInstructions());

                return_string += routeWalk.getDirection();
            }
            else if (s.getTravelMode().equals("TRANSIT")){
                RouteTransit routeTransit = new RouteTransit();
                routeTransit.setInstruction(s.getHtmlInstructions());
                routeTransit.setArrival_station(s.getTransitDetails().getArrivalStop().getName());
                routeTransit.setDeparture_station(s.getTransitDetails().getDepartureStop().getName());
                routeTransit.setTravel_mode(s.getTravelMode());
                routeTransit.setDuration(s.getDuration().getText().split(" ")[0]);
                routeTransit.setDistance(s.getDistance().getText());
                routeTransit.setLine(s.getTransitDetails().getLine().getShortName());
                routeTransit.setNum_stations(s.getTransitDetails().getNumStops());
                routeTransit.setTravel_type(s.getTransitDetails().getLine().getVehicle().getType());

                return_string += routeTransit.getDirection();

            }

        }
        return return_string;
    }
}
