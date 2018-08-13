package sun.net.www.protocol.ftps;

import sun.net.www.protocol.SecureFTP;

import java.net.URL;

/**
 * @author Moath
 */
public class FtpsConnection extends SecureFTP {

    protected FtpsConnection(URL url) {
        super(url);
    }
}
