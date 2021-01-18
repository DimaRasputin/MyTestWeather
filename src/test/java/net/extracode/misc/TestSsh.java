package net.extracode.misc;

import net.extracode.selenium.ezwim.util.SshServer;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class TestSsh {

    @Test
    @Ignore
    public void test() throws IOException {
        SshServer a = new SshServer("f-dev.lan", "davs", this.getClass().getResource("/test").toExternalForm());
        System.out.println(a.execute("ls -l"));
        System.out.println(a.saveToFile(
                IOUtils.toString(this.getClass().getResourceAsStream("/test.sql"), "UTF-8"), "test123"));
    }

}
