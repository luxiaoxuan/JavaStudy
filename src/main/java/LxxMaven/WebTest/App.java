package LxxMaven.WebTest;

import org.openqa.selenium.chrome.ChromeDriver;

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
}
