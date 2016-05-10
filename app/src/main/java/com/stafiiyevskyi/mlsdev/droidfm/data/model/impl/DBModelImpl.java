package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.DroidFMApplication;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.table.Tables;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBModel;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 06.05.16.
 */
public class DBModelImpl implements DBModel {
    @Override
    public Observable<List<FavoriteTrackDAO>> getFavoriteTracks() {

        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.allObjectsSorted(FavoriteTrackDAO.class, Tables.FavoriteTrack.ARTIST_NAME, Sort.ASCENDING)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(favoriteTrackDAOs -> favoriteTrackDAOs.subList(0, favoriteTrackDAOs.size() - 1));
    }

    @Override
    public void addFavoriteTrack(FavoriteTrackDAO track) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        FavoriteTrackDAO trackDAO = new FavoriteTrackDAO();
        trackDAO.setId(UUID.randomUUID().toString());
        trackDAO.setArtist_name(track.getArtist_name());
        trackDAO.setTrack_name(track.getTrack_name());
        realm.copyToRealm(trackDAO);
        realm.commitTransaction();
    }
}
