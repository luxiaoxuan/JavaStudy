package lxx.demo.seleniumstudy;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ResumeInputPage extends SeleniumPageObject {

	@FindBy(how = How.ID, using = "txtName")
	private WebElement txtName;

	@FindBys(value = { @FindBy(how = How.NAME, using = "rdSex") })
	private List<WebElement> rdSex;

	@FindBy(how = How.ID, using = "lblMarried")
	private WebElement lblMarried;

	@FindBy(how = How.ID, using = "ddlRegion")
	private WebElement ddlRegion;

	@FindBy(how = How.ID, using = "txtMail")
	private WebElement txtMail;

	@FindBy(how = How.ID, using = "txtSelfIntro")
	private WebElement txtSelfIntro;

	@FindBy(how = How.ID, using = "btnSubmit")
	private WebElement btnSubmit;

	public ResumeInputPage(WebDriver driver) {
		super(driver);
		// this.driver.navigate().to("http://localhost:49459/Selenium/ResumeInput.html");
	}

	public void open() {
		super.driver.navigate().to("http://localhost:49459/Selenium/ResumeInput.html");
	}

	public void inputInfo(JSONObject jsonObj) {

		String name = jsonObj.getString("name");
		String sex = jsonObj.getString("sex");
		boolean isMarried = jsonObj.getBoolean("isMarried");
		String region = jsonObj.getString("hometown");
		String mail = jsonObj.getString("mailAddress");
		String selfIntro = jsonObj.getString("selfIntro");

		this.inputInfo(name, sex, isMarried, region, mail, selfIntro);
	}

	public void inputInfo(String name, String sex, boolean isMarried, String region, String mail, String selfIntro) {
		this.txtName.sendKeys(name);
		this.rdSex.stream().filter(e -> e.getAttribute("value").equals(sex)).findFirst().get().click();
		if (isMarried) {
			this.lblMarried.click();
		}
		this.ddlRegion.findElements(By.xpath("//option")).stream().filter(e -> e.getText().equals(region)).findFirst()
				.get().click();
		this.txtMail.sendKeys(mail);
		this.txtSelfIntro.sendKeys(selfIntro);
	}

	public ResumeDisplayPage regist() {
		this.btnSubmit.click();
		this.driver.switchTo().alert().accept();
		for (String w : this.driver.getWindowHandles()) {
			if (!w.equals(this.driver.getWindowHandle())) {
				this.driver.switchTo().window(w);
				return PageFactory.initElements(this.driver, ResumeDisplayPage.class);
			}
		}
		return null;
	}
}
