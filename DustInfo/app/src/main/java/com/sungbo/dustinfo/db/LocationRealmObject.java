package com.sungbo.dustinfo.db;

import io.realm.RealmObject;

public class LocationRealmObject extends RealmObject {
    private String name;
    private double lat;
    private double lng;

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
