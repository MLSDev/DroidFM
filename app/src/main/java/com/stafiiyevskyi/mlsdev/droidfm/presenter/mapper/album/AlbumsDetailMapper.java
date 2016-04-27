package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetail;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailMapper implements Func1<AlbumDetail, AlbumsDetailEntity> {
    @Override
    public AlbumsDetailEntity call(AlbumDetail albumDetail) {
        AlbumsDetailEntity entity = new AlbumsDetailEntity();
        entity.setName(albumDetail.getName());
        entity.setMbid(albumDetail.getMbid());
        entity.setArtistName(albumDetail.getArtist());
        if (albumDetail.getWiki() != null) {
            entity.setContent(albumDetail.getWiki().getContent());
            entity.setSummary(albumDetail.getWiki().getSummary());
            entity.setPublished(albumDetail.getWiki().getPublished());
        }

        List<TrackEntity> tracks = Observable.from(albumDetail.getTracks().getTrack())
                .map(albumsTrack -> {
                    TrackEntity entityTrack = new TrackEntity();
                    entityTrack.setName(albumsTrack.getName());
                    entityTrack.setArtistName(albumsTrack.getArtist().getName());
                    return entityTrack;
                }).toList().toBlocking().first();
        entity.setTracks(tracks);

        List<TagEntity> tags = Observable.from(albumDetail.getTags().getTag())
                .map(albumsTag -> {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setName(albumsTag.getName());
                    return tagEntity;
                })
                .toList().toBlocking().first();
        entity.setTags(tags);
        return entity;
    }
}
