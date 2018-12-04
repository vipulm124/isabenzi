
package com.example.isebenzi.business.objects;

import com.example.isebenzi.business.objects.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("passportpath")
    @Expose
    private String passportpath;
    @SerializedName("User")
    @Expose
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassportpath() {
        return passportpath;
    }

    public void setPassportpath(String passportpath) {
        this.passportpath = passportpath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
