
package com.example.isebenzi.business.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pass_photo")
    @Expose
    private String passPhoto;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private Object updatedOn;
    @SerializedName("AverageRating")
    @Expose
    private String averageRating;
    @SerializedName("Availibility")
    @Expose
    private Availibility availibility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassPhoto() {
        return passPhoto;
    }

    public void setPassPhoto(String passPhoto) {
        this.passPhoto = passPhoto;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Object getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Object updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public Availibility getAvailibility() {
        return availibility;
    }

    public void setAvailibility(Availibility availibility) {
        this.availibility = availibility;
    }

}
