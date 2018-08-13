package sun.net.www.protocol.ftps;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author Moath
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL url) {
        return new FtpsConnection(url);
    }
}
