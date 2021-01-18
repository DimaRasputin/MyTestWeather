package net.extracode.selenium.ezwim.util.remote;

import net.extracode.selenium.ezwim.util.remote.sqlAct.PasswordActionsSql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SqlAct {

    private static final Logger LOGGER = LogManager.getLogger(SqlAct.class.getSimpleName());
    String serverName;
    String connSchema;
    String connDb;

    public SqlAct() throws IOException {
        serverName = new RemoteServerAct().getServerName();
        LOGGER.info("Server = " + serverName);

    }

    public SqlAct deleteCompany(String dbNumber, String companyNumber, String codeReseller) throws IOException {
        connSchema = new RemoteServerAct().getConnSchemaEem(serverName,dbNumber,dbNumber);
        connDb = new RemoteServerAct().getConnDbEem(serverName,dbNumber,dbNumber);

        String contentSQL = "conn " + connSchema + "_oper/xoper@" + connDb + "\n" +
                "set define off\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "\n" +
                "DECLARE\n" +
                "  l_tabAgree                      pk_autotest.tabParams;\n" +
                "BEGIN\n" +
                "  l_tabAgree('num') := '" + companyNumber + "';--TO_CHAR(SYSDATE, 'YYYYMMDD');\n" +
                "  l_tabAgree('reseller') := '" + codeReseller + "';\n" +
                "  pk_autotest.del_agree(l_tabAgree('num'), l_tabAgree('reseller'));\n" +
                "\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "exit;";
        RemoteServerAct ResetPassword = new RemoteServerAct();
        ResetPassword.createTempSqlFile(contentSQL);
        ResetPassword.putTempFileOnServer("autoload");
        ResetPassword.executeSql();
        LOGGER.info("SQL: Company was deleted");
        return this;

    }

    public PasswordActionsSql passwordActionsSql() throws IOException {
        return new PasswordActionsSql();
    }

    public void approvePeriod(String companyNumber, String dbStaging, String dbDemo) throws IOException {
        String schema = new RemoteServerAct().getConnSchemaEem(new RemoteServerAct().getServerName(),dbStaging,dbDemo);
        String dB = new RemoteServerAct().getConnDbEem(new RemoteServerAct().getServerName(),dbStaging,dbDemo);
        String contentSQL3 = "conn " + schema + "-owner/xowner@" + dB + "\n" +
                "set define off\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "" +
                "        declare\n" +
                "        l_reseller      t_agree.reseller%TYPE;\n" +
                "        l_root          VARCHAR2(100);\n" +
                "        l_corp          t_user.id_user%TYPE;\n" +
                "        l_account       t_account.id_account%TYPE;\n" +
                "        l_num           VARCHAR2(100):= '" + companyNumber + "';\n" +
                "        begin\n" +
                "        SELECT id_user, reseller INTO l_corp, l_reseller FROM t_agree WHERE num = l_num;\n" +
                "        SELECT moniker INTO l_root FROM t_user WHERE corp = l_corp AND kind='E' AND status='A';\n" +
                "        SELECT id_account INTO l_account FROM t_account WHERE state = 'L' AND id_agree = (SELECT id_agree FROM t_agree WHERE num = l_num);\n" +
                "        pk_web.open_session(l_root, l_reseller);\n" +
                "        pk_acc.approve(l_account, 'Y');\n" +
                "        pk_web.close_session;\n" +
                "        end;\n" +
                "/\n" +
                "commit;\n" +
                "exit";


        RemoteServerAct approve3 = new RemoteServerAct();
        approve3.createTempSqlFile(contentSQL3);
        approve3.putTempFileOnServer("autoload");
        approve3.executeSql();
        LOGGER.info("SQL: Period was approved");
    }


    public void autoShare(String companyNumber, String period, String dbStaging, String dbDemo) throws IOException {
        String schema = new RemoteServerAct().getConnSchemaEem(new RemoteServerAct().getServerName(),dbStaging,dbDemo);
        String dB = new RemoteServerAct().getConnDbEem(new RemoteServerAct().getServerName(),dbStaging,dbDemo);
        //String companyNumber = "8376938"
        //String period = "October 2018"
        String contentSQL = "conn " + schema + "-owner/xowner@" + dB + "\n" +
                "set define off\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "" +
                "        declare\n" +
                "        l_account       t_account.id_account%TYPE;\n" +
                "        l_num           VARCHAR2(100):= '" + companyNumber + "';\n" +
                "        begin\n" +
                "        SELECT id_account INTO l_account FROM t_account WHERE num = '" + period + "' AND id_agree = (SELECT id_agree FROM t_agree WHERE num = l_num);\n" +
                "        pki_report.auto_share(l_account);\n" +
                "        end;\n" +
                "/\n" +
                "commit;\n" +
                "exit";


        RemoteServerAct approve3 = new RemoteServerAct();
        approve3.createTempSqlFile(contentSQL);
        approve3.putTempFileOnServer("autoload");
        approve3.executeSql();
        LOGGER.info("SQL: autoshare is complete");
    }

}

