package net.extracode.selenium.ezwim.util.remote;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateCompanySql {

    //original file content
    /**

    set define off

            DECLARE
    l_tabAgree                      pk_autotest.tabParams;
    BEGIN
  -- for extra attributes. creation session
    l_tabAgree('cs_admin_moniker') := 'sysadmin'; -- active sysadmin moniker
  -- Company card
    l_tabAgree('num') := '20180803002';--TO_CHAR(SYSDATE, 'YYYYMMDD');
    l_tabAgree('name') := 'CREATE COMPANY TEST6';
    l_tabAgree('reseller') := 'EE';
    l_tabAgree('dtstart') := TO_CHAR(SYSDATE, 'DD.MM.YYYY');
    l_tabAgree('email') := 'm.muraveva@extracode.net';
    l_tabAgree('freezing_allocates_calls_to') := '2'; -- 1/2
    l_tabAgree('freeze_delay') := '1'; -- 1/2/3/4/5/6/-1
    l_tabAgree('allocation_method') := 'N'; --N/E
    l_tabAgree('project_allocation_method') := 'N'; --N/P
    l_tabAgree('egv_company') := '';
    l_tabAgree('egv_country') := '';
    l_tabAgree('date_display_format') := 'dd.mm.yyyy';
    l_tabAgree('currency') := 'GBP'; -- see CURRENCYKINDS at 1_t_enum.sql
    l_tabAgree('number_of_in_one') := '';
    l_tabAgree('components_list') := 'BANEW'; --Ezwim split billing advanced, Ezwim analysis, Ezwim fleet management, Ezwim fleet management advanced
    l_tabAgree('mod_fleet') := 'Y'; -- Y | N  -- Ezwim fleet management
    l_tabAgree('customized_comp_list') := '';
    l_tabAgree('admins_number') := '';
    l_tabAgree('starting_month') := '01.09.2016';
    l_tabAgree('operator') := 'EECSS'; -- see 9_t_enum_operators.sql
    l_tabAgree('start_month') := '09.2016'; -- for scheduler line
    l_tabAgree('billing_cycle_ee') := '3'; -- 1/3/5/8/10/12/15/17/19/22/24/26
    l_tabAgree('default_language') := 'E';
    l_tabAgree('languages_1') := 'E';
    l_tabAgree('languages_2') := 'C';
      l_tabAgree('mail_template_1') := 'a_mail_template_name=Notification Manager - EE - EN&a_mail_template_kind=M'; -- instead of id
  l_tabAgree('case_type_1') := 'a_w_ct_id=EE - Apple Dep Enrolment&a_w_ct_bred=Y&a_w_ct_sa=Y&a_w_ct_savpot=Y&a_w_ct_all=Y';
  l_tabAgree('data_push_1') := 'a_push_type=X&a_push_moment=I&a_push_method=E&a_push_target=patrick@ezwim-mail.com&a_push_sched=&a_push_notif=&a_push_case_type=';
  l_tabAgree('extra_user_attr_1') := 'a_template_kind=U&a_ex_num=1&a_ex_name=test&a_ex_type=S&a_ex_format=S&a_ex_list=';
  l_tabAgree('extra_phone_attr_1') := 'a_template_kind=P&a_ex_num_ph=1&a_ex_name_ph=test&a_ex_type_ph=S&a_ex_format_ph=S&a_ex_list_ph=';
  l_tabAgree('extra_asset_attr_1') := 'a_template_kind=D&a_ex_num_asset=1&a_extra_type_old=Q&a_ex_name_asset=test&a_ex_type_asset=S&a_ex_format_asset=S&a_ex_list_asset=';
     -- type software license
  l_tabAgree('extra_account_attr_1') := 'a_inp_acc_ex_num=1&a_inp_acc_ex_name=testo&a_sel_acc_ex_type=S&a_acc_ex_format=S&a_sel_acc_ex_reflist=&a_sel_acc_ex_value=&a_inp_acc_ex_value=new';
  l_tabAgree('extra_account_attr_2') := 'a_inp_acc_ex_num=2&a_inp_acc_ex_name=testo2&a_sel_acc_ex_type=S&a_acc_ex_format=S&a_sel_acc_ex_reflist=&a_sel_acc_ex_value=&a_inp_acc_ex_value=test';

  pk_autotest.del_agree(l_tabAgree('num'), l_tabAgree('reseller'));
  pk_autotest.add_agree(l_tabAgree);

  -- Company settings at EEM
  l_tabAgree('hierarchy_level1_name') := 'Division';
  l_tabAgree('hierarchy_level2_name') := 'Cost-center';
  l_tabAgree('category1_name') := 'Category 1';
  l_tabAgree('category2_name') := 'Category 2';
  l_tabAgree('password_notification') := '';
  l_tabAgree('registration_notification') := '';
  l_tabAgree('reminder') := '';
  l_tabAgree('send_password_in_separate_notification') := '';
  l_tabAgree('cc_manager_notification') := '';
  l_tabAgree('notification') := '';
  l_tabAgree('loading_success') := '';
  pk_autotest.change_company_settings(l_tabAgree);

END;
/

     */

    private static final Logger LOGGER = LogManager.getLogger(CreateCompanySql.class.getSimpleName());

    StringBuilder compose = new StringBuilder();
    String contentSQL = null;
    String serverName;
    String connSchema;
    String connDb;

    // this constructor is needed to define host name and db credentials based on url in properties file
    public CreateCompanySql(String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        LOGGER.info("Server = " + serverName);
        connSchema = new RemoteServerAct().getConnSchemaEem(serverName,dbNumberStaging,dbNumberDemo);
        connDb = new RemoteServerAct().getConnDbEem(serverName,dbNumberStaging,dbNumberDemo);
        SimpleDateFormat formatDate = new SimpleDateFormat("MM.yyyy");
        Calendar c = Calendar.getInstance();
        boolean isSummer = TimeZone.getTimeZone("ECT").inDaylightTime(new Date());
        if (isSummer) {
            c.add(Calendar.HOUR, -1);
        } else {
            c.add(Calendar.HOUR, -2);
        }
        compStartingMonth = "01." + formatDate.format(c.getTime());
    }

    String compNumber = "20180803002";
    String compName = "AutotestCompany";
    String compReseller = "EE";
    String compEmail = "patrick@ezwim-mail.com";
    String compFreezingAllocatesCallsTo = "2";
    String compFreezeDelay = "1";
    String compAllocationMethod = "N";
    String compProjectAllocationMethod = "N";
    String compEgvCompany = "";
    String compEgvCountry = "";
    String compDateDisplayFormat = "dd.mm.yyyy";
    String compCurrency = "GBP";
    String compNumberOfCurrencyInOneEgvCurrency = "";
    String compComponentsList = "BAfEW";
    String compFleetManagement = "Y";
    String compCustomizedComponentsList = "";
    String compNumberOfAdmins = "";
    String compStartingMonth;
    String compMainBillingCycle = "1";
    String compAllowedNumberUser = "";
    String compOperator = "EECSS";
//    String compStartMonth = "8.2018";
    String compStartMonth = "";
    String compBillingCycleEE = "";
    String compDefaultLanguage = "E";
    String compLanguageCode = "E";
//    String compMailTemplateParam = "a_mail_template_name=Notification Manager - EE - EN&a_mail_template_kind=M";
    String compMailTemplateParam = "";
//    String compCaseType = "a_w_ct_id=EE - Apple Dep Enrolment&a_w_ct_bred=Y&a_w_ct_sa=Y&a_w_ct_savpot=Y&a_w_ct_all=Y";
    String compCaseType = "";
//    String compDataPush = "a_push_type=X&a_push_moment=I&a_push_method=E&a_push_target=patrick@ezwim-mail.com&a_push_sched=&a_push_notif=&a_push_case_type=";
    String compDataPush = "";
    //    String compExtraUserAttr = "a_template_kind=U&a_ex_num=1&a_ex_name=test&a_ex_type=S&a_ex_format=S&a_ex_list=";
//    String compExtraPhoneAttr = "a_template_kind=P&a_ex_num_ph=1&a_ex_name_ph=test&a_ex_type_ph=S&a_ex_format_ph=S&a_ex_list_ph=";
//    String compExtraAssetAttr = "a_template_kind=D&a_ex_num_asset=1&a_extra_type_old=Q&a_ex_name_asset=test&a_ex_type_asset=S&a_ex_format_asset=S&a_ex_list_asset=";
//    String compExtraAccountAttr = "a_inp_acc_ex_num=1&a_inp_acc_ex_name=testo&a_sel_acc_ex_type=S&a_acc_ex_format=S&a_sel_acc_ex_reflist=&a_sel_acc_ex_value=&a_inp_acc_ex_value=new";
    String compExtraUserAttr = "";
    String compExtraPhoneAttr = "";
    String compExtraAssetAttr = "";
    String compExtraAccountAttr = "";

    public CreateCompanySql setCompanyNumber(String number) throws IOException {
        compNumber = number;
        return this;
    }

    public CreateCompanySql setCompanyName (String name) throws IOException {
        compName = name;
        return this;
    }

    public CreateCompanySql setReseller(String reseller) throws IOException {
        compReseller = reseller;
        return this;
    }

    public CreateCompanySql setCompanyEmail(String email) throws IOException {
        compEmail = email;
        return this;
    }

    public CreateCompanySql setFreezingAllocatesCallsTo(String categoryNumber) throws IOException {
        //category 1 or Category 2. Expected input is 1 or 2
        compFreezingAllocatesCallsTo = categoryNumber;
        return this;
    }

    public CreateCompanySql setFreezeDelay(String freezeDelay) throws IOException {
        compFreezeDelay = freezeDelay;
        return this;
    }

    public CreateCompanySql setAllocationMethod(String allocationMethod) throws IOException {
        compAllocationMethod = allocationMethod;
        return this;
    }

    public CreateCompanySql setProjectAllocationMethod(String projectAllocationMethod) throws IOException {
        compProjectAllocationMethod = projectAllocationMethod;
        return this;
    }

    public CreateCompanySql setEgvCompany(String egvCompany) throws IOException {
        compEgvCompany = egvCompany;
        return this;
    }

    public CreateCompanySql setEgvCountry(String egvCountry) throws IOException {
        compEgvCountry = egvCountry;
        return this;
    }

    public CreateCompanySql setDateDisplayFormat(String dateDisplayFormat) throws IOException {
        compDateDisplayFormat = dateDisplayFormat;
        return this;
    }

    public CreateCompanySql setCurrency(String currency) throws IOException {
        compCurrency = currency;
        return this;
    }

    public CreateCompanySql setNumberOfCurrencyInOneEgvCurrency(String numberOfCurrencyInOneEgvCurrency) throws IOException {
        compNumberOfCurrencyInOneEgvCurrency = numberOfCurrencyInOneEgvCurrency;
        return this;
    }

    public CreateCompanySql setComponentsList(String componentsList) throws IOException {
        compComponentsList = componentsList;
        return this;
    }

    public CreateCompanySql setFleetManagement (String fleetManagement) throws IOException {
        compFleetManagement = fleetManagement;
        return this;
    }

    public CreateCompanySql setCustomizedComponentsList(String customizedComponentsList) throws IOException {
        compCustomizedComponentsList = customizedComponentsList;
        return this;
    }

    public CreateCompanySql setNumberOfAdmins(String numberOfAdmins) throws IOException {
        compNumberOfAdmins = numberOfAdmins;
        return this;
    }

    public CreateCompanySql setStartingMonth(String startingMonth) throws IOException {
        compStartingMonth = startingMonth;
        return this;
    }

    public CreateCompanySql setMainBillingCycle(String mainBillingCycle) throws IOException {
        compMainBillingCycle = mainBillingCycle;
        return this;
    }

    public CreateCompanySql setAllowedNumberUser(String allowedNumberUser) throws IOException {
        compAllowedNumberUser = allowedNumberUser;
        return this;
    }

    public CreateCompanySql setOperator(String operator) throws IOException {
        compOperator = operator;
        return this;
    }

    public CreateCompanySql setStartMonth(String startMonth) throws IOException {
        compStartMonth = startMonth;
        return this;
    }

    public CreateCompanySql setBillingCycleEE(String billingCycleEE) throws IOException {
        compBillingCycleEE = billingCycleEE;
        return this;
    }

    public CreateCompanySql setDefaultLanguage(String defaultLanguage) throws IOException {
        compDefaultLanguage = defaultLanguage;
        return this;
    }

    public CreateCompanySql setLanguages(String languageNumber, String languageCode) throws IOException {
        if (languageNumber.equals("1"))
        {compLanguageCode = languageCode;}
        else {
            String addNewParam = "  l_tabAgree('languages_" + languageNumber + "') := '" + languageCode + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setMailTemplates(String lineNumber, String mailTemplateParam) throws IOException {
        if (lineNumber.equals("1")) {
            compMailTemplateParam = mailTemplateParam;
        } else {
            String addNewParam = "  l_tabAgree('mail_template_" + lineNumber + "') := '" + mailTemplateParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setCaseTypes(String lineNumber, String caseTypeParam) throws IOException {
        if (lineNumber.equals("1"))
        {compCaseType = caseTypeParam;}
        else {
            String addNewParam = "  l_tabAgree('case_type_" + lineNumber + "') := '" + caseTypeParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setDataPushes(String lineNumber, String dataPushParam) throws IOException {
        if (lineNumber.equals("1"))
        {compDataPush = dataPushParam;}
        else {
            String addNewParam = "  l_tabAgree('data_push_" + lineNumber + "') := '" + dataPushParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setExtraUserAttrs(String lineNumber, String extraUserAttrParam) throws IOException {
        if (lineNumber.equals("1"))
        {compExtraUserAttr = extraUserAttrParam;}
        else {
            String addNewParam = "  l_tabAgree('extra_user_attr_" + lineNumber + "') := '" + extraUserAttrParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setExtraPhoneAttrs(String lineNumber, String extraPhoneAttrParam) throws IOException {
        if (lineNumber.equals("1"))
        {compExtraPhoneAttr = extraPhoneAttrParam;}
        else {
            String addNewParam = "  l_tabAgree('extra_phone_attr_" + lineNumber + "') := '" + extraPhoneAttrParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setExtraAssetAttrs(String lineNumber, String extraAssetAttrParam) throws IOException {
        if (lineNumber.equals("1"))
        {compExtraAssetAttr = extraAssetAttrParam;}
        else {
            String addNewParam = "  l_tabAgree('extra_asset_attr_" + lineNumber + "') := '" + extraAssetAttrParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    public CreateCompanySql setExtraAccountAttrs(String lineNumber, String extraAccountAttrParam) throws IOException {
        if (lineNumber.equals("1"))
        {compExtraAccountAttr = extraAccountAttrParam;}
        else {
            String addNewParam = "  l_tabAgree('extra_account_attr_" + lineNumber + "') := '" + extraAccountAttrParam + "';\n";
            sqlContentAdditLines(addNewParam);
        }
        return this;
    }

    //method for adding case_type_1, case_type_2, languages_1, languages_2, language_3 and so on,
    // because such lines should be inserted
    private CreateCompanySql sqlContentAdditLines(String param){
        compose.append(param);
        return this;
    }

    //this method creates final content of file, pushes it on server and executes it in sqlplus
    public CreateCompanySql save() throws IOException {
        contentSQL = "conn " + connSchema + "_oper/xoper@" + connDb + "\n" +
                "set define off\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "\n" +
                "DECLARE\n" +
                "  l_tabAgree                      pk_autotest.tabParams;\n" +
                "BEGIN\n" +
                "  -- for extra attributes. creation session\n" +
                "  l_tabAgree('cs_admin_moniker') := 'admin'; -- active sysadmin moniker\n" +
                "  -- Company card\n" +
                "  l_tabAgree('num') := '" + compNumber + "';--TO_CHAR(SYSDATE, 'YYYYMMDD');\n" +
                "  l_tabAgree('name') := '" + compName + "';\n" +
                "  l_tabAgree('reseller') := '" + compReseller + "';\n" +
                "  l_tabAgree('dtstart') := TO_CHAR(SYSDATE, 'DD.MM.YYYY');\n" +
                "  l_tabAgree('email') := '" + compEmail + "';\n" +
                "  l_tabAgree('freezing_allocates_calls_to') := '" + compFreezingAllocatesCallsTo + "'; -- 1/2\n" +
                "  l_tabAgree('freeze_delay') := '" + compFreezeDelay + "'; -- 1/2/3/4/5/6/-1\n" +
                "  l_tabAgree('allocation_method') := '" + compAllocationMethod + "'; --N/E\n" +
                "  l_tabAgree('project_allocation_method') := '" + compProjectAllocationMethod + "'; --N/P\n" +
                "  l_tabAgree('egv_company') := '" + compEgvCompany + "';\n" +
                "  l_tabAgree('egv_country') := '" + compEgvCountry + "';\n" +
                "  l_tabAgree('date_display_format') := '" + compDateDisplayFormat + "';\n" +
                "  l_tabAgree('currency') := '" + compCurrency + "'; -- see CURRENCYKINDS at 1_t_enum.sql\n" +
                "  l_tabAgree('number_of_in_one') := '" + compNumberOfCurrencyInOneEgvCurrency + "';\n" +
                "  l_tabAgree('components_list') := '" + compComponentsList + "'; --Ezwim split billing advanced, Ezwim analysis, Ezwim fleet management, Ezwim fleet management advanced\n" +
                "  l_tabAgree('mod_fleet') := '" + compFleetManagement + "';" +
                "  l_tabAgree('customized_comp_list') := '" + compCustomizedComponentsList + "';\n" +
                "  l_tabAgree('admins_number') := '" + compNumberOfAdmins + "';\n" +
                "  l_tabAgree('starting_month') := '" + compStartingMonth + "';\n" +
                "  l_tabAgree('main_billing_cycle') := '" + compMainBillingCycle + "';\n" +
                "  l_tabAgree('allowed_number_user') := '" + compAllowedNumberUser + "';\n" +
                "  l_tabAgree('operator') := '" + compOperator + "'; -- see 9_t_enum_operators.sql\n" +
                "  l_tabAgree('start_month') := '" + compStartMonth + "'; -- for scheduler line\n" +
                "  l_tabAgree('billing_cycle_ee') := '" + compBillingCycleEE + "'; -- 1/3/5/8/10/12/15/17/19/22/24/26\n" +
                "  l_tabAgree('default_language') := '" + compDefaultLanguage + "';\n" +
                "  l_tabAgree('languages_1') := '" + compLanguageCode + "';\n" +
                "  l_tabAgree('mail_template_1') := '" + compMailTemplateParam + "'; -- instead of id\n" +
                "  l_tabAgree('case_type_1') := '" + compCaseType + "';\n" +
                "  l_tabAgree('data_push_1') := '" + compDataPush + "';\n" +
                "  l_tabAgree('extra_user_attr_1') := '" + compExtraUserAttr + "';\n" +
                "  l_tabAgree('extra_phone_attr_1') := '" + compExtraPhoneAttr + "';\n" +
                "  l_tabAgree('extra_asset_attr_1') := '" + compExtraAssetAttr + "';\n" +
                "  l_tabAgree('extra_account_attr_1') := '" + compExtraAccountAttr + "';\n" +
                "  pk_autotest.del_agree(l_tabAgree('num'), l_tabAgree('reseller'));\n" +
                "  pk_autotest.add_agree(l_tabAgree);\n" +
                "\n" +
                "  -- Company settings at EEM\n" +
                "  l_tabAgree('hierarchy_level1_name') := 'Division';\n" +
                "  l_tabAgree('hierarchy_level2_name') := 'Cost-center';\n" +
                "  l_tabAgree('category1_name') := 'Category 1';\n" +
                "  l_tabAgree('category2_name') := 'Category 2';\n" +
                "  l_tabAgree('password_notification') := '';\n" +
                "  l_tabAgree('registration_notification') := '';\n" +
                "  l_tabAgree('reminder') := '';\n" +
                "  l_tabAgree('send_password_in_separate_notification') := '';\n" +
                "  l_tabAgree('cc_manager_notification') := '';\n" +
                "  l_tabAgree('notification') := '';\n" +
                "  l_tabAgree('loading_success') := '';\n" +
                "  pk_autotest.change_company_settings(l_tabAgree);\n" +
                "\n" +
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
        RemoteServerAct newCompany = new RemoteServerAct();
        newCompany.createTempSqlFile(contentFinal);
        newCompany.putTempFileOnServer("autoload");
        newCompany.executeSql();
        LOGGER.info("SQL: Company created");
        return this;
    }
}
