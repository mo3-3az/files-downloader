package com.thecodelab.filedownloader.writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface StreamWriter {
    /**
     * This method should write the passed stream's content to the passed file.
     */
    void write(InputStream inputStream, File file) throws IOException;
}
