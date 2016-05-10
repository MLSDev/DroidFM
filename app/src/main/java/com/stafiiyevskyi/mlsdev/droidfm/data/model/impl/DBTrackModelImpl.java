package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.DroidFMApplication;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.table.Tables;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBTrackModel;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by oleksandr on 06.05.16.
 */
public class DBTrackModelImpl implements DBTrackModel {

    private AddTrackToDBCallback listener;

    public DBTrackModelImpl(AddTrackToDBCallback listener) {
        this.listener = listener;
    }

    @Override
    public Observable<List<FavoriteTrackDAO>> getFavoriteTracks() {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.allObjectsSorted(FavoriteTrackDAO.class, Tables.FavoriteTrack.ARTIST_NAME, Sort.ASCENDING)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteTrackDAOs -> favoriteTrackDAOs.subList(0, favoriteTrackDAOs.size()));
    }

    @Override
    public Observable<List<FavoriteTrackDAO>> findTracks(String artistName, String trackName) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.where(FavoriteTrackDAO.class)
                .equalTo(Tables.FavoriteTrack.ARTIST_NAME, artistName)
                .equalTo(Tables.FavoriteTrack.TRACK_NAME, trackName)
                .findAll()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteTrackDAOs -> favoriteTrackDAOs.subList(0, favoriteTrackDAOs.size()));
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
        listener.onSuccess();
    }

    @Override
    public void deleteFromFavorites(FavoriteTrackDAO track) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        realm.where(FavoriteTrackDAO.class)
                .equalTo(Tables.FavoriteTrack.ARTIST_NAME, track.getArtist_name())
                .equalTo(Tables.FavoriteTrack.TRACK_NAME, track.getTrack_name())
                .findAll().removeLast();
        realm.commitTransaction();
        listener.onSuccess();
    }

    public interface AddTrackToDBCallback {
        void onSuccess();
    }
}
