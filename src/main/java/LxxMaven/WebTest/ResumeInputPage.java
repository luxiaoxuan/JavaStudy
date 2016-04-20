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
		this.txtName.sendKeys(jsonObj.getString("name"));
		this.rdSex.stream().filter(e -> e.getAttribute("value").equals(jsonObj.getString("sex"))).findFirst().get()
				.click();
		if (jsonObj.getBoolean("isMarried")) {
			this.lblMarried.click();
		}
		this.ddlRegion.findElements(By.xpath("//option")).stream()
				.filter(e -> e.getText().equals(jsonObj.getString("hometown"))).findFirst().get().click();
		this.txtMail.sendKeys(jsonObj.getString("mailAddress"));
		this.txtSelfIntro.sendKeys(jsonObj.getString("selfIntro"));
		this.btnSubmit.click();
		this.driver.switchTo().alert().accept();
	}
}
