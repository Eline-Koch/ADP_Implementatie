package org.adp_implementatie;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    public static void main(String[] args) {
        Object[][] test1Results = {
                {"Test Name a asdasd  asdasdasssssss", "Result", "Execution Time"},
                {"Test1", "Passed", 120},
                {"Test2", "Failed", 95},
                {"Test3", "Passed", 110},
        };

        Object[][] test2Results = {
                {"Test Name", "Result", "Execution Time"},
                {"TestA", "Passed", 85},
                {"TestB", "Passed", 130},
                {"TestC", "Failed", 75},
        };

        Object[][][] allTests = {test1Results, test2Results};

        // Aanroepen van de helper methode
        ExcelWriter.writeTestResultsToExcel(allTests, "test_results_multi_tables.xlsx");
    }

    public static Object[][] generateTestResult(String testName, Object pushObject, PerformanceBenchmark benchmark) {
        Object[][] result = new Object[1][3];

        result[0][0] = testName;                       // Naam van de test
        result[0][1] = pushObject.toString();          // Object dat we pushen
        result[0][2] = benchmark.getElapsedTime();     // De tijd die het kostte om de operatie uit te voeren

        return result;
    }


    public static void writeTestResultsToExcel(Object[][][] allTests, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Results");

        int currentRow = 0;

        for (Object[][] testData : allTests) {
            // Voeg een kop toe om de test te onderscheiden
            Row testHeaderRow = sheet.createRow(currentRow++);
            Cell headerCell = testHeaderRow.createCell(0);
            headerCell.setCellValue("Test Group " + (currentRow / (testData.length + 1)));

            // Voeg een simpele stijl toe aan de header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerCell.setCellStyle(headerStyle);

            // Voeg de testdata toe
            for (Object[] rowData : testData) {
                Row row = sheet.createRow(currentRow++);
                for (int colNum = 0; colNum < rowData.length; colNum++) {
                    Cell cell = row.createCell(colNum);
                    if (rowData[colNum] instanceof String) {
                        cell.setCellValue((String) rowData[colNum]);
                    } else if (rowData[colNum] instanceof Number) {
                        cell.setCellValue(((Number) rowData[colNum]).doubleValue());
                    } else {
                        cell.setCellValue(rowData[colNum] != null ? rowData[colNum].toString() : "");
                    }
                }
            }

            // Laat een lege rij tussen tabellen
            currentRow++;
        }

        // Pas kolombreedtes automatisch aan
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Schrijf het werkboek naar een bestand
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("Excel bestand geschreven naar: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
