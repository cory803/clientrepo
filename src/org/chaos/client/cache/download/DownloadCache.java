package org.chaos.client.cache.download;

import org.chaos.client.Client;

/**
 * Created by Jonathan on 9/10/2016.
 */

public class DownloadCache {

    public static void showDownloadScreen() {
        Client.instance.processCacheScreen();
    }

    public static boolean needsCache() {
        return false;
    }

    public static void continueToStart() {
        Client.instance.startUp();
    }

}
