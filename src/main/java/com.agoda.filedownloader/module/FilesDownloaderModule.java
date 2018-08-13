package com.agoda.filedownloader.module;

import com.agoda.filedownloader.config.Config;
import com.agoda.filedownloader.config.JsonConfig;
import com.agoda.filedownloader.config.JsonConfigReader;
import com.agoda.filedownloader.downloader.FileDownloader;
import com.agoda.filedownloader.downloader.FileDownloaderImpl;
import com.agoda.filedownloader.reader.UrlReader;
import com.agoda.filedownloader.reader.UrlReaderImpl;
import com.agoda.filedownloader.writer.StreamWriter;
import com.agoda.filedownloader.writer.StreamWriterImpl;
import com.google.inject.AbstractModule;

import java.io.File;

/**
 * @author Moath
 */
public class FilesDownloaderModule extends AbstractModule {

    private File configFile;

    public FilesDownloaderModule() {
        this(null);
    }

    public FilesDownloaderModule(File configFile) {
        this.configFile = configFile;
    }

    @Override
    protected void configure() {
        final JsonConfig jsonConfig = new JsonConfig(new JsonConfigReader(configFile).getConfig());
        bind(Config.class).toInstance(jsonConfig);
        bind(UrlReader.class).toInstance(new UrlReaderImpl(jsonConfig.getReadTimeout(), jsonConfig.getConnectionTimeout()));
        bind(StreamWriter.class).toInstance(new StreamWriterImpl(jsonConfig.getIOBufferSize()));
        bind(FileDownloader.class).to(FileDownloaderImpl.class);
    }
}
