package com.google.ceylonbus;

public class BusLocation {
    double lat;
    double lon;
    String bus;
    String driver;

    public BusLocation(){}

    public BusLocation(double lat, double lon, String bus, String driver) {
        this.lat = lat;
        this.lon = lon;
        this.bus = bus;
        this.driver = driver;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
