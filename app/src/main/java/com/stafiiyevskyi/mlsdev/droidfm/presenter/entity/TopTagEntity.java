package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopTagEntity {
    private String name;
    private String taggings;
    private String reach;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaggings() {
        return taggings;
    }

    public void setTaggings(String taggings) {
        this.taggings = taggings;
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach;
    }
}
