package com.agoda.filedownloader.downloader.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Moath
 */
public class ProtocolManager {

    private String protocol;
    private final static Map<String, Boolean> protocols = new HashMap<>();

    static {
        protocols.put("sftp", true);
        protocols.put("https", false);
        protocols.put("http", false);
        protocols.put("ftp", false);
    }

    public ProtocolManager(String protocol) {
        this.protocol = protocol;
    }

    public boolean requiresAuthentication() {
        if (!protocols.containsKey(protocol)) {
            throw new IllegalArgumentException("Invalid protocol!");
        }

        return protocols.get(protocol);
    }
}
