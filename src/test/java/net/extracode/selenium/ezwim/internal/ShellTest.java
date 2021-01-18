package net.extracode.selenium.ezwim.internal;

import net.extracode.selenium.ezwim.util.SshServer;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class ShellTest {

    @Test
    @Ignore
    public void test() throws IOException {

        URL url = this.getClass().getResource("/test");
        System.out.println("private key url: " + url.toExternalForm());

        SshServer sshServer = new SshServer("f-dev.lan", "davs", url.toExternalForm());

        String command = "export ORACLE_HOME=/home/oracle/app/oracle/product/12.1.0/client_1;" +
                "/home/oracle/app/oracle/product/12.1.0/client_1/bin/sqlplus " +
                "libuser/libuser@DEVDBFIRST " +
                "@/home/davs/test.sql;";

        System.out.println("command output: " + sshServer.execute(command));
    }
}

