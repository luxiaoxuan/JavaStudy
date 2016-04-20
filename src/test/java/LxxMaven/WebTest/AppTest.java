package LxxMaven.WebTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class AppTest {

	public static App app = new App();

	private EventFiringWebDriver fDriver = null;

	@Before
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", ".\\res\\chromedriver.exe");

		this.fDriver = new EventFiringWebDriver(new ChromeDriver());
	}

	@Test
	public void test2() {

		assertTrue("rai".length() == 2);
	}

	@Test
	public void testOpenYahoo() {

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
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteResume() {

		String inputFilePath = ".\\data\\input-self-info_001.json";
		String jsonString = readFileText(inputFilePath);
		JSONObject jsonObj = new JSONObject(jsonString);
		
		PageFactory.initElements(this.fDriver, ResumeInputPage.class).inputInfo(jsonObj);;

		for (String w : this.fDriver.getWindowHandles()) {
			if (!w.equals(this.fDriver.getWindowHandle())) {
				this.fDriver.switchTo().window(w);
				break;
			}
		}

		String path = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		File scrFile = this.fDriver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile,
					new File(path + "\\WebTest\\java_screenshot_"
							+ new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime())
							+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		}
	}

	@After
	public void closeChrome() {
		// TODO Auto-generated method stub
		this.fDriver.quit();
	}

	private static String readFileText(String path) {
		String text = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {

			in.skip(1);// skip encode character on the beginning..?

			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}

			text = buffer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}
