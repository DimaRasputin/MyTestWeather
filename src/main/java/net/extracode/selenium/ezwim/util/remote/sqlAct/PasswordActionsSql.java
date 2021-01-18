package net.extracode.selenium.ezwim.util.remote.sqlAct;

import net.extracode.selenium.ezwim.exception.EzwimException;
import net.extracode.selenium.ezwim.util.remote.RemoteServerAct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class PasswordActionsSql {
    String serverName;
    String connSchema;
    String connDB;

    private static final Logger logger = LogManager.getLogger(PasswordActionsSql.class.getSimpleName());

    public PasswordActionsSql() throws IOException {
        serverName = new RemoteServerAct().getServerName();

    }


    public PasswordActionsSql resetDateOfLastPasswordChangeEGV(String userLogin) throws IOException {
        if (serverName.matches("staging")) {
            connSchema = "PS1";
            connDB = "IPROD1";
        } else if (serverName.matches("demo")) {
            connSchema = "DV1";
            connDB = "DEVDBFIRST";
        } else if (serverName.matches("unknown")) {
            throw new EzwimException("Problem with getting server by URL from properties file");
        }
        String contentSQL = "conn " + connSchema + "-owner/xowner@" + connDB + "\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "DECLARE\n" +
                "l_id_user number(20);\n" +
                "BEGIN\n" +
                "SELECT id_cs_user\n" +
                "  INTO l_id_user\n" +
                "FROM t_cs_user WHERE moniker = '" + userLogin + "' AND status='A';\n" +
                "pk_profile.edit('User',l_id_user, 'J', TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'));\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "/\n" +
                "" +
                "exit;" +
                "";
        RemoteServerAct ResetPassword = new RemoteServerAct();
        ResetPassword.createTempSqlFile(contentSQL);
        ResetPassword.putTempFileOnServer("autoload");
        ResetPassword.executeSql();
        System.out.println("SQL: Date of password change was reset");
        return this;

    }

    public PasswordActionsSql changePasswordEEM(String moniker, String password, String companyNumber,
                                                               String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        String connSchemaEem = new RemoteServerAct().getConnSchemaEem(serverName, dbNumberStaging, dbNumberDemo);
        String connDbEem = new RemoteServerAct().getConnDbEem(serverName, dbNumberStaging, dbNumberDemo);
        String contentSQL = "conn " + connSchemaEem + "-owner/xowner@" + connDbEem + "\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "DECLARE\n" +
                "  l_agree_num     t_agree.num%TYPE    := '"+companyNumber+"';\n" +
                "  l_user_moniker  t_user.moniker%TYPE := '"+moniker+"';\n" +
                "  l_id_user       t_user.id_user%TYPE;\n" +
                "BEGIN\n" +
                "update t_user set passwd = pk_ezuser.return_hash('" + password + "', id_user) where moniker = '" + moniker + "';\n" +
                "  l_id_user := eemo_user.find_by_moniker (a_id_corp => eemo_agree.get_user(a_id_agree => eemo_agree" +
                ".find_by_num (a_num => '"+companyNumber+"'))\n" +
                "                                         ,a_moniker => '"+moniker+"');\n" +
                "  pk_profile.edit(a_group   => 'P'\n" +
                "                 ,a_type    => 'J'\n" +
                "                 ,a_value   => TO_CHAR(SYSDATE, 'DD.MM.YYYY HH24:MI:SS')\n" +
                "                 ,a_id_user => l_id_user\n" +
                "                 ,a_auth    => 'Y');\n" +
                "\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "";
        RemoteServerAct ResetPassword = new RemoteServerAct();
        ResetPassword.createTempSqlFile(contentSQL);
        ResetPassword.putTempFileOnServer("autoload");
        ResetPassword.executeSql();
        logger.info("SQL: Password was changed and date of password change was reset");
        return this;

    }

    public PasswordActionsSql changePasswordEemByEmployeeNumber(String employeeNum, String password, String
            companyNumber,
                                                String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        String connSchemaEem = new RemoteServerAct().getConnSchemaEem(serverName, dbNumberStaging, dbNumberDemo);
        String connDbEem = new RemoteServerAct().getConnDbEem(serverName, dbNumberStaging, dbNumberDemo);
        String contentSQL = "conn " + connSchemaEem + "-owner/xowner@" + connDbEem + "\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "DECLARE\n" +
                "  l_agree_num     t_agree.num%TYPE    := '"+companyNumber+"';\n" +
                "  l_user_moniker  t_user.moniker%TYPE;\n" +
                "  l_id_user       t_user.id_user%TYPE;\n" +
                "  l_user_empnum   t_user.name%TYPE := '"+employeeNum+"';\n" +
                "BEGIN\n" +
                "select moniker into l_user_moniker from t_user where name = l_user_empnum and corp=(select id_user " +
                "from t_agree " +
                "where num=l_agree_num);\n" +
                "update t_user set passwd = pk_ezuser.return_hash('" + password + "', id_user) where moniker = " +
                "l_user_moniker;\n" +
                "  l_id_user := eemo_user.find_by_moniker (a_id_corp => eemo_agree.get_user(a_id_agree => eemo_agree" +
                ".find_by_num (a_num => '"+companyNumber+"'))\n" +
                "                                         ,a_moniker => l_user_moniker);\n" +
                "  pk_profile.edit(a_group   => 'P'\n" +
                "                 ,a_type    => 'J'\n" +
                "                 ,a_value   => TO_CHAR(SYSDATE, 'DD.MM.YYYY HH24:MI:SS')\n" +
                "                 ,a_id_user => l_id_user\n" +
                "                 ,a_auth    => 'Y');\n" +
                "\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "";
        RemoteServerAct ResetPassword = new RemoteServerAct();
        ResetPassword.createTempSqlFile(contentSQL);
        ResetPassword.putTempFileOnServer("autoload");
        ResetPassword.executeSql();
        logger.info("SQL: Password was changed and date of password change was reset");
        return this;

    }


}
