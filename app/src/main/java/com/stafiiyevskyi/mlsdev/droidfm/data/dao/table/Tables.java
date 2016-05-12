package com.stafiiyevskyi.mlsdev.droidfm.data.dao.table;

/**
 * Created by oleksandr on 06.05.16.
 */
public interface Tables {

    interface FavoriteTrack {
        String ID = "id";
        String TRACK_NAME = "track_name";
        String ARTIST_NAME = "artist_name";
    }

    interface FavoriteAlbum {
        String ID = "id";
        String ALBUM_NAME = "album_name";
        String ARTIST_NAME = "artist_name";
        String MBID = "mbid";
    }

    interface FavoriteArtist {
        String ID = "id";
        String NAME = "name";
        String MBID = "mbid";
        String IMAGE = "image";
    }

    interface Playlist {
        String ID = "id";
        String NAME = "name";
        String TRACKS = "tracks";
    }
}
