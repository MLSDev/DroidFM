package com.stafiiyevskyi.mlsdev.droidfm.view.util;

/**
 * Created by oleksandr on 27.04.16.
 */
public final class LinkUtil {
    private LinkUtil() {
    }

    public static String getHtmlLink(String content, String link) {
        return "<a href=\"" + link + "\">" + content + "</a>";
    }
}
