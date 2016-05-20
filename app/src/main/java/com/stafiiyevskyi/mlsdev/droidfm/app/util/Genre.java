package com.stafiiyevskyi.mlsdev.droidfm.app.util;

/**
 * Created by oleksandr on 19.05.16.
 */
public enum Genre {
    Rock(1, "Rock"), Pop(2, "Pop"), RapAndHipHop(3, "Rap & Hip-Hop"), EasyListening(4, "Easy Listening"),
    DanceAndHouse(5, "Dance & House"), Instrumental(6, "Instrumental"), Metal(7, "Metal"), Alternative(21, "Alternative"),
    Dubstep(8, "Dupstep"), JazzAndBlues(1001, "Jazz & Blues"), DrumAndBass(10, "Drum & Bass"), Trance(11, "Trance"), Chanson(12, "Chanson"),
    Ethnic(13, "Ethnic"), AcousticAndVocal(14, "Acoustic & Vocal"), Reggae(15, "Reggae"), Classical(16, "Classical"), IndiePop(17, "Indie Pop"),
    Speech(19, "Speech"), ElectropopAndDisco(22, "Electropop & Disco"), Other(18, "Other");

    private String genre;
    private int id;

    Genre(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return genre;
    }
}
