package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExcelUtil {
	public List<HashMap<String, String>> accessDataFromSheet(String fileName, String sheetName) throws IOException {
//		String dir = System.getProperty("user.dir").replace("\\", "\\\\");
//		List<HashMap<String, String>> dataList = new ArrayList<>();
//		File file =    new File(dir+"\\src\\main\\java\\RestoreSelenium\\Resource\\"+fileName+".xlsx");
//		FileInputStream inputStream = new FileInputStream(file);
		
		String dir = System.getProperty("user.dir");
		List<HashMap<String, String>> dataList = new ArrayList<>();
		// Use Paths and resolve for cleaner path management
		Path filePath = Paths.get(dir, "src", "main", "java", "RestoreSelenium", "Resource", fileName + ".xlsx");
		FileInputStream inputStream = new FileInputStream(filePath.toFile());

		XSSFWorkbook wb=new XSSFWorkbook(inputStream);
		XSSFSheet sheet=wb.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			//this is reduce scope of the hashmap 
			HashMap<String, String> map = new HashMap<>();
			for(int j=0; j<sheet.getRow(i).getLastCellNum();j++) {
				Cell cell = sheet.getRow(i).getCell(j);
				if(cell.getCellType().name().toString().equalsIgnoreCase("numeric")){
                    String cellText = String.valueOf(new BigDecimal(sheet.getRow(i).getCell(j).getNumericCellValue()).longValue());
                    map.put(sheet.getRow(0).getCell(j).toString(), cellText);
				}else if(cell.getCellType().name().toString().equalsIgnoreCase("date")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			        String cellText = dateFormat.format(cell.getDateCellValue());
			        map.put(sheet.getRow(0).getCell(j).toString(), cellText);
				}
				else {
					map.put(sheet.getRow(0).getCell(j).toString(),sheet.getRow(i).getCell(j).toString());
				}
			}
			dataList.add(map);
		}
		wb.close();
		inputStream.close();
		System.out.println(dataList.toString());
		return dataList;
	}
	
	public void setPatientDataForSearch( String fileName, String sheetName, Object[][] dataArray ) throws IOException {
		String dir = System.getProperty("user.dir");
		Path filePath = Paths.get(dir, "src", "main", "java", "RestoreSelenium", "Resource", fileName + ".xlsx");
		FileInputStream inputStream = new FileInputStream(filePath.toFile());
		XSSFWorkbook wb=new XSSFWorkbook(inputStream);
		XSSFSheet sheet=wb.getSheet(sheetName);
		int rowNumber = dataArray.length;
		Row row = sheet.getRow(rowNumber);
		if (row == null) {
		    row = sheet.createRow(rowNumber);
		}

		for (int i = 0; i < dataArray[rowNumber - 1].length; i++) {
		    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		    cell.setCellValue(dataArray[rowNumber - 1][i].toString());
		}

		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(filePath.toFile());
		wb.write(outputStream);
		outputStream.close();
		wb.close();
    
	}
	
	public void explicitWait(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			// TODO: handle exceptions
			System.out.println(e);
		}
		
	}
	
	// Generic method to wait for an element to be visible
    public static void waitForVisibility(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Generic method to wait for an element to be clickable
    public static void waitForClickability(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
