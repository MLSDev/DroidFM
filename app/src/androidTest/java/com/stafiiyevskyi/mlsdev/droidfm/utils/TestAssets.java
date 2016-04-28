package com.stafiiyevskyi.mlsdev.droidfm.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestAssets {

    public static String getErrorResponse(Context context) {
        return getStringFromFile(context, "error_response.json");
    }

    public static String getTopArtistsResponse(Context context) {
        return getStringFromFile(context, "top_artists_response.json");
    }

    public static String getTopArtistsPageSecondResponse(Context context) {
        return getStringFromFile(context, "top_artists_second_page_response.json");
    }

    public static String getTopTracksResponse(Context context) {
        return getStringFromFile(context, "top_tracks_response.json");
    }

    public static String getTopTracksPageSecondResponse(Context context) {
        return getStringFromFile(context, "top_tracks_second_page_response.json");
    }

    public static String getTrackDetailResponse(Context context) {
        return getStringFromFile(context, "track_detail_response.json");
    }


    private static String getStringFromFile(Context context, String filePath) {
        String result = null;
        try {
            InputStream is = context.getResources().getAssets().open(filePath);
            result = convertStreamToString(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}


