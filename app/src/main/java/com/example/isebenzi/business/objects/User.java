
package com.example.isebenzi.business.objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("SessionId")
    @Expose
    private String sessionId;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Firstname")
    @Expose
    private String firstname;
    @SerializedName("Lastname")
    @Expose
    private String lastname;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Passport")
    @Expose
    private String passport;
    @SerializedName("Locations")
    @Expose
    private List<Object> locations = null;
    @SerializedName("Availibility")
    @Expose
    private Availibility availibility;
    @SerializedName("Bank")
    @Expose
    private Bank bank;
    @SerializedName("Ratings")
    @Expose
    private List<Object> ratings = null;
    @SerializedName("Balance")
    @Expose
    private Integer balance;
    @SerializedName("AverageRating")
    @Expose
    private String averageRating;
    @SerializedName("Photo")
    @Expose
    private String photo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

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

    public List<Object> getLocations() {
        return locations;
    }

    public void setLocations(List<Object> locations) {
        this.locations = locations;
    }

    public Availibility getAvailibility() {
        return availibility;
    }

    public void setAvailibility(Availibility availibility) {
        this.availibility = availibility;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Object> getRatings() {
        return ratings;
    }

    public void setRatings(List<Object> ratings) {
        this.ratings = ratings;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
