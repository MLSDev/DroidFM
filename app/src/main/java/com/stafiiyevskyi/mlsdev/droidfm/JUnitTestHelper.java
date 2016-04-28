package com.stafiiyevskyi.mlsdev.droidfm;

/**
 * Created by oleksandr on 28.04.16.
 */
public class JUnitTestHelper {
    private boolean isJUnitRunning = false;
    private static JUnitTestHelper instance;

    private JUnitTestHelper() {
    }

    public static JUnitTestHelper getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new JUnitTestHelper();
            return instance;
        }
    }

    public void setJUnitRunning() {
        isJUnitRunning = true;
    }

    public boolean isJunitRunning() {
        return isJUnitRunning;
    }
}
