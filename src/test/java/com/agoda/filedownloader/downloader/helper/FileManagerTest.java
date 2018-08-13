package com.agoda.filedownloader.downloader.helper;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileManagerTest {

    private static final String VALID_URL = "https://www.website.com/";
    private static final String INVALID_URL = "https_www_invalid_com";

    private static final String DIR = "downloadsDir";

    private static final String EMPTY = "";
    private static final String FILE_WITHOUT_EXTENSION = "file";
    private static final String FILE_WITH_EXTENSION = "file.txt";
    private static final String FILE_WITH_QUERY_PARAMS = "file?p=1";
    private static final String FILE_WITH_EXTENSION_AND_QUERY_PARAMS = "file.txt?p=1";

    @Test
    public void getFileNameFromUrlWithoutFile() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL), DIR);
        Assert.assertEquals(EMPTY, fileManager.getFileName());
    }

    @Test
    public void getFileNameFromUrlWithoutExtension() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITHOUT_EXTENSION), DIR);
        Assert.assertEquals(FILE_WITHOUT_EXTENSION, fileManager.getFileName());
    }

    @Test
    public void getFileNameFromUrlWithExtension() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITH_EXTENSION), DIR);
        Assert.assertEquals(FILE_WITH_EXTENSION, fileManager.getFileName());
    }

    @Test
    public void getFileNameFromUrlWithoutExtensionWithQueryParams() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITH_QUERY_PARAMS), DIR);
        Assert.assertEquals(FILE_WITHOUT_EXTENSION, fileManager.getFileName());
    }

    @Test
    public void getFileNameFromUrlWithExtensionAndQueryParams() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITH_EXTENSION_AND_QUERY_PARAMS), DIR);
        Assert.assertEquals(FILE_WITH_EXTENSION, fileManager.getFileName());
    }

    @Test(expected = MalformedURLException.class)
    public void getFileNamesFromInvalidUrl() throws MalformedURLException {
        new FileManager(new URL(INVALID_URL), null).getFileName();
    }

    @Test
    public void getFileRelativePathFromUrl() throws MalformedURLException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITHOUT_EXTENSION), DIR);
        Assert.assertEquals(DIR + File.separator + FILE_WITHOUT_EXTENSION, fileManager.getFileRelPath());
    }

    @Test
    public void cleanUp() throws IOException {
        final FileManager fileManager = new FileManager(new URL(VALID_URL + FILE_WITHOUT_EXTENSION), DIR);
        Assert.assertTrue("File wasn't deleted!", fileManager.cleanup());
        if (new File(fileManager.getFileName()).createNewFile()) {
            Assert.assertTrue("File wasn't deleted!", fileManager.cleanup());
        }
    }
}