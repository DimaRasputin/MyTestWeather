package net.extracode.selenium.ezwim.util.remote;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CreateCallsSql {

    //original file content
    /*
/* $Id$
 * <SCHEME> OPER </SCHEME>
 /

    set define off
    set serveroutput on

            DECLARE
    l_tabCall                       pk_autotest.tabParams;
    BEGIN
  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));
  -- template values for call
    l_tabCall('company_num') := '20170918';
    l_tabCall('opercode') := 'EECSS';
    l_tabCall('invoice') := '20170918';
    l_tabCall('client_num') := '20170918';
    l_tabCall('paym_agree') := '20170918';
    l_tabCall('low_cost') := '0'; -- in case of empty 'summa' then random cost
    l_tabCall('high_cost') := '5'; -- in case of empty 'summa' then random cost
    l_tabCall('type') := 'C'; -- Usage -- SERV_TYPES
    l_tabCall('dt') := ''; -- empty then random
    l_tabCall('dt_end') := '';
    l_tabCall('abonent') := '';
    l_tabCall('country') := '';
    l_tabCall('traffic_kb') := '';
    l_tabCall('accounting') := ''; -- SERV_ACCOUNTING_TYPES
    l_tabCall('accflag') := '';
    l_tabCall('acc_id') := '';
    l_tabCall('vat') := '';
    l_tabCall('id_project') := '';
    l_tabCall('budget') := '';
    l_tabCall('oper_code1') := '';
    l_tabCall('oper_code2') := '';
    l_tabCall('oper_code3') := '';
    l_tabCall('oper_code4') := '';
    l_tabCall('oper_code5') := '';
    l_tabCall('oper_code6') := '';
    l_tabCall('oper_code7') := '';
    l_tabCall('oper_code8') := '';
    l_tabCall('oper_code9') := '';
    l_tabCall('country_iso') := 'gb'; -- country of destination iso code
    l_tabCall('country_r_iso') := 'gb'; -- country of origin iso code

  -- defined values for call
    l_tabCall('phone_number') := '07000000019'; -- NOT empty
    l_tabCall('dest') := 'N'; -- National -- DESTINATNoFalloutFileIfEmpUserDoesNotExistAndAssetWasNotCreated_ALL_STAGING_EEM_BR_02640AndALL_STAGING_EEM_BR_02648IONS
    l_tabCall('subdest') := 'To mobile phones';
    l_tabCall('duration') := '59';
    l_tabCall('summa') := '0,02';
  pk_autotest.add_call(l_tabCall);
    l_tabCall('duration') := '743';
    l_tabCall('summa') := '0,99';
  pk_autotest.add_call(l_tabCall);
    l_tabCall('duration') := '1054';
    l_tabCall('summa') := '1,79';
  pk_autotest.add_call(l_tabCall);
    l_tabCall('dest') := 'R'; -- Roaming -- DESTINATIONS
    l_tabCall('subdest') := 'Call made to mobile phones';
    l_tabCall('duration') := '50';
    l_tabCall('summa') := '';
  pk_autotest.add_call(l_tabCall);

    l_tabCall('duration') := '';
    l_tabCall('summa') := '';

    l_tabCall('dest') := 'D'; -- Data -- DESTINATIONS
    l_tabCall('subdest') := '';
    l_tabCall('traffic_kb') := '1234,56';
    l_tabCall('abonent') := 'mobile internet';
  pk_autotest.add_call(l_tabCall);
    l_tabCall('dest') := 'M'; -- SMS MMS -- DESTINATIONS
    l_tabCall('subdest') := 'SMS national outgoing';
    l_tabCall('traffic_kb') := '';
    l_tabCall('abonent') := '';
  pk_autotest.add_call(l_tabCall);

    l_tabCall('phone_number') := '07000000020'; -- NOT empty
    l_tabCall('dest') := 'N'; -- National -- DESTINATIONS
    l_tabCall('subdest') := 'To mobile phones';
  pk_autotest.add_call(l_tabCall);

  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));
  DBMS_OUTPUT.put_line('CDRs are added. Recalculating cache...');
  -- after all need to recalculate cache
  pk_autotest.recalculate_cache(l_tabCall);
  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));
  DBMS_OUTPUT.put_line('Recalculating cache is complete.');

    END;
    /



     */
    private static final Logger LOGGER = LogManager.getLogger(CreateCallsSql.class.getSimpleName());
    StringBuilder compose = new StringBuilder();
    String contentSQL = null;
    String serverName;
    String connSchema;
    String connDb;
    String callCompanyNumber = "";
    String callOperator = "";
    String callInvoice = "";
    String callClientNumber = "";
    String callPaymentAgreement = "";
    String callType = "";
    String callDate = "";
    String callDateEnd = "";
    String callAbonent = "";
    String callCountry = "";
    String callCountryOrigin = "";
    String callCountryDestination = "";
    String callPhoneNumber = "";
    String callDestination = "";
    String callSubDestination = "";
    String callDuration = "";
    String callTraffic = "";
    String callCost = "";
    String callOperCode1 = "";
    String callOperCode2 = "";
    String callOperCode3 = "";
    String callOperCode4 = "";
    String callOperCode5 = "";
    String callOperCode6 = "";
    String callOperCode7 = "";
    String callOperCode8 = "";
    String callOperCode9 = "";
    // this constructor is needed to define host name and db credentials based on url in properties file
    public CreateCallsSql(String dbNumberStaging, String dbNumberDemo) throws IOException {
        serverName = new RemoteServerAct().getServerName();
        connSchema = new RemoteServerAct().getConnSchemaEem(serverName, dbNumberStaging, dbNumberDemo);
        connDb = new RemoteServerAct().getConnDbEem(serverName, dbNumberStaging, dbNumberDemo);

    }

    public CreateCallsSql setCompanyNumber(String number) {
        callCompanyNumber = number;
        return this;
    }

    public CreateCallsSql setOperator(String operator) {
        callOperator = operator;
        return this;
    }

    public CreateCallsSql setInvoice(String invoice) {
        callInvoice = invoice;
        return this;
    }

    public CreateCallsSql setClientNumber(String clientNumber) {
        callClientNumber = clientNumber;
        return this;
    }

    public CreateCallsSql setPaymentAgreement(String paymentAgreement) {
        callPaymentAgreement = paymentAgreement;
        return this;
    }

    public CreateCallsSql setType(String type) {
        callType = type;
        return this;
    }

    public CreateCallsSql setDate(String date) {
        callDate = date;
        return this;
    }

    public CreateCallsSql setDateEnd(String dateEnd) {
        callDateEnd = dateEnd;
        return this;
    }

    public CreateCallsSql setAbonent(String abonent) {
        callAbonent = abonent;
        return this;
    }

    public CreateCallsSql setCountry(String country) {
        callCountry = country;
        return this;
    }

    public CreateCallsSql setCountryOrigin(String countryOriginIso) {
        callCountryOrigin = countryOriginIso;
        return this;
    }

    public CreateCallsSql setCountryDestination(String countryDestinationIso) {
        callCountryDestination = countryDestinationIso;
        return this;
    }

    public CreateCallsSql setPhoneNumber(String phoneNumber) {
        callPhoneNumber = phoneNumber;
        return this;
    }

    public CreateCallsSql setDestination(String destination) {
        callDestination = destination;
        return this;
    }

    public CreateCallsSql setSubDestination(String subDestination) {
        callSubDestination = subDestination;
        return this;
    }

    public CreateCallsSql setDuration(String duration) {
        callDuration = duration;
        return this;
    }

    public CreateCallsSql setTrafficKb(String trafficKb) {
        callTraffic = trafficKb;
        return this;
    }


    public CreateCallsSql setCost(String cost) {
        callCost = cost;
        return this;
    }

    public CreateCallsSql setcallOperCode2(String OperCode2) {
        callOperCode2 = OperCode2;
        return this;
    }

    public CreateCallsSql setcallOperCode1(String OperCode1) {
        callOperCode1 = OperCode1;
        return this;
    }

    public CreateCallsSql addCall() {
        String call = "  l_tabCall('type') := '" + callType + "'; -- Usage -- SERV_TYPES\n" +
                "  l_tabCall('dt') := '" + callDate + "'; -- empty then random\n" +
                "  l_tabCall('opercode') := '" + callOperator + "';\n" +
                "  l_tabCall('dt_end') := '" + callDateEnd + "';\n" +
                "  l_tabCall('abonent') := '" + callAbonent + "';\n" +
                "  l_tabCall('country') := '" + callCountry + "';\n" +
                "  l_tabCall('accounting') := ''; -- SERV_ACCOUNTING_TYPES\n" +
                "  l_tabCall('accflag') := '';\n" +
                "  l_tabCall('acc_id') := '';\n" +
                "  l_tabCall('vat') := '';\n" +
                "  l_tabCall('id_project') := '';\n" +
                "  l_tabCall('budget') := '';\n" +
                "  l_tabCall('oper_code1') := '" + callOperCode1 + "';\n" +
                "  l_tabCall('oper_code2') := '" + callOperCode2 + "';\n" +
                "  l_tabCall('oper_code3') := '" + callOperCode3 + "';\n" +
                "  l_tabCall('oper_code4') := '" + callOperCode4 + "';\n" +
                "  l_tabCall('oper_code5') := '" + callOperCode5 + "';\n" +
                "  l_tabCall('oper_code6') := '" + callOperCode6 + "';\n" +
                "  l_tabCall('oper_code7') := '" + callOperCode7 + "';\n" +
                "  l_tabCall('oper_code8') := '" + callOperCode8 + "';\n" +
                "  l_tabCall('oper_code9') := '" + callOperCode9 + "';\n" +
                "  l_tabCall('country_iso') := '" + callCountryDestination + "'; -- country of destination iso code\n" +
                "  l_tabCall('country_r_iso') := '" + callCountryOrigin + "'; -- country of origin iso code\n" +
                "    l_tabCall('phone_number') := '" + callPhoneNumber + "'; -- NOT empty\n" +
                "    l_tabCall('dest') := '" + callDestination + "'; -- SMS MMS -- DESTINATIONS\n" +
                "    l_tabCall('subdest') := '" + callSubDestination + "';\n" +
                "    l_tabCall('duration') := '" + callDuration + "';\n" +
                "    l_tabCall('traffic_kb') := '" + callTraffic + "';\n" +
                "    l_tabCall('summa') := '" + callCost + "';\n" +
                "  pk_autotest.add_call(l_tabCall);\n" +
                "  \n";

        compose.append(call);
        return this;
    }

    public CreateCallsSql addSeveralCalls(int startCycleNum, int endCycleNum, Map<String, String> callMap) {
        String phoneNumber = callMap.get("phoneNumber");
        String cost = callMap.get("cost");
        String date = callMap.get("date");
        for (int i = startCycleNum; i <= endCycleNum; i++) {
            String dateC = date;
            String phoneNumberC = phoneNumber.replace("##i", Integer.toString(i));
            String costC = cost.replace("##i", Integer.toString(i));
            this
                    //CDR1: International (minutes) : 1:01
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("B")
                    .setSubDestination("To mobile phones")
                    .setDate(dateC)
                    .setDuration("61")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR2: Data National (MB) : 10.5
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("12345678")
                    .setCountryOrigin("sg")
                    .setCountryDestination("")
                    .setDestination("D")
                    .setSubDestination("National")
                    .setDate(dateC)
                    .setDuration("0")
                    .setTrafficKb("10752")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR3: Data Roaming (MB) : 20.5
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("D")
                    .setSubDestination("Roaming")
                    .setDate(dateC)
                    .setDuration("0")
                    .setTrafficKb("20992")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR4: Internal (minutes) : 2:02
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("I")
                    .setSubDestination("Off net")
                    .setDate(dateC)
                    .setDuration("122")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR5: SMS
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("M")
                    .setSubDestination("SMS roaming outgoing")
                    .setDate(dateC)
                    .setDuration("0")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR6: Roaming (minutes) : 3:03
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("R")
                    .setSubDestination("Call made to mobile phones")
                    .setDate(dateC)
                    .setDuration("183")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR6: Services
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("S")
                    .setSubDestination("SMS Services")
                    .setDate(dateC)
                    .setDuration("0")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR7: National to mobile (minutes) : 4:04
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("N")
                    .setSubDestination("National to mobile")
                    .setDate(dateC)
                    .setDuration("244")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall()

                    //CDR8: National to fixed/other (minutes) : 5:05
                    .setPhoneNumber(phoneNumberC)
                    .setAbonent("1234567")
                    .setCountryOrigin("sg")
                    .setCountryDestination("gb")
                    .setDestination("N")
                    .setSubDestination("National to fixed/other")
                    .setDate(dateC)
                    .setDuration("305")
                    .setTrafficKb("0")
                    .setType("C") //Service types: Usage
                    .setCost(costC)
                    .addCall();
        }
        return this;
    }

    //this method creates final content of file, pushes it on server and executes it in sqlplus
    public CreateCallsSql save() throws IOException {
        contentSQL = "conn " + connSchema + "_oper/xoper@" + connDb + "\n" +
                "set define off\n" +
                "set serveroutput on\n" +
                "whenever sqlerror exit sql.sqlcode rollback\n" +
                "\n" +
                "DECLARE\n" +
                "  l_tabCall                       pk_autotest.tabParams;\n" +
                "BEGIN\n" +
                "  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));\n" +
                "  -- template values for call\n" +
                "  l_tabCall('company_num') := '" + callCompanyNumber + "';\n" +

                "  l_tabCall('invoice') := '" + callInvoice + "';\n" +
                "  l_tabCall('client_num') := '" + callClientNumber + "';\n" +
                "  l_tabCall('paym_agree') := '" + callPaymentAgreement + "';\n" +
                "  l_tabCall('low_cost') := '0'; -- in case of empty 'summa' then random cost\n" +
                "  l_tabCall('high_cost') := '5'; -- in case of empty 'summa' then random cost\n" +
                "  \n" +
                "  -- defined values for call\n" +
                "\n" +
                "  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));\n" +
                "  DBMS_OUTPUT.put_line('CDRs are added. Recalculating cache...');\n" +
                "  -- after all need to recalculate cache\n" +
                "  pk_autotest.recalculate_cache(l_tabCall);\n" +
                "  DBMS_OUTPUT.put_line(TO_CHAR(SYSDATE, 'HH24:MI:SS'));\n" +
                "  DBMS_OUTPUT.put_line('Recalculating cache is complete.');\n" +
                "  \n" +
                "END;\n" +
                "/\n" +
                "commit;\n" +
                "exit;";

        String additionalLines = compose.toString();
        String contentFinal = null;
        if (!additionalLines.isEmpty()) {
            contentFinal = contentSQL.replace("  -- defined values for call",
                    "  -- defined values for call\n" + additionalLines);
        } else
            contentFinal = contentSQL;
        RemoteServerAct newCall = new RemoteServerAct();
        newCall.createTempSqlFile(contentFinal);
        newCall.putTempFileOnServer("autoload");
        newCall.executeSql();
        LOGGER.info("SQL: Calls uploaded in company");
        return this;
    }
}
