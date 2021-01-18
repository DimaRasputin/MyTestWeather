package net.extracode.selenium.ezwim.util;

import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;
import com.jcraft.jsch.JSchException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

public class SshServer {

    public static void main(String[] args) throws JSchException, IOException {
        SshServer sshServer = new SshServer("f-dev.lan", "davs", "file:///home/wls/test");
        System.out.println(sshServer.execute("ls -la"));
    }

    private String host;
    private String username;
    private String privateKeyURL;

    public SshServer(String host, String username, String privateKeyURL) {
        this.host = host;
        this.username = username;
        this.privateKeyURL = privateKeyURL;
    }

    public String execute(String command) throws IOException {
        Shell shell = new SSH(host, username, new URL(privateKeyURL));
        return new Shell.Plain(shell).exec(command);
    }

    public int saveToFile(String content, String path) throws IOException {
        Shell shell = new SSH(host, username, new URL(privateKeyURL));
        return shell.exec("cat > " + path,
                new ByteArrayInputStream(content.getBytes()), null, null);
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPrivateKeyURL() {
        return privateKeyURL;
    }
}
