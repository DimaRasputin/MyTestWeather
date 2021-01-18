package net.extracode.selenium.ezwim.util.remote;

import com.jcraft.jsch.*;
import net.extracode.selenium.ezwim.exception.EzwimException;
import net.extracode.selenium.ezwim.util.SshServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class RemoteServerAct {

    private static final Logger LOGGER = LogManager.getLogger(RemoteServerAct.class.getSimpleName());

    private String tempFileName;
    private String tempFilePath;
    private String sqlOutput = null;
    String remoteDir = "/home/autoload";
    String connSchema;
    String connSchemaEgv;
    String connDb;

    public String getServerName() throws IOException {
        String servName;
        String configFile;
        if (System.getProperties().containsKey("test.config")) {
            configFile = System.getProperty("test.config");
        } else {
            configFile = "config_local.properties";
        }

        Properties property = new Properties();
        InputStream inputStream = SqlAct.class.getClassLoader().getResourceAsStream(configFile);
        property.load(inputStream);
        String serverFileProp = property.getProperty("contextProps");
        inputStream = SqlAct.class.getClassLoader().getResourceAsStream(serverFileProp);
        property.load(inputStream);
        String serverURL = property.getProperty("egv.url");
        if (serverURL.contains("stagdup.eztest.nu")) {
            servName = "staging";
        } else if (serverURL.contains("stagdup2.eztest.nu")) {
            servName = "staging2";
        } else if (serverURL.contains("development-06.lan")) {
            servName = "demo";
        } else {
            servName = "unknown";
        }

        return servName;
    }

    public void defineSchemaDbEem(String serverName, String dbNumberStaging, String dbNumberDemo) {
        if (serverName.matches("staging")) {
            if (dbNumberStaging.matches("101")) {
                connSchema = "ps1p01";
                connSchemaEgv = "ps1";
                connDb = "iprod1";
            } else if (dbNumberStaging.matches("102")) {
                connSchema = "ps1p02";
                connSchemaEgv = "ps1";
                connDb = "iprod1";
            } else if (dbNumberStaging.matches("201")) {
                connSchema = "st2p01";
                connSchemaEgv = "st2";
                connDb = "ist3slv";
            } else if (dbNumberStaging.matches("202")) {
                connSchema = "st2p02";
                connSchemaEgv = "st2";
                connDb = "ist3slv";
            } else if (dbNumberStaging.matches("203")) {
                connSchema = "st2p03";
                connSchemaEgv = "st2";
                connDb = "ist3slv";
            } else if (dbNumberStaging.matches("401")) {
                connSchema = "st4p01";
                connSchemaEgv = "st4";
                connDb = "ist4slv";
            } else
                throw new EzwimException("Wrong DB number for Staging");
        } else if (serverName.matches("demo")) {
            if (dbNumberDemo.matches("101")) {
                connSchema = "dv1p01";
                connSchemaEgv = "dv1";
                connDb = "devdbfirst";
            } else if (dbNumberDemo.matches("102")) {
                connSchema = "dv1p02";
                connSchemaEgv = "dv1";
                connDb = "devdbfirst";
            } else if (dbNumberDemo.matches("201")) {
                connSchema = "dv2p01";
                connSchemaEgv = "dv2";
                connDb = "devdbsecond";
            } else if (dbNumberDemo.matches("202")) {
                connSchema = "dv2p02";
                connSchemaEgv = "dv2";
                connDb = "devdbsecond";
            } else
                throw new EzwimException("Wrong DB number for Demo");
        } else if (serverName.matches("staging2")) {
            if (dbNumberStaging.matches("101")) {
                connSchema = "ps1p01";
                connSchemaEgv = "ps1";
                connDb = "dbmain";
            } else if (dbNumberStaging.matches("102")) {
                connSchema = "ps1p02";
                connSchemaEgv = "ps1";
                connDb = "dbmain";
            } else if (dbNumberStaging.matches("201")) {
                connSchema = "st2p01";
                connSchemaEgv = "st2";
                connDb = "dbnode3";
            } else if (dbNumberStaging.matches("202")) {
                connSchema = "st2p02";
                connSchemaEgv = "st2";
                connDb = "dbnode3";
            } else if (dbNumberStaging.matches("203")) {
                connSchema = "st2p03";
                connSchemaEgv = "st2";
                connDb = "dbnode3";
            } else if (dbNumberStaging.matches("401")) {
                connSchema = "st4p01";
                connSchemaEgv = "st4";
                connDb = "dbnode4";
            } else
                throw new EzwimException("Wrong DB number for Staging");
        } else if (serverName.matches("unknown")) {
            throw new EzwimException("Problem with getting server by URL from properties file");
        }
    }

    public String getConnSchemaEem(String serverName, String dbNumberStaging, String dbNumberDemo) {
        defineSchemaDbEem(serverName, dbNumberStaging, dbNumberDemo);
        return connSchema;
    }

    public String getConnSchemaEgvByEemPartition(String serverName, String dbNumberStaging, String dbNumberDemo) {
        defineSchemaDbEem(serverName, dbNumberStaging, dbNumberDemo);
        return connSchemaEgv;
    }

    public String getConnDbEem(String serverName, String dbNumberStaging, String dbNumberDemo) {
        defineSchemaDbEem(serverName, dbNumberStaging, dbNumberDemo);
        return connDb;
    }

    public String getHostName() throws IOException {
        String server = getServerName();
        String hostName = null;
        if (server.equals("staging")) {
            hostName = "d-fe1.ezwim.cyso.net";
        }
        if (server.equals("staging2")) {
            hostName = "d-fe2.ezwim.cyso.net";
        }
        if (server.equals("demo")) {
            hostName = "frontend-06.lan";
        }
        return hostName;
    }


    public void createTempSqlFile(String contentSQL) {
        try {
            File tempFile;
            tempFile = File.createTempFile("Autotest", ".sql");
            tempFilePath = tempFile.getAbsolutePath();
            tempFileName = tempFile.getName();
            FileWriter fileWriter = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(contentSQL);
            bw.close();
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTempFile(String content, String fileName) {
        createTempFile(content, fileName, StandardCharsets.UTF_8);
    }

    public void createTempFile(String content, String fileName, Charset charSet) {
        try {
            File tempFile;
            String tDir = System.getProperty("java.io.tmpdir");
            if (!tDir.endsWith("/")) {
                tDir = tDir + "/";
            }
            tempFile = new File(tDir + fileName);
            tempFilePath = tempFile.getAbsolutePath();
            tempFileName = tempFile.getName();
            FileWriter fileWriter = new FileWriter(tempFile, charSet, false);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(content);
            bw.close();
            fileWriter.close();
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createTempFileNew(String content, String fileName) {
        try {
            File tempFile;
            String tDir = System.getProperty("java.io.tmpdir");
            if (!tDir.endsWith("/")) {
                tDir = tDir + "/";
            }
            tempFile = new File(tDir + fileName);
            tempFilePath = tempFile.getAbsolutePath();
            tempFileName = tempFile.getName();
            FileWriter fileWriter = new FileWriter(tempFile, false);
            BufferedWriter bw = new BufferedWriter(fileWriter);
//            byte [] b = content.getBytes(StandardCharsets.UTF_8);
//            String c = new String(b, StandardCharsets.UTF_8);
            bw.write('\uFEFF');
            bw.write(content);
            bw.close();
            fileWriter.close();
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFilePath;
    }

    public void putTempFileOnServer(String userName) throws IOException {
        Session session = null;
        Channel channel = null;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
//            SshServer remoteSSH = new SshServer("d-fe1.ezwim.cyso.net", "autoload", keyFileURL.toExternalForm());
//            String outputScan = (remoteSSH.execute("ssh-keyscan -t rsa d-fe1.ezwim.cyso.net"));
//            String[] mytext = outputScan.split("\n");
//            String knownHostPublicKey = mytext[1].trim();
//            ssh.setKnownHosts(new ByteArrayInputStream(knownHostPublicKey.getBytes()));
            ssh.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, 22);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(tempFilePath, remoteDir);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * possibility to set remoteDir
     */
    public void putTempFileOnServer(String userName, String remoteDirectory) throws IOException {
        Session session = null;
        Channel channel = null;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            ssh.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, 22);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(tempFilePath, remoteDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void putFileOnServer(String userName, String pathToLocalFile, String remoteDirectory) throws IOException {
        LOGGER.info("Put file " + pathToLocalFile + " in " + remoteDirectory);
        Session session = null;
        Channel channel = null;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            ssh.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, 22);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(pathToLocalFile, remoteDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void executeSql() throws IOException {
        String pathToPrivateKey = this.getClass().getResource("/id_rsa_autoload_staging").toExternalForm();
        String hostName = getHostName();
        SshServer SshServ = new SshServer(hostName, "autoload", pathToPrivateKey);
        StringBuilder command1 = new StringBuilder();
        command1.append("export ORACLE_HOME=/home/oracle/app/oracle/product/12.1.0/client_1;");
        command1.append("/home/oracle/app/oracle/product/12.1.0/client_1/bin/sqlplus /nolog @" + remoteDir + "/" + tempFileName + ";");
        sqlOutput = (SshServ.execute(command1.toString()));
        SshServ.execute("rm " + remoteDir + "/" + tempFileName + "");
        SshServ.execute("exit");
        if ((sqlOutput.contains("procedure successfully completed.") || sqlOutput.contains("rows updated.")
                || sqlOutput.contains("row updated.")) || sqlOutput.contains("Commit complete")
                || (!sqlOutput.toLowerCase().contains("SQL Error".toLowerCase())
                && !sqlOutput.toLowerCase().contains("ORA-".toLowerCase()))) {
            LOGGER.info("Sql successfully executed");
        } else {
            throw new EzwimException("Problem with executing sql.\nSQL error:\n" + sqlOutput);
        }
    }

    public String executeSql(boolean checkSuccess) throws IOException {
        String pathToPrivateKey = this.getClass().getResource("/id_rsa_autoload_staging").toExternalForm();
        String hostName = getHostName();
        SshServer SshServ = new SshServer(hostName, "autoload", pathToPrivateKey);
        StringBuilder command1 = new StringBuilder();
        command1.append("export ORACLE_HOME=/home/oracle/app/oracle/product/12.1.0/client_1;");
        command1.append("/home/oracle/app/oracle/product/12.1.0/client_1/bin/sqlplus /nolog @" + remoteDir + "/" + tempFileName + ";");
        sqlOutput = (SshServ.execute(command1.toString()));
        SshServ.execute("rm " + remoteDir + "/" + tempFileName + "");
        SshServ.execute("exit");
        if (checkSuccess) {
            if ((sqlOutput.contains("procedure successfully completed.") || sqlOutput.contains("rows updated.")
                    || sqlOutput.contains("row updated."))
                    && sqlOutput.contains("Commit complete")) {
                LOGGER.info("Sql successfully executed");
            } else {
                throw new EzwimException("Problem with executing sql.\nSQL error:\n" + sqlOutput);
            }
        }
        return sqlOutput;
    }

    public void getFileFromServer(String userName, String pathToRemoteFile, String localDirectory) throws
            IOException {

        Session session = null;
        Channel channel = null;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        LOGGER.info("Get from "+hostName+" file " + pathToRemoteFile + " and put to " + localDirectory);
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            ssh.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, 22);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.get(pathToRemoteFile, localDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public String getSqlOutput() {
        return this.sqlOutput;
    }
}
