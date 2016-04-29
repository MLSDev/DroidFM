package com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 29.04.16.
 */
public class VKTrackResponse {
    @SerializedName("response")
    private VkTrackItemResponse[] response;

    public VkTrackItemResponse[] getResponse() {
        return response;
    }

    public void setResponse(VkTrackItemResponse[] response) {
        this.response = response;
    }
}
