package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteListAlbumsFromDAOMapper implements Func1<List<FavoriteAlbumDAO>, List<FavoriteAlbumEntity>> {
    @Override
    public List<FavoriteAlbumEntity> call(List<FavoriteAlbumDAO> favoriteAlbumDAOs) {
        return Observable.from(favoriteAlbumDAOs)
                .map(new FavoriteAlbumFromDAOMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
