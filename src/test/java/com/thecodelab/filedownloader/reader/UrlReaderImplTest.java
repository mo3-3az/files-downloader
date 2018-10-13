package com.thecodelab.filedownloader.reader;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlReaderImplTest {

    private static final int TIMEOUT = 5000;
    private static final String TEST_FILE = "/test.file";
    private static final String INVALID_URL = "invalid Url";
    private static final int UNEXPECTED_VALUE = -1;

    private static UrlReader urlReader;
    private InputStream inputStream = null;

    @BeforeClass
    public static void setUp() {
        urlReader = new UrlReaderImpl(TIMEOUT, TIMEOUT);
    }

    @Test
    public void passValidUrl() throws IOException {
        inputStream = urlReader.read(getClass().getResource(TEST_FILE));
        Assert.assertNotNull("Input stream was null!", inputStream);
        Assert.assertNotEquals("Couldn't read the first byte from the stream!", UNEXPECTED_VALUE, inputStream.read());
    }

    @Test(expected = IllegalArgumentException.class)
    public void passNullForUrl() throws IOException {
        urlReader.read(null);
    }

    @Test(expected = MalformedURLException.class)
    public void passInvalidUrl() throws IOException {
        urlReader.read(new URL(INVALID_URL));
    }

    @After
    public void tearDown() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }
}