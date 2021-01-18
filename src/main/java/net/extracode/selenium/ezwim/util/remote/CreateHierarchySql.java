package net.extracode.selenium.ezwim.util.remote;

import java.io.IOException;

public class CreateHierarchySql {

    //original file content
    /*

 set define off

DECLARE
  l_tabCompanySettings            pk_autotest.tabParams;
BEGIN
  l_tabCompanySettings('company_num') := '20170918';
  l_tabCompanySettings('hierarchy_level1') := 'div';
  l_tabCompanySettings('hierarchy_level2') := 'cc';
  l_tabCompanySettings('number_of_users') := '10'; -- min number of users
  pk_autotest.add_hierarchy(l_tabCompanySettings);
END;
/

     */

    StringBuilder compose = new StringBuilder();
    String contentSQL = null;
    String serverName;
    String connSchema;
    String connDb;

    // this constructor is needed to define host name and db credentials based on url in properties file
    public CreateHierarchySql(String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        connSchema = new RemoteServerAct().getConnSchemaEem(serverName,dbNumberStaging,dbNumberDemo);
        connDb = new RemoteServerAct().getConnDbEem(serverName,dbNumberStaging,dbNumberDemo);
    }

    String hierarCompanyNumber = "";
    String hierarLevel1 = "";
    String hierarLevel2 = "";
    String hierarNumberUsers = "0"; // by default random users will not be created



    public CreateHierarchySql setCompanyNumber(String number) {
        hierarCompanyNumber = number;
        return this;
    }

    public CreateHierarchySql setHierarchy1(String hierarchy1) {
        hierarLevel1 = hierarchy1;
        return this;
    }

    public CreateHierarchySql setHierarchy2(String hierarchy2) {
        hierarLevel2 = hierarchy2;
        return this;
    }

    public  CreateHierarchySql setNumberUsers(String numberUsers) {
        hierarNumberUsers = numberUsers; //min number should be > 2
        return this;
    }



    public CreateHierarchySql addHierarchy() {
        String hierarchy2Line;
        if (hierarLevel2.isEmpty()) {
            hierarchy2Line = "";
        } else {
            hierarchy2Line = "  l_tabCompanySettings('hierarchy_level2') := '" + hierarLevel2 + "';\n";
        }

        String hierarchy = "  l_tabCompanySettings('company_num') := '" + hierarCompanyNumber + "';\n" +
        "  l_tabCompanySettings('hierarchy_level1') := '" + hierarLevel1 + "';\n" +
        hierarchy2Line +
        "  l_tabCompanySettings('number_of_users') := '" + hierarNumberUsers + "'; -- min number of users\n" +
        "  pk_autotest.add_hierarchy(l_tabCompanySettings);\n";

        compose.append(hierarchy);
        return this;
    }

    //this method creates final content of file, pushes it on server and executes it in sqlplus
    public CreateHierarchySql save() throws IOException {
        contentSQL = "conn " + connSchema + "_oper/xoper@" + connDb + "\n" +
                "set define off\n" +
                "set serveroutput on\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "\n" +
                "DECLARE\n" +
                "  l_tabCompanySettings            pk_autotest.tabParams;\n" +
                "BEGIN\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "exit;";

        String additionalLines = compose.toString();
        String contentFinal = null;
        if (!additionalLines.isEmpty()) {
            contentFinal = contentSQL.replace("BEGIN", "BEGIN\n" + additionalLines);
        }
        else
            contentFinal = contentSQL;
        RemoteServerAct newHierarchy = new RemoteServerAct();
        newHierarchy.createTempSqlFile(contentFinal);
        newHierarchy.putTempFileOnServer("autoload");
        newHierarchy.executeSql();
        System.out.println("SQL: Hierarchy was created");
        return this;
    }
}
