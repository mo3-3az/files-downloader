package com.agoda.filedownloader.downloader.exception;

/**
 * This will be thrown if an error happens while initializing the file downloader.
 * Mainly due to a failure of creation the downloads directory,.
 *
 * @author Moath
 */
public class FileDownloaderInitializationException extends RuntimeException {

    public FileDownloaderInitializationException() {
        super("Error while initializing file downloader! Couldn't create the download directory!");
    }
}
