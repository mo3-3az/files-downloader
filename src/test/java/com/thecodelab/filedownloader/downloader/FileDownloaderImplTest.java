package com.thecodelab.filedownloader.downloader;

import com.thecodelab.filedownloader.reader.UrlReaderImpl;
import com.thecodelab.filedownloader.writer.StreamWriterImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import static org.mockito.BDDMockito.given;

public class FileDownloaderImplTest {

    private static final String TEST_DOWNLOADS = "test-downloads";
    private static final String TEST_FILE = "/test.file";
    private static final String INVALID_TEST_FILE = "/invalid.file";
    private static final String DOWNLOADED_TEST_FILE = "test.file";
    private static final String TEST_PROTOCOL = "file";
    private static final String TEST_HOST = "host";
    private static final int TEST_PORT = 0;
    private static final int TIMEOUT = 5000;
    private static final int IO_BUFFER_SIZE = 8192;

    private static FileDownloaderImpl fileDownloader;
    private static File downloadDirectory;

    @BeforeClass
    public static void setUp() throws Exception {
        fileDownloader = new FileDownloaderImpl(new UrlReaderImpl(TIMEOUT, TIMEOUT), new StreamWriterImpl(IO_BUFFER_SIZE));
        downloadDirectory = new File(TEST_DOWNLOADS);
        if (!downloadDirectory.exists()) {
            downloadDirectory.mkdirs();
        }
    }

    @Test
    public void downloadValidUrl() throws Exception {
        fileDownloader.download(getMockUrl(), downloadDirectory).call();
        Assert.assertTrue(new File(TEST_DOWNLOADS, DOWNLOADED_TEST_FILE).exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void downloadInvalidUrl() throws Exception {
        fileDownloader.download(getMockInvalidUrl(), downloadDirectory).call();
        Assert.assertTrue(new File(TEST_DOWNLOADS, DOWNLOADED_TEST_FILE).exists());
    }

    private URL getMockUrl() throws IOException {
        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        given(mockConnection.getInputStream()).willReturn(getClass().getResourceAsStream(TEST_FILE));

        final URLStreamHandler handler = getUrlStreamHandler(mockConnection);
        return getUrl(handler);
    }

    private URL getMockInvalidUrl() throws IOException {
        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        given(mockConnection.getInputStream()).willReturn(getClass().getResourceAsStream(INVALID_TEST_FILE));

        final URLStreamHandler handler = getUrlStreamHandler(mockConnection);
        return getUrl(handler);
    }

    private URL getUrl(URLStreamHandler handler) throws MalformedURLException {
        return new URL(TEST_PROTOCOL, TEST_HOST, TEST_PORT, TEST_FILE, handler);
    }

    private URLStreamHandler getUrlStreamHandler(URLConnection mockConnection) {
        return new URLStreamHandler() {

            @Override
            protected URLConnection openConnection(final URL url) {
                return mockConnection;
            }
        };
    }

    @AfterClass
    public static void tearDown() {
        if (downloadDirectory.exists()) {
            downloadDirectory.deleteOnExit();
        }
    }
}