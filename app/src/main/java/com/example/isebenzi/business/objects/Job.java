
package com.example.isebenzi.business.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("seeker_id")
    @Expose
    private String seekerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("per_day")
    @Expose
    private String perDay;
    @SerializedName("per_hour")
    @Expose
    private String perHour;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("persons_required")
    @Expose
    private String personsRequired;
    @SerializedName("seeker")
    @Expose
    private String seeker;
    @SerializedName("provider")
    @Expose
    private String provider;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(String seekerId) {
        this.seekerId = seekerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPerDay() {
        return perDay;
    }

    public void setPerDay(String perDay) {
        this.perDay = perDay;
    }

    public String getPerHour() {
        return perHour;
    }

    public void setPerHour(String perHour) {
        this.perHour = perHour;
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

    public String getPersonsRequired() {
        return personsRequired;
    }

    public void setPersonsRequired(String personsRequired) {
        this.personsRequired = personsRequired;
    }

    public String getSeeker() {
        return seeker;
    }

    public void setSeeker(String seeker) {
        this.seeker = seeker;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
