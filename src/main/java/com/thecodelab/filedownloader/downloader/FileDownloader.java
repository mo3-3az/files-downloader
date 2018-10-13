package com.thecodelab.filedownloader.downloader;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Callable;

public interface FileDownloader {

    /**
     * This method should download the file from the passed URL to the passed directory.
     * If the process fails while downloading for whatever reason it should delete the
     * partially downloaded file.
     * <p>
     * It should return a callable which will download the file when invoked.
     */
    Callable<Void> download(URL url, File downloadDirectory);

}
