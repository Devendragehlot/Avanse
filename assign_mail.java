package blueStar_script;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class assign_mail 
{
	  
	  public WebDriver driver;
		public WebDriverWait wait;
		String appURL = "http://192.168.1.182/bluestar/login.php";
		
		//Locators
		private By byEmail = By.id("username");
		private By byPassword = By.id("password");
		private By bySubmit = By.id("login_button");
	
		
		@BeforeClass
		public void testSetup() {
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 5);
			driver.navigate().to(appURL);
			driver.findElement(byEmail).sendKeys("atalker1");
			driver.findElement(byPassword).sendKeys("1234");
			//wait for element to be visible and perform click
			wait.until(ExpectedConditions.visibilityOfElementLocated(bySubmit));
			driver.findElement(bySubmit).click();
			
			wait = new WebDriverWait(driver, 10);
			
			driver.findElement(By.xpath("//input[@id='move_to_crm']")).click();
			
		}
		

		@Test(dataProvider="empLogin")
		public void assign(String id,String value,String value1 ,String value2) 
		{

			
			wait = new WebDriverWait(driver, 10);
			
			
			driver.findElement(By.xpath(id)).click();
			
			driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/div[2]/div[5]/div[1]/ul/li/div")).click();
			
			
			driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/div[2]/div[5]/div[1]/ul/li/ul/li[1]")).click();
			
			Select roledropdown = new Select(driver.findElement(By.id("role_user_id")));
			
			roledropdown.selectByValue(value);
			
			
            Select assigndropdown = new Select(driver.findElement(By.id("assign_user_id")));
			
			assigndropdown.selectByValue(value1);
			
			
           Select worktypedropdown = new Select(driver.findElement(By.id("worktype_id")));
           
			worktypedropdown.selectByValue(value2);
			
			
			driver.findElement(By.id("Assign")).click();
			
			
			Alert alert=driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		 
			wait = new WebDriverWait(driver, 20);
			
		}
			
		
		
		
		@DataProvider(name="empLogin")
		public Object[][] loginData() {
			Object[][] arrayObject = getExcelData("C:\\Users\\tvt\\Documents\\bluestar3 assign.xls","assignmail");
			return arrayObject;
		}

		/**
		 * @param File Name
		 * @param Sheet Name
		 * @return
		 */
		public String[][] getExcelData(String fileName, String sheetName) {
			String[][] arrayExcelData = null;
			try {
				FileInputStream fs = new FileInputStream(fileName);
				Workbook wb = Workbook.getWorkbook(fs);
				Sheet sh = wb.getSheet(sheetName);

				int totalNoOfCols = sh.getColumns();
				int totalNoOfRows = sh.getRows();
				
				arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
				
				for (int i= 1 ; i < totalNoOfRows; i++) {

					for (int j=0; j < totalNoOfCols; j++) {
						arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
					}

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				e.printStackTrace();
			} catch (BiffException e) {
				e.printStackTrace();
			}
			return arrayExcelData;
		}

		@Test
		public void tearDown() {
			driver.quit();

  }
}
