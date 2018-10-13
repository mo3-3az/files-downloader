package com.thecodelab.filedownloader.config.exception;

/**
 * This will be thrown on startup if an error happens while configuration loading.
 * Such as, invalid configurations.
 *
 * @author Moath
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String s) {
        super(s);
    }

    public ConfigurationException(String message, Exception exception) {
        super(message, exception);
    }
}
