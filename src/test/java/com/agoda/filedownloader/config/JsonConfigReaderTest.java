package com.agoda.filedownloader.config;

import com.agoda.filedownloader.config.exception.ConfigurationException;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class JsonConfigReaderTest {

    private static final String VALID_JSON_CONFIG_FILE = "/validConfig.json";
    private static final String NONEXISTENT_JSON_FILE = "/nonexistent-file.json";
    private static final String INVALID_JSON_CONFIG_FILE = "/invalidConfig.json";

    @Test
    public void passingNullShouldLoadDefaultConfigurations() {
        final JsonConfigReader jsonConfigReader = new JsonConfigReader(null);
        final JsonObject config = jsonConfigReader.getConfig();
        Assert.assertNotNull("Config object was null!", config);
    }

    @Test
    public void passingValidConfigFile() throws URISyntaxException {
        File configFile = new File(getClass().getResource(VALID_JSON_CONFIG_FILE).toURI());
        final JsonConfigReader jsonConfigReader = new JsonConfigReader(configFile);
        Assert.assertNotNull("Config object was null!", jsonConfigReader.getConfig());
    }

    @Test(expected = ConfigurationException.class)
    public void passingInvalidConfigFilePath() {
        new JsonConfigReader(new File(NONEXISTENT_JSON_FILE));
    }

    @Test(expected = ConfigurationException.class)
    public void passingInvalidConfigFile() throws URISyntaxException {
        final URI configFile = getClass().getResource(INVALID_JSON_CONFIG_FILE).toURI();
        new JsonConfigReader(new File(configFile));
    }
}