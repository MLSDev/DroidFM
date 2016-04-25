package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopTagEntity {
    private String mName;
    private String mTaggings;
    private String mReach;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getTaggings() {
        return mTaggings;
    }

    public void setTaggings(String taggings) {
        this.mTaggings = taggings;
    }

    public String getReach() {
        return mReach;
    }

    public void setReach(String reach) {
        this.mReach = reach;
    }
}
