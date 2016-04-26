package LxxMaven.WebTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class NewTest {

	private WebDriver driver = null;

	@Test(dataProvider = "dp")
	public void inputResume(String name, String sex, boolean isMarried, String region, String mail, String selfIntro) {

		ResumeInputPage pageResumeInput = PageFactory.initElements(this.driver, ResumeInputPage.class);
		pageResumeInput.open();
		pageResumeInput.inputInfo(name, sex, isMarried, region, mail, selfIntro);
		ResumeDisplayPage pageResumeDisplay = pageResumeInput.regist();

		Assert.assertNotEquals(pageResumeDisplay, null);

		Object[] resumeInfo = pageResumeDisplay.getResumeInfo();
		Assert.assertEquals(name, resumeInfo[0]);
		Assert.assertEquals(sex, resumeInfo[1]);
		Assert.assertEquals(isMarried, resumeInfo[2]);
		Assert.assertEquals(region, resumeInfo[3]);
		Assert.assertEquals(mail, resumeInfo[4]);
		Assert.assertEquals(selfIntro, resumeInfo[5]);

		String path = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		File scrFile = pageResumeDisplay.getScreenshotAsFile();
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

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", ".\\res\\chromedriver.exe");
		this.driver = new ChromeDriver();
	}

	@AfterMethod
	public void afterMethod() {
		this.driver.quit();
	}

	@DataProvider
	public Object[][] dp() {

		File dataFolder = new File(".\\data");
		File[] inputFiles = dataFolder.listFiles();
		Object[][] datas = new Object[inputFiles.length][];

		for (int i = 0; i < inputFiles.length; i++) {
			String jsonString = readFileText(inputFiles[i].getPath());
			JSONObject jsonObj = new JSONObject(jsonString);
			datas[i] = new Object[] { jsonObj.getString("name"), jsonObj.getString("sex"),
					jsonObj.getBoolean("isMarried"), jsonObj.getString("hometown"), jsonObj.getString("mailAddress"),
					jsonObj.getString("selfIntro"), };
		}

		return datas;
	}

	private static String readFileText(String path) {
		String text = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {

			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}

			text = buffer.toString().trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text.substring(text.indexOf('{'));
	}
}
