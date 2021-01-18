package net.extracode.selenium.ezwim.util;

import net.extracode.selenium.common.*;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.exception.EzwimException;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipFile;

public class GetAct {

    private static final Logger logger = LogManager
            .getLogger(GetAct.class.getSimpleName());


    public static int getLastRowNum(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName).getLastRowNum();
    }

    public static int getLastRowNum(String filename, String sheetName) throws IOException, InvalidFormatException {
        return getExcelWorkbook(filename).getSheet(sheetName).getLastRowNum();
    }

    public static int getLastRowNum(String filename, int sheetIndex) throws IOException, InvalidFormatException {
        return getExcelWorkbook(filename).getSheetAt(sheetIndex).getLastRowNum();
    }

    public static String getElementText(By by, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        String tempVar = GetAct.class.getSimpleName() + System.currentTimeMillis();
        new GetText(by, tempVar).act(driverWrapper, context, reporter);
        return (String) context.getVars().get(tempVar);
    }

    public static double getNumberFromExcelCell(Workbook workbook, String sheetName, int row, int col) throws
            IOException {
        double result = workbook.getSheet(sheetName).getRow(row).getCell(col).getNumericCellValue();
        workbook.close();
        return result;
    }

    public static double getNumberFromExcelFile(String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {
        return getNumberFromExcelCell(getExcelWorkbook(filename), sheetName, row, col);
    }

    public static double getNumberFromExcelCell(Workbook workbook, int sheetIndex, int row, int col) throws
            IOException {
        double result = workbook.getSheetAt(sheetIndex).getRow(row).getCell(col).getNumericCellValue();
        workbook.close();
        return result;
    }

    public static double getNumberFromExcelFile(String filename, int sheetIndex, int row, int col) throws IOException,
            InvalidFormatException {
        return getNumberFromExcelCell(getExcelWorkbook(filename), sheetIndex, row, col);
    }


    public static String getValueFromExcelFile(String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {

        String valueCell = " ";
        String typeCell = getTypeOfExcelCell(getExcelWorkbook(filename), sheetName, row, col);

        if (typeCell.equals("NUMERIC")) {
            valueCell = Double.toString(getNumberFromExcelCell(getExcelWorkbook(filename), sheetName, row, col));
        }
        if (typeCell.equals("STRING")) {
            valueCell = getStringFromExcelCell(getExcelWorkbook(filename), sheetName, row, col);
        }
        //this is not using because getTypeOfExcelCell date fields is returning as NUMERIC
        if (typeCell.equals("DATE")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            valueCell = dateFormat.format(getDateFromExcelCell(getExcelWorkbook(filename), sheetName, row, col));
        }

        return valueCell;
    }

    public static void validateValueFromExcelFile(String expectedValue, String expectedTypeValue, String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {

        String actualValue = getValueFromExcelFile(filename, sheetName, row, col);
        String actualTypeValue = getTypeOfExcelCell(getExcelWorkbook(filename), sheetName, row, col);

        if (!expectedTypeValue.equals("NUMERIC") && !expectedTypeValue.equals("STRING")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(expectedTypeValue);
            actualValue = dateFormat.format(getDateFromExcelCell(getExcelWorkbook(filename), sheetName, row, col));
            expectedTypeValue = actualTypeValue;
        }
        if (expectedValue.equals("SKIP")) {
            logger.info("Check of : " + col + " col in " + row + " row was skipped");
        } else {
            logger.info("Position : " + col + " col in " + row + " row");
            logger.info("Expected value: " + expectedValue);
            logger.info("Actual  value : " + actualValue);
            if (actualValue.equals(expectedValue)) {
                logger.info("Expected type: " + expectedTypeValue);
                logger.info("Actual  type : " + actualTypeValue);
                if (actualTypeValue.equals(expectedTypeValue)) {
                    logger.info("Check is passed.");
                } else throw new EzwimException("Check is failed.");
            } else throw new EzwimException("Check is failed.");
        }
    }


    public static String getTypeOfExcelCell(Workbook workbook, String sheetName, int row, int col) {
        return workbook.getSheet(sheetName).getRow(row).getCell(col).getCellType().toString();
    }

    public static String getTypeOfExcelCell(String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {
        return getTypeOfExcelCell(getExcelWorkbook(filename), sheetName, row, col);
    }

    public static Date getDateFromExcelCell(Workbook workbook, String sheetName, int row, int col) throws IOException {
        Date result = workbook.getSheet(sheetName).getRow(row).getCell(col).getDateCellValue();
        workbook.close();
        return result;
    }

    public static Date getDateFromExcelFile(String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {
        return getDateFromExcelCell(getExcelWorkbook(filename), sheetName, row, col);
    }


    public static String getElementAttr(By by, String attributeName,
                                        DriverWrapper driverWrapper, Context context, Reporter reporter) {
        String tempVar = GetAct.class.getSimpleName() + System.currentTimeMillis();
        new GetAttribute(by, tempVar, attributeName).act(driverWrapper, context, reporter);
        return (String) context.getVars().get(tempVar);
    }

    public static String getUrl(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        String tempVar = GetAct.class.getSimpleName() + System.currentTimeMillis();
        new GetCurrentUrl(tempVar).act(driverWrapper, context, reporter);
        return (String) context.getVars().get(tempVar);
    }

    public static int getCount(By by, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        String tempVar = GetAct.class.getSimpleName() + System.currentTimeMillis();
        new GetSize(by, tempVar).act(driverWrapper, context, reporter);
        return Integer.valueOf((String) context.getVars().get(tempVar));
    }

    public static Workbook getExcelWorkbook(String filename) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(new File(filename));
    }

    public static String getStringFromExcelCell(Workbook workbook, String sheetName, int row, int col) throws IOException {
        String result = workbook.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
        workbook.close();
        return result;
    }

    public static String getStringFromExcelCell(Workbook workbook, int sheetIndex, int row, int col) throws IOException {
        String result = workbook.getSheetAt(sheetIndex).getRow(row).getCell(col).getStringCellValue();
        workbook.close();
        return result;
    }

    public static String getStringFromExcelFile(String filename, String sheetName, int row, int col) throws IOException, InvalidFormatException {
        return getStringFromExcelCell(getExcelWorkbook(filename), sheetName, row, col);
    }

    public static String getStringFromExcelFile(String filename, int sheetIndex, int row, int col) throws IOException, InvalidFormatException {
        return getStringFromExcelCell(getExcelWorkbook(filename), sheetIndex, row, col);
    }

    public static CSVParser getCSVParserFromFile(String filename) throws IOException {
        return getCSVParserFromFile(filename, CSVFormat.DEFAULT);
    }

    public static CSVParser getCSVParserFromFile(String filename, CSVFormat format) throws IOException {
        return CSVParser.parse(new File(filename), Charset.defaultCharset(), format);
    }

    public static CSVParser getCSVParserFromContent(String content, CSVFormat format) throws IOException {
        return CSVParser.parse(content, format);
    }

    public static CSVParser getCSVParserFromContent(String content) throws IOException {
        return getCSVParserFromContent(content, CSVFormat.DEFAULT);
    }

    public static String getStringFromCSVCell(CSVParser csvParser, int row, int col) throws IOException {
        return csvParser.getRecords().get(row).get(col);
    }

    public static String getStringFromCSVFile(String filename, int row, int col) throws IOException {
        return getStringFromCSVFile(filename, CSVFormat.DEFAULT, row, col);
    }

    public static String getStringFromCSVFile(String filename, CSVFormat format, int row, int col) throws IOException {
        return getStringFromCSVCell(getCSVParserFromFile(filename, format), row, col);
    }

    public static String getStringFromCSVContent(String content, int row, int col) throws IOException {
        return getStringFromCSVCell(getCSVParserFromContent(content), row, col);
    }

    public static String getStringFromCSVContent(String content, CSVFormat format, int row, int col) throws IOException {
        return getStringFromCSVCell(getCSVParserFromContent(content, format), row, col);
    }

    public static byte[] getFileContentFromZip(String zipFilename, String filename) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilename);
             InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(filename))) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    public static String getValueJQuery(String jQueryExpres, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        new Script("return " + jQueryExpres + ";", "result")
                .act(driverWrapper, context, reporter);
        String result = (String) context.getVars().get("result");
        return result;
    }

    public static String getValueJSByid(String id, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        new Script("return document.getElementById('" + id + "').value;", "result")
                .act(driverWrapper, context, reporter);
        String result = (String) context.getVars().get("result");
        return result;
    }

    public static String getValueJSByXpath(String xpath, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        new Script(
                "function getElementByXpath(path) {return document" +
                        ".evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;} " +
                        "return(getElementByXpath(\"" + xpath + "\").value );", "result")
                .act(driverWrapper, context, reporter);
        String result = (String) context.getVars().get("result");
        return result;
    }

    public static int getNumberOpenedWindows(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        Set<String> windowHandles = driverWrapper.getWindowHandles();
        return windowHandles.size();
    }

    public static Boolean getCheckBoxStatusJSByXpath(String xPath, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        // xPass must point to node with type="checkbox"
        new Script(
                "function getElementByXpath(path) {return document" +
                        ".evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;} " +
                        "return(getElementByXpath(\"" + xPath + "\").checked );", "result")
                .act(driverWrapper, context, reporter);
        Boolean result = (Boolean) context.getVars().get("result");
        return result;
    }

    /**
     * @param xml   is xml saved in String.
     * @param xPath is xPath to tag eg //*[name()='ser:session']
     */
    public static String getTagValueFromXml(String xml, String xPath) throws ParserConfigurationException, IOException
            , SAXException, XPathExpressionException {
        InputSource source = new org.xml.sax.InputSource(new StringReader(xml));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(source);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        return xpath.evaluate(xPath, document);
    }

}
