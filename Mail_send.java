package blueStar_script;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import generic_libs.Retry;

public class Mail_send {
	
		public WebDriver driver;
		public WebDriverWait wait;
		String appURL = "https://login.yahoo.com/config/login?.src=ym&.intl=us&.lang=en-US&.done=https%3A%2F%2Fmail.yahoo.com";
		
		//Locators
		private By byEmail = By.id("login-username");
		private By byPassword = By.id("login-passwd");
		private By bySubmit = By.id("login-signin");
		
		private By bycompose =By.xpath("/html/body/div[9]/div[3]/div[3]/div[1]/ul/li/button");
		/*private By byTo=By.id("to-field");	
		*(.//*[@id='Compose']/button)
		*private By bysub=By.id("subject-field");
		*/
		
		//private By byError = By.id("global-alert-queue");
		
		@BeforeClass
		public void testSetup() {
			
			//this code is get login into yahoo
			
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			
			//wait = new WebDriverWait(driver, 5);
			
			driver.navigate().to(appURL);
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
			driver.findElement(byEmail).sendKeys("dev_gehlod@yahoo.com");
			
			driver.findElement(byPassword).sendKeys("9977277204pj");
			
			
			//wait for element to be visible and perform click
		
			//wait.until(ExpectedConditions.visibilityOfElementLocated(bySubmit));
			driver.findElement(bySubmit).click();
			
			
		}
		

		@Test(dataProvider="empLogin",retryAnalyzer=Retry.class)
		public void VerifyInvalidLogin(String subject, String text) throws IOException, InterruptedException {
			
			driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
			
			//Click on comp0se button
			
			WebElement composebtn = (new WebDriverWait(driver, 10))
					  .until(ExpectedConditions.presenceOfElementLocated(bycompose));
			
			composebtn.click();
			
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			
			WebElement fieldtxt = (new WebDriverWait(driver, 100))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id("to-field")));
			
			fieldtxt.sendKeys("jay39349@gmail.com");
		
			//absolute xpath (html/body/div[9]/div[3]/div[4]/div[3]/div[7]/div/table/tbody/tr[1]/td/div/div[6]/div/label/span/input)
			driver.findElement(By.id("subject-field")).sendKeys(subject);
			
			 driver.findElement(By.xpath(" html/body/div[9]/div[3]/div[4]/div[3]/div[7]/div/table/tbody/tr[2]/td/div/div[1]/div[2]")).sendKeys(text);
			 //(.//*[@id='rtetext'])
			
			 driver.findElement(By.id("attachment-button-input")).click();
			 
			 //this is auto it code for attachment 
			 
			 Runtime.getRuntime().exec("C:\\Users\\tvt\\Desktop\\AutoIT\\Fileupload.exe");
			 
			 driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
			
			 //driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
			 
			 
			 driver.findElement(By.xpath("//span[@class='btn default']")).click();
			 
			 
		}
			
			
		
		@DataProvider(name="empLogin")
		public Object[][] loginData() {
			Object[][] arrayObject = getExcelData("C:\\Users\\tvt\\workspace\\blueStar\\src\\xecel\\bluestar1.xls","mail1");
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
		
		
		
		

		@Test(enabled=false)
		public void tearDown() {
			driver.quit();
		}
	}



