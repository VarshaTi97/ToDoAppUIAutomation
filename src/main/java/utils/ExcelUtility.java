package utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {

    public static String[][] getData(String filePath, String sheetName) {
        String[][] data = null;
        XSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(fis);
        } catch(IOException e){
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(sheetName);
        int lastCell = sheet.getRow(0).getLastCellNum();
        int lastRow = sheet.getLastRowNum();


        data = new String[lastRow][lastCell];
        for(int i=0; i<lastRow; i++){
            for(int j=0;j<lastCell; j++){
                data[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue();
            }
        }
        return data;
    }
}
