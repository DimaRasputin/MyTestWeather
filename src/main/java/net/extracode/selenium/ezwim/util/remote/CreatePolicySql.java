package net.extracode.selenium.ezwim.util.remote;

import java.io.IOException;

public class CreatePolicySql {

    //original file content
    /*

set define off

DECLARE
  l_tabPolicy                     pk_autotest.tabParams;
  l_tabVariable                   pk_autotest.tabParams;
  l_tabRAlert                     pk_autotest.tabParams;
BEGIN
  l_tabPolicy('company_num') := '20170918';
  l_tabPolicy('a_name') := 'General';
  l_tabPolicy('a_budget_kind') := 'U'; -- P/B/T/U
  l_tabPolicy('a_type') := 'L'; -- L - Qualitative/N - Quantitative
  l_tabPolicy('a_budget_last_year') := '1000';
  l_tabPolicy('a_budget_current_year') := '1000';
  l_tabPolicy('a_budget_next_year') := '1000';
  l_tabPolicy('a_serv_type') := 'C'; -- Service types (for Qualitative)
  l_tabPolicy('a_expression') := ''; -- (for Definition of private or business)
  l_tabPolicy('a_allocation_type') := '';
  l_tabPolicy('a_allow_negative') := 'N';
  l_tabPolicy('a_ttype') := 'P'; -- threshold kind: P-private/B-business
  l_tabPolicy('a_tval') := '25'; -- threshold value
  pk_autotest.add_policy(l_tabPolicy);

  -- variable
  l_tabVariable('company_num') := l_tabPolicy('company_num');
  l_tabVariable('a_name') := 'Test variable';
  l_tabVariable('a_sum_type') := '';
  l_tabVariable('a_ytd_box') := '';
  l_tabVariable('phone_condition_1') := '&a_ph_num=&a_ph_attr=&a_ph_con=&a_ph_sel_val=&a_ph_inp_val=';
  l_tabVariable('having_condition_1') := '&a_ha_num=&a_ha_attr=&a_ha_con=>&a_ha_xattr=&a_ha_sel_val=&a_ha_inp_val=';
  l_tabVariable('cdr_condition_1') := '&a_cdr_num=&a_cdr_attr=&a_cdr_con=&a_cdr_sel_val=&a_cdr_inp_val=';
  pk_autotest.add_variable(l_tabVariable);

  -- realtime alert
  l_tabRAlert('company_num') := l_tabPolicy('company_num');
  l_tabRAlert('a_name') := 'Test realtime alert';
  l_tabRAlert('a_type') := 'A'; -- ALERT_TYPE
  l_tabRAlert('a_variable') := eemo_variable.find_by_moniker(l_tabVariable('a_name')); -- id_variable
  l_tabRAlert('a_percent') := ''; -- for Percentage type
  l_tabRAlert('a_quantity') := '';
  l_tabRAlert('a_reciver') := 'B'; -- ALERT_RECIPIENT
  l_tabRAlert('a_email') := '1014'; -- user mail template id
  l_tabRAlert('a_email2') := '1014'; -- manager mail template id
  l_tabRAlert('a_sms') := '';
  l_tabRAlert('a_state') := 'A';
  pk_autotest.add_realtime_alert(l_tabRAlert);

END;
/



     */

    StringBuilder compose = new StringBuilder();
    String contentSQL = null;
    String serverName;
    String connSchema;
    String connDb;

    // this constructor is needed to define host name and db credentials based on url in properties file
    public CreatePolicySql(String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        connSchema = new RemoteServerAct().getConnSchemaEem(serverName,dbNumberStaging,dbNumberDemo);
        connDb = new RemoteServerAct().getConnDbEem(serverName,dbNumberStaging,dbNumberDemo);
    }

    String polCompanyNumber = "";
    String polName = "";
    String polBudgetKind = "";
    String polType = "L";
    String polBudgetLastYear = "" ;
    String polBudgetCurrentYear = "";
    String polBudgetNextYear = "";
    String polServiceType = "C";
    String polExpression = "";
    String polAllocationType = "";



    public CreatePolicySql setCompanyNumber(String number) {
        polCompanyNumber = number;
        return this;
    }

