package LxxMaven.WebTest;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public abstract class SeleniumPageObject {

	protected EventFiringWebDriver driver;

	public SeleniumPageObject(WebDriver driver) {
		if (driver.equals(null)) {
			this.driver = new EventFiringWebDriver(new ChromeDriver());
		} else {
			this.driver = new EventFiringWebDriver(driver);
		}
	}

	public File getScreenshotAsFile() {
		return this.driver.getScreenshotAs(OutputType.FILE);
	}
}
