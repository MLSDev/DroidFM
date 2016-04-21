package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ImageEntity {

    private String imageUrl;

    private String size;


    public String getText() {
        return imageUrl;
    }


    public void setText(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}

