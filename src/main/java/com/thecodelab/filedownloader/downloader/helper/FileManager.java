package com.thecodelab.filedownloader.downloader.helper;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;

/**
 * This class will extract the file from the url and handle the cleanup when needed.
 *
 * @author Moath
 */
public class FileManager {

    private URL url;
    private File directory;

    public FileManager(URL url, File directory) {
        if (url == null) {
            throw new IllegalArgumentException("Url is null!");
        }

        if (directory == null) {
            throw new IllegalArgumentException("Directory is null!");
        }

        this.url = url;
        this.directory = directory;
    }

    public String getFileName() {
        return FilenameUtils.getName(url.getPath());
    }

    public String getFileRelPath() {
        return directory + File.separator + getFileName();
    }

    public boolean cleanup() {
        if (StringUtils.isBlank(getFileName())) {
            return true;
        }

        final String fileRelPath = getFileRelPath();
        final File partiallyDownloaded = new File(fileRelPath);
        if (!partiallyDownloaded.exists()) {
            return true;
        }

        return partiallyDownloaded.delete();
    }
}
