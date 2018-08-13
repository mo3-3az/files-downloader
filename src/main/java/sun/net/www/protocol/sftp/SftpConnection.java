package sun.net.www.protocol.sftp;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Moath
 */
public class SftpConnection extends URLConnection {

    public static final String USERNAME = "getSftpUsername";
    public static final String PASSWORD = "PASSWORD";

    private FileObject fileObject;


    protected SftpConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
//
//        JSch jsch = new JSch();
//        Session session = null;
//        try {
//            final String userInfo = uri.getUserInfo();
//            session = jsch.getSession(userInfo == null ? getRequestProperty(USERNAME) : userInfo, uri.getHost(), uri.getPort());
//            session.setConfig(STRICT_HOST_KEY_CHECKING, NO);
//            session.setPassword(getRequestProperty(PASSWORD));
//            session.connect(getConnectTimeout());
//
//            channel = session.openChannel(SFTP);
//            channel.connect();
//        } catch (JSchException e) {
//            throw new IOException(e);
//        } finally {
//            if (session != null) {
//                session.disconnect();
//            }
//        }

        FileSystemOptions fsOptions = new FileSystemOptions();
        final SftpFileSystemConfigBuilder instance = SftpFileSystemConfigBuilder.getInstance();
        instance.setStrictHostKeyChecking(fsOptions, "no");
        instance.setTimeout(fsOptions, getConnectTimeout());
        FileSystemManager fsManager = VFS.getManager();
        fileObject = fsManager.resolveFile(uri.toString(), fsOptions);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return fileObject.getContent().getInputStream();
    }


}
