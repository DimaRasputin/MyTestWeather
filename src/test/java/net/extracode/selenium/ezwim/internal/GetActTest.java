package net.extracode.selenium.ezwim.internal;

import net.extracode.selenium.ezwim.util.GetAct;
import org.apache.commons.csv.CSVFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class GetActTest {

    @Test
    public void csv() throws IOException {
        Assert.assertEquals("header1", GetAct.getStringFromCSVFile("test_data/test.csv", 0, 0));
        Assert.assertEquals("value11", GetAct.getStringFromCSVFile("test_data/test.csv", 1, 0));
        Assert.assertEquals("value22", GetAct.getStringFromCSVFile("test_data/test.csv", 2, 1));
        Assert.assertEquals("value33", GetAct.getStringFromCSVFile("test_data/test.csv", 3, 2));
    }

    @Test
    public void csv2() throws IOException {
        Assert.assertEquals("header1", GetAct.getStringFromCSVFile("test_data/test2.csv", CSVFormat.DEFAULT.withDelimiter(';'), 0, 0));
        Assert.assertEquals("value11", GetAct.getStringFromCSVFile("test_data/test2.csv", CSVFormat.DEFAULT.withDelimiter(';'), 1, 0));
        Assert.assertEquals("value22", GetAct.getStringFromCSVFile("test_data/test2.csv", CSVFormat.DEFAULT.withDelimiter(';'), 2, 1));
        Assert.assertEquals("value33", GetAct.getStringFromCSVFile("test_data/test2.csv", CSVFormat.DEFAULT.withDelimiter(';'), 3, 2));
    }

    @Test
    public void xls() throws IOException, InvalidFormatException {
        Assert.assertEquals("header1", GetAct.getStringFromExcelFile("test_data/test.xls", "sheet1", 0, 0));
        Assert.assertEquals("value11", GetAct.getStringFromExcelFile("test_data/test.xls", "sheet1", 1, 0));
        Assert.assertEquals("value22", GetAct.getStringFromExcelFile("test_data/test.xls", "sheet1", 2, 1));
        Assert.assertEquals("value33", GetAct.getStringFromExcelFile("test_data/test.xls", "sheet1", 3, 2));
    }

    @Test
    public void xlsx() throws IOException, InvalidFormatException {
        Assert.assertEquals("header1", GetAct.getStringFromExcelFile("test_data/test.xlsx", "sheet1", 0, 0));
        Assert.assertEquals("value11", GetAct.getStringFromExcelFile("test_data/test.xlsx", "sheet1", 1, 0));
        Assert.assertEquals("value22", GetAct.getStringFromExcelFile("test_data/test.xlsx", "sheet1", 2, 1));
        Assert.assertEquals("value33", GetAct.getStringFromExcelFile("test_data/test.xlsx", "sheet1", 3, 2));
    }

    @Test
    public void zip() throws IOException, InvalidFormatException {
        Assert.assertEquals(
                "This is test content",
                new String(GetAct.getFileContentFromZip("test_data/test.zip", "test.txt")).trim());
        Assert.assertEquals(
                "1",
                GetAct.getStringFromCSVContent(
                        new String(GetAct.getFileContentFromZip("test_data/ziptest.zip", "ziptest.csv")).trim(),
                        0, 0));
    }
}
