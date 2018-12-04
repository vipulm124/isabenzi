
package com.example.isebenzi.business.objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultsResponse {

    @SerializedName("providers")
    @Expose
    private List<Provider> providers = null;

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

}
