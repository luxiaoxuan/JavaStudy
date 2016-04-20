package LxxMaven.WebTest;

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
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		// System.out.println( "Hello World!" );

		openYahoo();
	}

	public static void openYahoo() {

		System.setProperty("webdriver.chrome.driver", ".\\res\\chromedriver.exe");

		ChromeDriver driver = new ChromeDriver();

		driver.navigate().to("http://www.yahoo.co.jp");

		driver.quit();
	}

	public static void writeResume() {

		String inputFilePath = ".\\data\\input-self-info_001.json";
		String jsonString = readFileText(inputFilePath);
		JSONObject jsonObj = new JSONObject(jsonString);

		EventFiringWebDriver fDriver = new EventFiringWebDriver(new ChromeDriver());

		fDriver.navigate().to("http://localhost:49459/Selenium/ResumeInput.html");

		String inputWindow = fDriver.getWindowHandle();

		fDriver.findElement(By.id("txtName")).sendKeys(jsonObj.getString("name"));
		fDriver.findElements(By.name("rdSex")).stream()
				.filter(e -> e.getAttribute("value").equals(jsonObj.getString("sex"))).findFirst().get().click();
		if (jsonObj.getBoolean("isMarried")) {
			fDriver.findElement(By.id("lblMarried")).click();
		}
		fDriver.findElement(By.id("ddlRegion")).findElements(By.xpath("//option")).stream()
				.filter(e -> e.getText().equals(jsonObj.getString("hometown"))).findFirst().get().click();
		fDriver.findElement(By.id("txtMail")).sendKeys(jsonObj.getString("mailAddress"));
		fDriver.findElement(By.id("txtSelfIntro")).sendKeys(jsonObj.getString("selfIntro"));
		fDriver.findElement(By.id("btnSubmit")).click();
		fDriver.switchTo().alert().accept();

		for (String w : fDriver.getWindowHandles()) {
			if (!w.equals(inputWindow)) {
				fDriver.switchTo().window(w);
				break;
			}
		}

		String path = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		File scrFile = fDriver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile,
					new File(path + "\\evidence_java_"
							+ new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime())
							+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		}

		fDriver.quit();
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
