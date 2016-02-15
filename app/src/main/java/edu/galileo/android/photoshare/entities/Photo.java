package edu.galileo.android.photoshare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by ykro.
 */
public class Photo {
    @JsonIgnore
    private String id;

    private String url;
    private String email;
    private double latitutde;
    private double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(double latitutde) {
        this.latitutde = latitutde;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
