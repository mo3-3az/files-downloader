package sun.net.www.protocol.sftp;

import sun.net.www.protocol.SecureFTP;

import java.net.URL;

/**
 * @author Moath
 */
public class SftpConnection extends SecureFTP {

    protected SftpConnection(URL url) {
        super(url);
    }
}
