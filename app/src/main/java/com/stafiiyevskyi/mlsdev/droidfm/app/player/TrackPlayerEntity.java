package com.stafiiyevskyi.mlsdev.droidfm.app.player;

/**
 * Created by oleksandr on 04.05.16.
 */
public class TrackPlayerEntity {
    private String mTrackUrl;
    private String mTrackName;
    private String mAlbumImageUrl;
    private String mArtistName;

    public String getmTrackUrl() {
        return mTrackUrl;
    }

    public void setmTrackUrl(String mTrackUrl) {
        this.mTrackUrl = mTrackUrl;
    }

    public String getmTrackName() {
        return mTrackName;
    }

    public void setmTrackName(String mTrackName) {
        this.mTrackName = mTrackName;
    }

    public String getmAlbumImageUrl() {
        return mAlbumImageUrl;
    }

    public void setmAlbumImageUrl(String mAlbumImageUrl) {
        this.mAlbumImageUrl = mAlbumImageUrl;
    }

    public String getmArtistName() {
        return mArtistName;
    }

    public void setmArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }
}
