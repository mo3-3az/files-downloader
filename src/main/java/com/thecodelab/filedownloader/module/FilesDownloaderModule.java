package com.thecodelab.filedownloader.module;

import com.google.inject.AbstractModule;
import com.thecodelab.filedownloader.config.Config;
import com.thecodelab.filedownloader.config.JsonConfig;
import com.thecodelab.filedownloader.config.JsonConfigReader;
import com.thecodelab.filedownloader.downloader.FileDownloader;
import com.thecodelab.filedownloader.downloader.FileDownloaderImpl;
import com.thecodelab.filedownloader.reader.UrlReader;
import com.thecodelab.filedownloader.reader.UrlReaderImpl;
import com.thecodelab.filedownloader.writer.StreamWriter;
import com.thecodelab.filedownloader.writer.StreamWriterImpl;

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
        final Config jsonConfig = new JsonConfig(new JsonConfigReader(configFile).getConfig());
        bind(Config.class).toInstance(jsonConfig);
        bind(UrlReader.class).toInstance(new UrlReaderImpl(jsonConfig.getReadTimeout(), jsonConfig.getConnectionTimeout()));
        bind(StreamWriter.class).toInstance(new StreamWriterImpl(jsonConfig.getIOBufferSize()));
        bind(FileDownloader.class).to(FileDownloaderImpl.class);
    }
}
