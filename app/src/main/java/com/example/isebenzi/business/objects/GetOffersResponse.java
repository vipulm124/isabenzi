
package com.example.isebenzi.business.objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOffersResponse {

    @SerializedName("jobs")
    @Expose
    private List<Job> jobs = null;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}
