package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.DroidFMApplication;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.table.Tables;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBAlbumModel;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by oleksandr on 10.05.16.
 */
public class DBAlbumModelImpl implements DBAlbumModel {
    private AddAlbumToDBCallback listener;

    public DBAlbumModelImpl(AddAlbumToDBCallback listener) {
        this.listener = listener;
    }

    @Override
    public Observable<List<FavoriteAlbumDAO>> getFavoriteAlbums() {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.allObjectsSorted(FavoriteAlbumDAO.class, Tables.FavoriteAlbum.ARTIST_NAME, Sort.ASCENDING)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteAlbumDAOs -> favoriteAlbumDAOs.subList(0, favoriteAlbumDAOs.size()));
    }

    @Override
    public Observable<List<FavoriteAlbumDAO>> findAlbum(String artistName, String albumName) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        return realm.where(FavoriteAlbumDAO.class)
                .equalTo(Tables.FavoriteAlbum.ARTIST_NAME, artistName)
                .equalTo(Tables.FavoriteAlbum.ALBUM_NAME, albumName)
                .findAll()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .map(favoriteAlbumDAOs -> favoriteAlbumDAOs.subList(0, favoriteAlbumDAOs.size()));
    }

    @Override
    public void addFavoriteAlbum(FavoriteAlbumDAO album) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        FavoriteAlbumDAO trackDAO = new FavoriteAlbumDAO();
        trackDAO.setId(UUID.randomUUID().toString());
        trackDAO.setArtist_name(album.getArtist_name());
        trackDAO.setAlbum_name(album.getAlbum_name());
        realm.copyToRealm(trackDAO);
        realm.commitTransaction();
        listener.onSuccess();
    }

    @Override
    public void deleteFromFavorites(FavoriteAlbumDAO album) {
        Realm realm = Realm.getInstance(DroidFMApplication.getInstance());
        realm.beginTransaction();
        realm.where(FavoriteAlbumDAO.class)
                .equalTo(Tables.FavoriteAlbum.ARTIST_NAME, album.getArtist_name())
                .equalTo(Tables.FavoriteAlbum.ALBUM_NAME, album.getAlbum_name())
                .findAll().removeLast();
        realm.commitTransaction();
        listener.onSuccess();
    }

    public interface AddAlbumToDBCallback {
        void onSuccess();
    }
}
