
package com.example.isebenzi.business.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("seeker_id")
    @Expose
    private String seekerId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private Object updatedOn;
    @SerializedName("seeker")
    @Expose
    private String seeker;

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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getSeeker() {
        return seeker;
    }

    public void setSeeker(String seeker) {
        this.seeker = seeker;
    }

}
