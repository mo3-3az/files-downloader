package com.thecodelab.filedownloader.writer;

import java.io.*;

/**
 * This implementation utilizes the {@link BufferedWriter} and the {@link BufferedReader}.
 *
 * @author Moath
 */
public class StreamWriterImpl implements StreamWriter {

    private int ioBufferSize;

    public StreamWriterImpl(int ioBufferSize) {
        this.ioBufferSize = ioBufferSize;
    }

    @Override
    public void write(InputStream inputStream, File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File is null!");
        }

        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream is null!");
        }

        try (BufferedOutputStream bufferedWriter = new BufferedOutputStream(new FileOutputStream(file), ioBufferSize);
             BufferedInputStream bufferedReader = new BufferedInputStream(inputStream, ioBufferSize)) {
            int readByte;
            while ((readByte = bufferedReader.read()) != -1) {
                bufferedWriter.write(readByte);
            }
        }
    }
}
