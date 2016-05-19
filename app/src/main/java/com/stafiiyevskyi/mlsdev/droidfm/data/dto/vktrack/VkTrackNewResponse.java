package com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkTrackNewResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     *
     * @return
     * The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

}