package com.agoda.filedownloader.config;

import com.agoda.filedownloader.config.exception.ConfigurationException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Moath
 */

public class JsonConfigTest {

    private static final String JSON_CONFIG_STRING =
            "{" +
                    "  \"urls\": [" +
                    "    \"url\"" +
                    "  ]," +
                    "  \"downloadDirectory\": \"/downloads\"," +
                    "  \"sftpUsername\": \"sftpUsername\"," +
                    "  \"sftpPassword\": \"sftpPassword\"," +
                    "  \"connectionTimeout\": 5000," +
                    "  \"readTimeout\": 5000," +
                    "  \"threadPoolSize\": 5," +
                    "  \"ioBufferSize\": 1024" +
                    "}";

    private static final String INCOMPLETE_JSON_CONFIG = "{\"downloadDirectory\":\"dir\"}";


    @Test
    public void passValidJsonConfigObject() {
        final JsonObject jsonObjectConfig = new JsonParser().parse(JSON_CONFIG_STRING).getAsJsonObject();
        final JsonConfig jsonConfig = new JsonConfig(jsonObjectConfig);

        Assert.assertEquals(jsonObjectConfig.toString(), jsonConfig.toString());
    }

    @Test(expected = ConfigurationException.class)
    public void passJsonConfigObjectWithMissingConfigs() {
        final JsonObject jsonObjectConfig = new JsonParser().parse(INCOMPLETE_JSON_CONFIG).getAsJsonObject();
        new JsonConfig(jsonObjectConfig);
    }
}
