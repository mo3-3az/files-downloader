package sun.net.www.protocol.sftp;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author Moath
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL url) {
        return new SftpConnection(url);
    }
}
