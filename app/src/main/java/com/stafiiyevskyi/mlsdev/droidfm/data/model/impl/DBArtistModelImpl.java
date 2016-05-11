package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.DroidFMApplication;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.table.Tables;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by oleksandr on 11.05.16.
 */
public class DBArtistModelImpl implements DBArtistModel {

    private TransactionCallback listener;

    public DBArtistModelImpl(TransactionCallback listener) {
        this.listener = listener;
    }

    @Override
    public Observable<List<FavoriteArtistDAO>> getFavoriteArtists() {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.allObjectsSorted(FavoriteArtistDAO.class, Tables.FavoriteArtist.NAME, Sort.ASCENDING)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteArtistDAOs -> favoriteArtistDAOs.subList(0, favoriteArtistDAOs.size()));
    }

    @Override
    public Observable<List<FavoriteArtistDAO>> findArtist(String artistName, String mbid) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.where(FavoriteArtistDAO.class)
                .equalTo(Tables.FavoriteArtist.NAME, artistName)
                .equalTo(Tables.FavoriteArtist.MBID, mbid)
                .findAll()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteArtistDAOs -> favoriteArtistDAOs.subList(0, favoriteArtistDAOs.size()));
    }

    @Override
    public void addFavoriteArtist(FavoriteArtistDAO artist) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        FavoriteArtistDAO trackDAO = new FavoriteArtistDAO();
        trackDAO.setId(UUID.randomUUID().toString());
        trackDAO.setName(artist.getName());
        trackDAO.setMbid(artist.getMbid());
        trackDAO.setImage(artist.getImage());
        realm.copyToRealm(trackDAO);
        realm.commitTransaction();
        listener.onSuccess();
    }

    @Override
    public void deleteFromFavorites(FavoriteArtistDAO artist) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        realm.where(FavoriteArtistDAO.class)
                .equalTo(Tables.FavoriteArtist.NAME, artist.getName())
                .equalTo(Tables.FavoriteArtist.MBID, artist.getMbid())
                .findAll().removeLast();
        realm.commitTransaction();
        listener.onSuccess();
    }
}
