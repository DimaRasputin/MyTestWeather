package net.extracode.selenium.ezwim.util.remote;

import com.jcraft.jsch.*;
import net.extracode.selenium.common.exception.SeleniumDownloadException;
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
import java.util.Random;


public class RemoteServerAct {

    private static final Logger logger = LogManager.getLogger(RemoteServerAct.class.getSimpleName());

    private String tempFileName;
    private String tempFilePath;
    private String sqlOutput = null;
    //String remoteDir = "/home/autoload";
    String remoteDir = "/home/ezconfig/tmp";
    String connSchema;
    String connSchemaEgv;
    String connSchemaEsm;
    String connDb;
    String connDbEsm;
    //int sshPort = 17022;
    int sshPort = 22;

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
        if (serverURL == null) return "not egv url";
        if (serverURL.contains("stagdup.eztest.nu")) {
            servName = "staging";
        } else if (serverURL.contains("stagdup2.eztest.nu")) {
            servName = "staging2";
        } else if (serverURL.contains("pp-test-cloud.eztest.nu")) {
            servName = "test_cloud"; //10.0.1.25
        } else if (serverURL.contains("pp-staging-cloud.eztest.nu")) {
            servName = "staging_cloud"; //10.65.50.25
        } else if (serverURL.contains("development-06.lan")) {
            servName = "demo";
        } else {
            servName = "unknown";
        }
        //logger.info("Server name: " + servName);
        return servName;
    }

    public void defineSchemaDbEem(String serverName, String dbNumberStaging, String dbNumberDemo) {
        if (serverName.matches("staging")) {
            connDbEsm = "IST2ESM";
            connSchemaEsm = "wf";
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
            connDbEsm = "DEVDBFIRST";
            connSchemaEsm = "wf";
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
            connDbEsm = "DBNODE2";
            connSchemaEsm = "wf";
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

    public String getConnSchemaEsm(String serverName) {
        defineSchemaDbEem(serverName, "101", "101");
        return connSchemaEsm;
    }

    public String getConnSchemaEgvByEemPartition(String serverName, String dbNumberStaging, String dbNumberDemo) {
        defineSchemaDbEem(serverName, dbNumberStaging, dbNumberDemo);
        return connSchemaEgv;
    }

    public String getConnDbEem(String serverName, String dbNumberStaging, String dbNumberDemo) {
        defineSchemaDbEem(serverName, dbNumberStaging, dbNumberDemo);
        return connDb;
    }

    public String getConnDbEsm(String serverName) {
        defineSchemaDbEem(serverName, "101", "101");
        return connDbEsm;
    }

    //need to refactor code - arrays should not be in method and need ability to choose master and slave
    public String getRandomPartition() throws IOException {
        String serv = getServerName();
        String partition = "";
        Random generator = new Random();

        if (serv.equals("demo")) {
            String[] array = new String[]{"101", "102", "201", "202"};
            int randomIndex = generator.nextInt(array.length);
            partition = array[randomIndex];
        }
        if (serv.equals("staging") || serv.equals("staging2")) {
            String[] array = new String[]{"101", "102", "201", "202", "203", "401"};
            int randomIndex = generator.nextInt(array.length);
            partition = array[randomIndex];
        }
        return partition;
    }

    public String getHostName() throws IOException {
        String server = getServerName();
        String hostName = null;
        if (server.equals("test_cloud")) {
            //hostName = "cuda1-extracode.ddns.net"; //10.0.1.25
            hostName = "10.0.1.25";
        }
        if (server.equals("staging_cloud")) {
            //10.65.50.25
            hostName = "10.65.50.25";
        }
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
        Session session;
        Channel channel;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        logger.info("Put temp file to host: " + hostName + " user: " + userName);

        try {
            logger.debug("Start ssh");
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            JSch.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, sshPort);
            session.connect();
            logger.debug("Start sftp");
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            logger.debug("Put file");
            sftp.put(tempFilePath, remoteDir);
            channel.disconnect();
            session.disconnect();
            logger.debug("Done.");
        } catch (JSchException | SftpException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * possibility to set remoteDir
     */
    public void putTempFileOnServer(String userName, String remoteDirectory) throws IOException {
        Session session;
        Channel channel;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        logger.info("Put temp file to host: " + hostName + " user: " + userName + " to " + remoteDirectory);
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            JSch.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, sshPort);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(tempFilePath, remoteDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | URISyntaxException | SftpException e) {
            e.printStackTrace();
        }
    }


    public void putFileOnServer(String userName, String pathToLocalFile, String remoteDirectory) throws IOException {
        Session session;
        Channel channel;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        logger.info("Put file " + pathToLocalFile + " to host: " + hostName + " user: " + userName + " to " + remoteDirectory);

        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            JSch.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, sshPort);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(pathToLocalFile, remoteDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void executeSql() throws IOException {
        executeSql(true);
    }

    public String executeSql(boolean checkSuccess) throws IOException {
        String command1 = "export ORACLE_HOME=/opt/oracle/instantclient_19;" +
                "export TNS_ADMIN=$ORACLE_HOME/network/admin;" +
                "/opt/oracle/instantclient_19/sqlplus /nolog @" + remoteDir + "/" + tempFileName + ";";
        String command2 = "rm " + remoteDir + "/" + tempFileName + ";" +
                "exit";
        String pathToPrivateKey = this.getClass().getResource("/id_rsa_autoload_staging").toExternalForm();
        String hostName = getHostName();
        logger.debug("Start ssh connection");
        SshServer SshServ = new SshServer(hostName, "ezconfig", pathToPrivateKey);
        logger.debug("Start execute sql");
        sqlOutput = (SshServ.execute(command1));
        SshServ.execute(command2);

        logger.info("sqlOutput:\n" + sqlOutput);
        if (checkSuccess) {
            boolean success = ((sqlOutput.contains("procedure successfully completed.") || sqlOutput.contains("rows updated.")
                    || sqlOutput.contains("row updated.")) && sqlOutput.contains("Commit complete")
                    || sqlOutput.contains("rows selected") || sqlOutput.contains("row selected")
                    || (!sqlOutput.toLowerCase().contains("SQL Error".toLowerCase())
                    && !sqlOutput.toLowerCase().contains("ORA-".toLowerCase())));

            if (success) {
                logger.info("Sql successfully executed");
            } else {
                throw new EzwimException("Problem with executing sql.\nSQL error:\n" + sqlOutput);
            }
        }

        return sqlOutput;
    }

    public void getFileFromServer(String userName, String pathToRemoteFile, String localDirectory) throws
            IOException {
        File dir = new File(localDirectory);
        if (!dir.exists()) {
            logger.debug("download dir is not exist, create...");
            if (!dir.mkdirs()) {
                throw new SeleniumDownloadException("can't create download dir");
            }
        } else {
            logger.debug("download dir exists");
        }
        Session session;
        Channel channel;
        String namePrivKey = "id_rsa_autoload_staging";
        String hostName = getHostName();
        logger.info("Get from " + hostName + " file " + pathToRemoteFile + " and put to " + localDirectory);
        try {
            JSch ssh = new JSch();
            URL keyFileURL = this.getClass().getClassLoader().getResource(namePrivKey);
            URI keyFileURI = keyFileURL.toURI();
            JSch.setConfig("StrictHostKeyChecking", "no");
            ssh.addIdentity(new File(keyFileURI).getAbsolutePath());
            session = ssh.getSession(userName, hostName, sshPort);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.get(pathToRemoteFile, localDirectory);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException | URISyntaxException e) {
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
