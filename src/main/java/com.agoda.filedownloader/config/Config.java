package com.agoda.filedownloader.config;

import java.util.List;

/**
 * This describes the application configurations.
 */
public interface Config {

    List<String> getUrls();

    String getDownloadDirectory();

    String getSftpUsername();

    String getSftPassword();

    int getConnectionTimeout();

    int getReadTimeout();

    int getThreadsPoolSize();

    int getIOBufferSize();

}
