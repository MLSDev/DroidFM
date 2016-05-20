package com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkPopularTrackResponse {

    @SerializedName("response")
    @Expose
    private List<Response> response = new ArrayList<>();

    /**
     * @return The response
     */
    public List<Response> getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(List<Response> response) {
        this.response = response;
    }

}


