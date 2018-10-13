package com.thecodelab.filedownloader;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thecodelab.filedownloader.config.Config;
import com.thecodelab.filedownloader.downloader.FileDownloader;
import com.thecodelab.filedownloader.module.FilesDownloadManager;
import com.thecodelab.filedownloader.module.FilesDownloaderModule;

import java.io.File;

/**
 * This is the main entry point to the application.
 *
 * @author Moath
 */
public class Main {

    /**
     * You can optionally pass a path to config file.
     */
    public static void main(String[] args) {
        final FilesDownloaderModule filesDownloaderModule;
        if (args.length == 0) {
            filesDownloaderModule = new FilesDownloaderModule();
        } else {
            filesDownloaderModule = new FilesDownloaderModule(new File(args[0]));
        }

        final Injector injector = Guice.createInjector(filesDownloaderModule);
        final Config config = injector.getInstance(Config.class);
        FilesDownloadManager filesDownloader = new FilesDownloadManager(config, injector.getInstance(FileDownloader.class));
        filesDownloader.downloadAll();
    }

}
