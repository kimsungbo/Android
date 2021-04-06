package com.sungbo.findtheway.routeData;

public class RouteWalk {
    private String travel_mode;
    private String duration;
    private String distance;
    private String instruction;

    public RouteWalk(){}

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDirection(){
        if (instruction.equals("최종목적지")){
            return duration + "분간 " + travel_mode + "으로 " + distance + "를 이동하여 " + instruction +"도착. ";

        }
        return duration + "분간 " + travel_mode + "으로 " + distance + "를 이동하여 " + instruction +". ";
    }
}
