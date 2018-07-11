package com.company.llp.spediteur.velodepiste;

public class AthleteLocationRequest {
    private int id;
    private String latitude;
    private String longitude;
    private String riderId;
    private String riderBibNo;
    private String locationRecievedOn;
    // getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderBibNo() {
        return riderBibNo;
    }

    public void setRiderBibNo(String riderBibNo) {
        this.riderBibNo = riderBibNo;
    }
    public void setLocationRecievedOn(String locationRecievedOn) {
        this.locationRecievedOn = locationRecievedOn;
    }
}
