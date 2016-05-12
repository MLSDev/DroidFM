package com.stafiiyevskyi.mlsdev.droidfm.data.dao;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.PlaylistDAO;

import io.realm.annotations.RealmModule;

/**
 * Created by oleksandr on 06.05.16.
 */
@RealmModule(classes = {FavoriteTrackDAO.class, FavoriteAlbumDAO.class, FavoriteArtistDAO.class, PlaylistDAO.class})
public class DroidFMRealmModule {
}
