package LxxMaven.WebTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class NewTest {
	
	private EventFiringWebDriver fDriver = null;
	
	@Test(dataProvider = "dp")
	public void inputResume(String name, String sex, boolean isMarried, String region, String mail, String selfIntro) {
		PageFactory.initElements(this.fDriver, ResumeInputPage.class).inputInfo(name, sex, isMarried, region, mail, selfIntro);

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

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", ".\\res\\chromedriver.exe");

		this.fDriver = new EventFiringWebDriver(new ChromeDriver());
	}

	@AfterMethod
	public void afterMethod() {
		this.fDriver.quit();
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] {
				{ "馬雲", "male", true, "広州", "jackma@ali.com",
						"小さい頃から英語を学ぼうと、朝早くから自転車で近くのホテルに行って外国人と話していた。9年間このような生活が続いた後、互いに文通をしあう外国人の友達と出会い、彼女からJackと呼ばれるようになる" },
				{ "メグ・ホイットマン", "female", true, "東京", "ceo@hp.com",
						"1998年3月、従業員数30人程度の規模だったeBayの社長兼最高経営責任者に就任。退任する2008年3月までに世界的なインターネットオークション会社に成長させた。2011年9月22日、米国ヒューレット・パッカード前任CEOの退任に伴い、CEOに就任した。" },
				{ "しょうで", "male", false, "上海", "mangliuxiaodan@lyl.net", "ユデりゃんクェパンニョ" }, };
	}
}
