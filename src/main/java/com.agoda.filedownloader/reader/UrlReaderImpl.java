package com.agoda.filedownloader.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This implementation utilizes the URL class provide by Java.
 *
 * @author Moath
 */
public class UrlReaderImpl implements UrlReader {

    private static final String REQUEST_PROPERTY_USER_AGENT = "User-Agent";
    private static final String USER_AGENT_JAVA_APP_1_0 = "JavaAppFilesDownloader/1.0";

    private int readTimeout;
    private int connectionTimeout;

    public UrlReaderImpl(int readTimeout, int connectionTimeout) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public InputStream read(URL url) throws IOException {
        URLConnection urlConnection = getConnection(url);
        urlConnection.connect();

        return urlConnection.getInputStream();
    }

    private URLConnection getConnection(URL url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("Url is null!");
        }

        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(connectionTimeout);
        urlConnection.setReadTimeout(readTimeout);
        urlConnection.addRequestProperty(REQUEST_PROPERTY_USER_AGENT, USER_AGENT_JAVA_APP_1_0);

        return urlConnection;
    }
}