    public CreatePolicySql setPolicyName(String name) {
        polName = name;
        return this;
    }

    public CreatePolicySql setBudgetKind (String budgetKind) {
        // P/B/T/U - private, bussiness, total cost, usage cost
        polBudgetKind = budgetKind;
        return this;
    }

    public CreatePolicySql setPolicyType (String policyType) {
        polType = policyType;
        return this;
    }

    public CreatePolicySql setBudgetLastYear (String budgetLastYear) {
        polBudgetLastYear = budgetLastYear;
        return this;
    }

    public CreatePolicySql setBudgetCurrentYear (String budgetCurrentYear) {
        polBudgetCurrentYear = budgetCurrentYear;
        return this;
    }

    public CreatePolicySql setBudgetNextYear (String budgetNextYear) {
        polBudgetNextYear = budgetNextYear;
        return this;
    }

    public CreatePolicySql setServiceType (String serviceType) {
        //'Policy applies to' tab in policy card
        //many values can be entered, eg "CMO" - Usage, Main subscription, Other
        polServiceType = serviceType;
        return this;
    }

    public CreatePolicySql setExpression (String sqlExpression) {
        polExpression = sqlExpression;
        return this;
    }

    public CreatePolicySql setAllocationType (String allocationType) {
        polAllocationType = allocationType;
        return this;
    }

    public CreatePolicySql setAllowNegative () {
        return this;
    }

    public CreatePolicySql setThresholdType () {
        return this;
    }

    public CreatePolicySql setThresholdValue () {
        return this;
    }


    public CreatePolicySql addPolicy() {
        String policy =
                "  l_tabPolicy('a_name') := '" + polName + "';\n" +
                "  l_tabPolicy('a_budget_kind') := '" + polBudgetKind + "'; -- P/B/T/U\n" +
                "  l_tabPolicy('a_type') := '" + polType + "'; -- L - Qualitative/N - Quantitative\n" +
                "  l_tabPolicy('a_budget_last_year') := '" + polBudgetLastYear + "';\n" +
                "  l_tabPolicy('a_budget_current_year') := '" + polBudgetCurrentYear + "';\n" +
                "  l_tabPolicy('a_budget_next_year') := '" + polBudgetNextYear + "';\n" +
                "  l_tabPolicy('a_serv_type') := '" + polServiceType + "'; -- Service types (for Qualitative)\n" +
                "  l_tabPolicy('a_expression') := '" + polExpression + "'; -- (for Definition of private or business)\n" +
                "  l_tabPolicy('a_allocation_type') := '" + polAllocationType + "';\n" +
                "  l_tabPolicy('a_allow_negative') := 'N';\n" +
                "  l_tabPolicy('a_ttype') := 'P'; -- threshold kind: P-private/B-business\n" +
                "  l_tabPolicy('a_tval') := '25'; -- threshold value\n" +
                "  pk_autotest.add_policy(l_tabPolicy);\n";

        compose.append(policy);
        return this;
    }

    //this method creates final content of file, pushes it on server and executes it in sqlplus
    public CreatePolicySql save() throws IOException {
        contentSQL = "conn " + connSchema + "_oper/xoper@" + connDb + "\n" +
                "set define off\n" +
                "set serveroutput on\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "\n" +
                "DECLARE\n" +
                "  l_tabPolicy                     pk_autotest.tabParams;\n" +
                "BEGIN\n" +
                "  l_tabPolicy('company_num') := '" + polCompanyNumber + "';\n" +
                "-- policy details\n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "exit;";

        String additionalLines = compose.toString();
        String contentFinal = null;
        if (!additionalLines.isEmpty()) {
            contentFinal = contentSQL.replace("-- policy details", "-- policy details\n" + additionalLines);
        }
        else
            contentFinal = contentSQL;
        //System.out.println(contentFinal);
        RemoteServerAct newPolicy = new RemoteServerAct();
        newPolicy.createTempSqlFile(contentFinal);
        newPolicy.putTempFileOnServer("autoload");
        newPolicy.executeSql();
        System.out.println("SQL: Policies created");
        return this;
    }
}
