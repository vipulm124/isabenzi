
package com.example.isebenzi.business.objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRatingResponse {

    @SerializedName("ratings")
    @Expose
    private List<Rating> ratings = null;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

}
