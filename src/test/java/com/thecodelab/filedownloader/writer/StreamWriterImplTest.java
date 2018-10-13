package com.thecodelab.filedownloader.writer;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StreamWriterImplTest {

    private static final int IO_BUFFER_SIZE = 8192;
    private static final String TEST_TXT = "test.txt";
    private static final String TEST_FILE = "/test.file";

    @Test
    public void writeValidStream() throws IOException {
        new StreamWriterImpl(IO_BUFFER_SIZE).write(getClass().getResourceAsStream(TEST_FILE), new File(TEST_TXT));
        Assert.assertTrue("No file created!", new File(TEST_TXT).exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeInvalidInputStreamWithValidFile() throws IOException {
        final File file = new File(TEST_TXT);
        new StreamWriterImpl(IO_BUFFER_SIZE).write(null, file);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeValidInputStreamWithInvalidFile() throws IOException {
        final InputStream inputStream = getClass().getResourceAsStream(TEST_FILE);
        new StreamWriterImpl(IO_BUFFER_SIZE).write(inputStream, null);
    }

}