package com.thecodelab.filedownloader.downloader;

import com.google.inject.Inject;
import com.thecodelab.filedownloader.downloader.helper.FileManager;
import com.thecodelab.filedownloader.reader.UrlReader;
import com.thecodelab.filedownloader.writer.StreamWriter;
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
    public Callable<Void> download(URL url, File downloadDirectory) {
        return () -> downloadFile(url, downloadDirectory);
    }

    private Void downloadFile(URL url, File downloadDirectory) {
        FileManager fileManager = new FileManager(url, downloadDirectory);
        try {
            final String fileName = fileManager.getFileName();
            final String fileRelPath = fileManager.getFileRelPath();

            LOGGER.info("Download started from URL: " + url.toString());

            final File targetFile = new File(fileRelPath);
            writer.write(reader.read(url), targetFile);

            LOGGER.info("File from URL: " + url.toString() + " was downloaded successfully as: " + fileName);
        } catch (IOException e) {
            LOGGER.error("Error while downloading a file from url: " + url, e);
            if (!fileManager.cleanup()) {
                LOGGER.error("Error while deleting a partially downloaded file from url: " + url);
            }
        }

        return null;
    }

}
