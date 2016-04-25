package LxxMaven.WebTest;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;

public class ResumeInputPage {

	private WebDriver driver;

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
		this.driver = driver;
		this.driver.navigate().to("http://localhost:49459/Selenium/ResumeInput.html");
	}

	public void inputInfo(JSONObject jsonObj) {

		String name = jsonObj.getString("name");
		String sex = jsonObj.getString("sex");
		boolean isMarried = jsonObj.getBoolean("isMarried");
		String region = jsonObj.getString("hometown");
		String mail = jsonObj.getString("mailAddress");
		String selfIntro = jsonObj.getString("selfIntro");
		
		this.inputInfo(name, sex, isMarried, region, mail, selfIntro);

//		this.txtName.sendKeys(jsonObj.getString("name"));
//		this.rdSex.stream().filter(e -> e.getAttribute("value").equals(jsonObj.getString("sex"))).findFirst().get()
//				.click();
//		if (jsonObj.getBoolean("isMarried")) {
//			this.lblMarried.click();
//		}
//		this.ddlRegion.findElements(By.xpath("//option")).stream()
//				.filter(e -> e.getText().equals(jsonObj.getString("hometown"))).findFirst().get().click();
//		this.txtMail.sendKeys(jsonObj.getString("mailAddress"));
//		this.txtSelfIntro.sendKeys(jsonObj.getString("selfIntro"));
//		this.btnSubmit.click();
//		this.driver.switchTo().alert().accept();
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
		this.btnSubmit.click();
		this.driver.switchTo().alert().accept();
	}
}
