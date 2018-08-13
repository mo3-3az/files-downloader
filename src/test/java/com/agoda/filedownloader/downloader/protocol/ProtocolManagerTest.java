package com.agoda.filedownloader.downloader.protocol;

import org.junit.Assert;
import org.junit.Test;

public class ProtocolManagerTest {

    @Test
    public void passSecureProtocol() {
        Assert.assertTrue(new ProtocolManager("sftp").requiresAuthentication());
    }

    @Test
    public void passInsecureProtocol() {
        Assert.assertFalse(new ProtocolManager("http").requiresAuthentication());
        Assert.assertFalse(new ProtocolManager("ftp").requiresAuthentication());
        Assert.assertFalse(new ProtocolManager("https").requiresAuthentication());
    }

    @Test(expected = IllegalArgumentException.class)
    public void passInvalidProtocol() {
        new ProtocolManager("protocol").requiresAuthentication();
    }

}