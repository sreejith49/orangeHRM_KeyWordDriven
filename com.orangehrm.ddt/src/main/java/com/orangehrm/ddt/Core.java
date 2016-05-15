package com.orangehrm.ddt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import jxl.Sheet;
import jxl.Workbook;

public class Core {
	
	static WebDriver driver;	
	
	public static void textBoxUtlitites(String IdentifierType, String ObjectIdentifier, String action, String testData){
		
		WebElement Element = null;
		
		try{

			Element = AssignElement(IdentifierType, ObjectIdentifier);
			
			if(action.equalsIgnoreCase("Enter")){
				Element.clear();
				Element.sendKeys(testData);
			}else if(action.equalsIgnoreCase("verify")){
				boolean elementStatus = false;
				Thread.sleep(3000);
				System.out.println("OOOOOOOOOOOOO"+Element.isEnabled());
				if(elementStatus == Element.isEnabled()){
					
					System.out.println("Assertion passed");
				}else {
					System.out.println("Assertion failed");
				}
				
				System.out.println(elementStatus);
			}
			
		}
		catch(Exception e){
			
		}
	}
	
	public static void clickableElementUtlitites(String IdentifierType, String ObjectIdentifier, String action){
		
		WebElement Element = null;
		
		try {
			
			Element = AssignElement(IdentifierType, ObjectIdentifier);
			
			if(action.equalsIgnoreCase("click")){
				
				Element.click();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void spanUtilities(String IdentifierType, String ObjectIdentifier, String action, String testData){
		WebElement Element = null;
		
		try {
			
			Element = AssignElement(IdentifierType, ObjectIdentifier);
			
			if(action.equalsIgnoreCase("verify")){
				
				if(Element.getText().equalsIgnoreCase(testData)){
					System.out.println("Asserted successfully");
				}
				else{
					System.out.println("Assertion failed");
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	
	public static void tableUtilities(String IdentifierType, String ObjectIdentifier, String action, String testData){
		WebElement Element = null;
		
		try {
			
			Element = AssignElement(IdentifierType, ObjectIdentifier);
			
			if(action.equals("select")){
				
				List<WebElement> tableRows = Element.findElements(By.tagName("tr"));
				String[] listEmployeeDetails = null;
				for(WebElement tableRow: tableRows){
					
					if(tableRow.getText().contains("Jay")){
						
						listEmployeeDetails = tableRow.getText().split(" ");
						String idn ="ohrmList_chkSelectRecord_"+listEmployeeDetails[0].replace("0", "");
						driver.findElement(By.id(idn)).click();;
					}
										
					
				}
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebElement AssignElement(String IdentifierType, String ObjectIdentifier){
		WebElement Element = null;
		
		try {
			if(IdentifierType.equalsIgnoreCase("id")){
				
				Element = driver.findElement(By.id(ObjectIdentifier));
				
			}
			else if(IdentifierType.equalsIgnoreCase("class")){
				
				Element = driver.findElement(By.className(ObjectIdentifier));
				
			}
			else if(IdentifierType.equalsIgnoreCase("css")){
				
				Element = driver.findElement(By.cssSelector(ObjectIdentifier));
				
			}
			else if(IdentifierType.equalsIgnoreCase("linkText")){
				
				Element = driver.findElement(By.linkText(ObjectIdentifier));
				
			}
			else if(IdentifierType.equalsIgnoreCase("xpath")){
				
				Element = driver.findElement(By.xpath(ObjectIdentifier));
				
			}			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return Element;
			
	}
	
	public static void main(String[] args) {
		
		try{
			
			System.setProperty("webdriver.chrome.driver", "./config/drivers/chromedriver.exe");
			driver = new ChromeDriver();

			driver.get("http://192.168.242.190/orangehrm-3.2.1/symfony/web/index.php/auth/login");			
			Workbook workbook = Workbook.getWorkbook(new File("orangeHrm_ddt.xls"));
		    Sheet testScenariosSheet = workbook.getSheet("testscenarios");
		    
		    int testScenariosSheetRows = testScenariosSheet.getRows();
		    
		    Sheet testCasesSheet = workbook.getSheet("testcases");
		    
		    int testCasesSheetRows = testCasesSheet.getRows();
		    int testCasesSheetColumns = testCasesSheet.getColumns();
	    
	    
		    for(int testScenariosSheetRow=1; testScenariosSheetRow < testScenariosSheetRows; testScenariosSheetRow++){
		    			    	
		    	if(testScenariosSheet.getCell(2, testScenariosSheetRow).getContents().equalsIgnoreCase("yes")){
		    		System.out.println(testScenariosSheet.getCell(1, testScenariosSheetRow).getContents());
		    		
				    //***********************************
				    for(int testCasesSheetRow=1; testCasesSheetRow < testCasesSheetRows; testCasesSheetRow++){
		    			
		    			for(int testCasesSheetColumn=1; testCasesSheetColumn < testCasesSheetColumns; testCasesSheetColumn++){
		    				    				
		    				if(testCasesSheet.getCell(1,testCasesSheetRow).getContents().equalsIgnoreCase(testScenariosSheet.getCell(0, testScenariosSheetRow).getContents())){
		    					
		    					if(testCasesSheet.getCell(8, testCasesSheetRow).getContents().equalsIgnoreCase("yes")){
			    					
			    					if(testCasesSheet.getCell(testCasesSheetColumn, testCasesSheetRow).getContents().equalsIgnoreCase("clickableElement")){
			    						System.out.println("clickableElement");
			    						clickableElementUtlitites(testCasesSheet.getCell(3, testCasesSheetRow).getContents(), testCasesSheet.getCell(2, testCasesSheetRow).getContents(), testCasesSheet.getCell(7, testCasesSheetRow).getContents());
			    					}
			    					else if(testCasesSheet.getCell(testCasesSheetColumn, testCasesSheetRow).getContents().equalsIgnoreCase("textbox")){
			    						System.out.println("textbox");
			    						textBoxUtlitites(testCasesSheet.getCell(3, testCasesSheetRow).getContents(), testCasesSheet.getCell(2, testCasesSheetRow).getContents(), testCasesSheet.getCell(7, testCasesSheetRow).getContents(), testCasesSheet.getCell(5, testCasesSheetRow).getContents());
			    					}
			    					else if(testCasesSheet.getCell(testCasesSheetColumn, testCasesSheetRow).getContents().equalsIgnoreCase("menu")){
			    						System.out.println("menu");
			    					}
			    					else if(testCasesSheet.getCell(testCasesSheetColumn, testCasesSheetRow).getContents().equalsIgnoreCase("span")){
			    						System.out.println("span");
			    						spanUtilities(testCasesSheet.getCell(3, testCasesSheetRow).getContents(), testCasesSheet.getCell(2, testCasesSheetRow).getContents(), testCasesSheet.getCell(7, testCasesSheetRow).getContents(), testCasesSheet.getCell(5, testCasesSheetRow).getContents());
			    					}
			    					else if(testCasesSheet.getCell(testCasesSheetColumn, testCasesSheetRow).getContents().equalsIgnoreCase("table")){
			    						System.out.println("table");
			    						tableUtilities(testCasesSheet.getCell(3, testCasesSheetRow).getContents(), testCasesSheet.getCell(2, testCasesSheetRow).getContents(), testCasesSheet.getCell(7, testCasesSheetRow).getContents(), testCasesSheet.getCell(5, testCasesSheetRow).getContents());
			    					}
			    					
			    				}
		    					
		    				}
		    				
		    			}
		    			
		    		}		    
				    //***********************************
		    		
		    	}
		    	
		    }
			
		   // driver.close();
		   // driver.quit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		

	}

}
