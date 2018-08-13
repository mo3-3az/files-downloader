package com.agoda.filedownloader.config;

import com.agoda.filedownloader.config.exception.ConfigurationException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * This class is reading data from a JSON file to a JSON object.
 *
 * @author Moath
 */
public class JsonConfigReader {

    private static final Logger LOGGER = Logger.getLogger(JsonConfigReader.class);

    private static final String DEFAULT_CONFIG_FILE = "config.json";

    private JsonObject config;

    /**
     * Will load the passed file if passed and a valid json file, otherwise will terminate.
     */
    public JsonConfigReader(File configFile) {
        InputStream configFileStream;
        if (configFile == null) {
            configFileStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
            LOGGER.info("Loading default configurations.");
        } else {
            try {
                configFileStream = new FileInputStream(configFile);
                LOGGER.info("Loading configurations from file: " + configFile);
            } catch (FileNotFoundException e) {
                LOGGER.fatal("Error while loading configurations from file: " + configFile + ", file not found!", e);
                throw new ConfigurationException("Error while loading configurations from file: " + configFile + ", file not found!", e);
            }
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(configFileStream)) {
            config = new JsonParser().parse(inputStreamReader).getAsJsonObject();
        } catch (JsonIOException | JsonSyntaxException e) {
            LOGGER.fatal("Error while parsing configurations from file: " + configFile + ", falling back to default configurations.", e);
            throw new ConfigurationException("Error while parsing configurations from file: " + configFile, e);
        } catch (IOException e) {
            LOGGER.fatal("Error while closing configuration input stream", e);
        }
    }

    public JsonObject getConfig() {
        return config;
    }
}
