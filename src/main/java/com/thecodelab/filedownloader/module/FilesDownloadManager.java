package com.thecodelab.filedownloader.module;

import com.google.inject.Inject;
import com.thecodelab.filedownloader.config.Config;
import com.thecodelab.filedownloader.downloader.FileDownloader;
import com.thecodelab.filedownloader.downloader.exception.FileDownloaderInitializationException;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the main controller, it will take the configurations
 * and create an {@link ExecutorService} to execute download tasks.
 *
 * @author Moath
 */
public class FilesDownloadManager {

    private static final Logger LOGGER = Logger.getLogger(FilesDownloadManager.class);

    private Config config;
    private String downloadsDirectory;
    private ExecutorService executor;
    private FileDownloader fileDownloader;

    @Inject
    public FilesDownloadManager(Config config, FileDownloader fileDownloader) {
        this.config = config;
        this.downloadsDirectory = config.getDownloadDirectory();
        createDownloadDirectory();

        executor = Executors.newFixedThreadPool(config.getThreadsPoolSize());
        this.fileDownloader = fileDownloader;
    }

    private void createDownloadDirectory() {
        if (!new File(downloadsDirectory).mkdirs() && !new File(downloadsDirectory).exists()) {
            throw new FileDownloaderInitializationException();
        }

        LOGGER.info("All files will be downloaded to " + downloadsDirectory);
    }

    public void downloadAll() {
        List<Callable<Void>> tasks = new ArrayList<>();
        config.getUrls().forEach(urlString -> {
            try {
                final URL url = new URL(urlString);
                tasks.add(fileDownloader.download(url, new File(downloadsDirectory)));
            } catch (Exception e) {
                LOGGER.error("Error while downloading file from url: " + urlString, e);
            }
        });

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            LOGGER.error("Error while shutting down the files downloader!", e);
        } finally {
            executor.shutdownNow();
        }

    }

}