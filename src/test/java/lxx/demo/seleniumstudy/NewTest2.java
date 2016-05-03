package lxx.demo.seleniumstudy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewTest2 {

	private EventFiringWebDriver fDriver = null;

	@BeforeMethod
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", ".\\res\\chromedriver.exe");

		this.fDriver = new EventFiringWebDriver(new ChromeDriver());
	}

	@Test(groups = { "Success", "Selenium", })
	public void openYahoo() {
		this.fDriver.navigate().to("http://www.yahoo.co.jp");

		WebElement btn = this.fDriver.findElement(By.id("srchbtn"));
		String height = btn.getCssValue("height");
		String width = btn.getCssValue("width");

		this.fDriver.executeScript("alert('height is " + height + ". width is " + width + ".')");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.fDriver.switchTo().alert().accept();
	}

	@AfterMethod
	public void endTest() {
		this.fDriver.quit();
	}
}
