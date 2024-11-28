package Utils;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataLibrary {
	
	public static Object[][] readExcelData(String dataSheetName, String sheetName) throws IOException {
		
		DataFormatter formatter = new DataFormatter();
		XSSFWorkbook workBook = new XSSFWorkbook("./Data/"+dataSheetName+".xlsx");
		XSSFSheet sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		int columnCount = sheet.getRow(0).getLastCellNum();
		Object[][] data = new Object[rowCount][columnCount];
		for(int i=1;i<=rowCount;i++) {
			XSSFRow row = sheet.getRow(i);
			for(int j=0;j<columnCount;j++) {
				XSSFCell cell = row.getCell(j);
				data[i-1][j] = formatter.formatCellValue(cell);
			}
		} workBook.close();
		return data;
			
	}

}
