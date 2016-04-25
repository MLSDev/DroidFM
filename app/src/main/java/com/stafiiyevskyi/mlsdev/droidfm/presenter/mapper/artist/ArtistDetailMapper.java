package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.ArtistDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.ArtistInfoResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.TagArtistDetails;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistDetailMapper implements Func1<ArtistInfoResponse, ArtistDetailEntity> {
    @Override
    public ArtistDetailEntity call(ArtistInfoResponse artistInfoResponse) {
        ArtistDetail artistDetail = artistInfoResponse.getArtist();
        ArtistDetailEntity entity = new ArtistDetailEntity();

        entity.setName(artistDetail.getName());
        entity.setContent(artistDetail.getBio().getContent());
        entity.setPublished(artistDetail.getBio().getPublished());
        entity.setSummary(artistDetail.getBio().getSummary());
        entity.setPlaycount(artistDetail.getStats().getPlaycount());
        entity.setListeners(artistDetail.getStats().getListeners());

        List<TagEntity> tags = new ArrayList<>();
        for (TagArtistDetails tagArtist : artistDetail.getTags().getTag()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setName(tagArtist.getName());
            tags.add(tagEntity);
        }
        entity.setTags(tags);

        return entity;
    }
}
