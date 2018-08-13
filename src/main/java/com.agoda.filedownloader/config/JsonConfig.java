package com.agoda.filedownloader.config;

import com.agoda.filedownloader.config.exception.ConfigurationException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This implementation is reading the configurations from a JSON object.
 *
 * @author Moath
 */
public class JsonConfig implements Config {

    private static final String JSON_CONFIG_KEY_URLS = "urls";
    private static final String JSON_CONFIG_KEY_DOWNLOAD_DIRECTORY = "downloadDirectory";
    private static final String JSON_CONFIG_KEY_SFTP_USERNAME = "sftpUsername";
    private static final String JSON_CONFIG_KEY_SFTP_PASSWORD = "sftpPassword";
    private static final String JSON_CONFIG_KEY_CONNECTION_TIMEOUT = "connectionTimeout";
    private static final String JSON_CONFIG_KEY_READ_TIMEOUT = "readTimeout";
    private static final String JSON_CONFIG_KEY_THREAD_POOL_SIZE = "threadPoolSize";
    private static final String JSON_CONFIG_KEY_IO_BUFFER_SIZE = "ioBufferSize";

    private JsonObject config;

    private List<String> urls;
    private String downloadDirectory;
    private String sftpUsername;
    private String sftpPassword;
    private int connectionTimeout;
    private int readTimeout;
    private int threadPoolSize;
    private int ioBufferSize;

    public JsonConfig(JsonObject jsonObjectConfig) {
        if (jsonObjectConfig == null) {
            throw new IllegalArgumentException("Json object is null!");
        }

        config = jsonObjectConfig;
        init();
    }

    private void init() {
        checkConfiguration(JSON_CONFIG_KEY_URLS);
        JsonArray asJsonArray = config.getAsJsonArray(JSON_CONFIG_KEY_URLS);
        urls = new ArrayList<>(asJsonArray.size());
        asJsonArray.forEach(value -> urls.add(value.getAsString()));

        checkConfiguration(JSON_CONFIG_KEY_DOWNLOAD_DIRECTORY);
        downloadDirectory = config.get(JSON_CONFIG_KEY_DOWNLOAD_DIRECTORY).getAsString();

        checkConfiguration(JSON_CONFIG_KEY_SFTP_USERNAME);
        sftpUsername = config.get(JSON_CONFIG_KEY_SFTP_USERNAME).getAsString();

        checkConfiguration(JSON_CONFIG_KEY_SFTP_PASSWORD);
        sftpPassword = config.get(JSON_CONFIG_KEY_SFTP_PASSWORD).getAsString();

        checkConfiguration(JSON_CONFIG_KEY_CONNECTION_TIMEOUT);
        connectionTimeout = config.get(JSON_CONFIG_KEY_CONNECTION_TIMEOUT).getAsInt();

        checkConfiguration(JSON_CONFIG_KEY_READ_TIMEOUT);
        readTimeout = config.get(JSON_CONFIG_KEY_READ_TIMEOUT).getAsInt();

        checkConfiguration(JSON_CONFIG_KEY_THREAD_POOL_SIZE);
        threadPoolSize = config.get(JSON_CONFIG_KEY_THREAD_POOL_SIZE).getAsInt();

        checkConfiguration(JSON_CONFIG_KEY_IO_BUFFER_SIZE);
        ioBufferSize = config.get(JSON_CONFIG_KEY_IO_BUFFER_SIZE).getAsInt();
    }

    private void checkConfiguration(String configJsonKey) {
        if (!config.has(configJsonKey)) {
            throw new ConfigurationException("Error while parsing configurations, " + configJsonKey + " not found!");
        }
    }

    @Override
    public List<String> getUrls() {
        return urls;
    }

    @Override
    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    @Override
    public String getSftpUsername() {
        return sftpUsername;
    }

    @Override
    public String getSftPassword() {
        return sftpPassword;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }

    @Override
    public int getThreadsPoolSize() {
        return threadPoolSize;
    }

    @Override
    public int getIOBufferSize() {
        return ioBufferSize;
    }

    @Override
    public String toString() {
        return config.toString();
    }
}
