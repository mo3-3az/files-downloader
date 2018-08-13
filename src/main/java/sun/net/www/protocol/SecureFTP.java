package sun.net.www.protocol;

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
 * This is based on Apache Commons VFS.
 *
 * @author Moath
 */
public class SecureFTP extends URLConnection {

    private static final String HOST_KEY_CHECKING = "no";
    private FileObject fileObject;

    protected SecureFTP(URL url) {
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

        FileSystemOptions fsOptions = new FileSystemOptions();
        final SftpFileSystemConfigBuilder instance = SftpFileSystemConfigBuilder.getInstance();
        instance.setStrictHostKeyChecking(fsOptions, HOST_KEY_CHECKING);
        instance.setTimeout(fsOptions, getConnectTimeout());
        FileSystemManager fsManager = VFS.getManager();
        fileObject = fsManager.resolveFile(uri.toString(), fsOptions);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return fileObject.getContent().getInputStream();
    }

}