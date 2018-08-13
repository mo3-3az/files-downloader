package com.agoda.filedownloader.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface UrlReader {

    /**
     * This method should open a stream from the passed url.
     * It should accept any URL and support file protocols
     */
    InputStream read(URL url) throws IOException;

}
