package com.agoda.filedownloader.downloader;

import com.agoda.filedownloader.downloader.helper.FileManager;
import com.agoda.filedownloader.reader.UrlReader;
import com.agoda.filedownloader.writer.StreamWriter;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * This is an implementation of the file downloader which makes use of
 * {@link UrlReader} and {@link StreamWriter}.
 *
 * @author Moath
 */
public class FileDownloaderImpl implements FileDownloader {

    private static final Logger LOGGER = Logger.getLogger(FileDownloaderImpl.class);

    private UrlReader reader;
    private StreamWriter writer;

    @Inject
    public FileDownloaderImpl(UrlReader reader, StreamWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Callable<Void> download(URL url, String downloadDirectory) {
        return () -> downloadFile(url, null, null, downloadDirectory);
    }

    @Override
    public Callable<Void> downloadSecure(URL url, String username, String password, String downloadDirectory) {
        return () -> downloadFile(url, username, password, downloadDirectory);
    }

    private Void downloadFile(URL url, String username, String password, String downloadDirectory) {
        FileManager fileManager = new FileManager(url, downloadDirectory);
        try {
            final String fileName = fileManager.getFileName();
            final String fileRelPath = fileManager.getFileRelPath();

            LOGGER.info("Download started from URL: " + url.toString());

            final File targetFile = new File(fileRelPath);
            if (StringUtils.isAnyBlank(username, password)) {
                writer.write(reader.read(url), targetFile);
            } else {
                writer.write(reader.readSecure(url, username, password), targetFile);
            }

            LOGGER.info("File from URL: " + url.toString() + " was downloaded successfully as: " + fileName);
        } catch (IOException e) {
            LOGGER.error("Error while downloading a file from url: " + url, e);
            if (!fileManager.cleanup()) {
                LOGGER.error("Error while deleting a partially downloaded file from url: " + url);
            }
            Thread.currentThread().interrupt();
        }

        return null;
    }

}
