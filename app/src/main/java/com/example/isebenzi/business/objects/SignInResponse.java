
package com.example.isebenzi.business.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("User")
    @Expose
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
