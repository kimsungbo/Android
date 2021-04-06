package com.sungbo.findtheway.routeData;

public class RouteTransit {
    private String travel_mode;
    private String duration;
    private String distance;
    private String instruction;
    private String departure_station;
    private String arrival_station;
    private String line;
    private Integer num_stations;
    private String travel_type;

    public RouteTransit(){}

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

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(String arrival_station) {
        this.arrival_station = arrival_station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getNum_stations() {
        return num_stations;
    }

    public void setNum_stations(Integer num_stations) {
        this.num_stations = num_stations;
    }

    public String getTravel_type() {
        return travel_type;
    }

    public void setTravel_type(String travel_type) {
        this.travel_type = travel_type;
    }

    public String getDirection(){
        String s = "";
        if (travel_type.equals("SUBWAY")){
            s = "지하철 " + line + "을 타고 " + departure_station +"에서 " + arrival_station +"역으로 총 " + duration + "분간 " + num_stations +"정거장 이동";
        }
        else{
            s = "버스 " + line + "를 타고 " + departure_station +"에서 " + arrival_station +"역으로 총 " + duration + "분간 " + num_stations +"정거장 이동";

        }

        if (instruction.equals("최종목적지")){
            s += "하여 최종목적지 도착. ";
        }
        else{
            s += ". ";
        }

        return s;
    }
}
