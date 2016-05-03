package lxx.demo.seleniumstudy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ResumeDisplayPage extends SeleniumPageObject {

	@FindBy(how = How.ID, using = "spName")
	private WebElement spName;

	@FindBy(how = How.ID, using = "spSex")
	private WebElement spSex;

	@FindBy(how = How.ID, using = "spIsMarried")
	private WebElement spIsMarried;

	@FindBy(how = How.ID, using = "spHometown")
	private WebElement spHometown;

	@FindBy(how = How.ID, using = "spMailAddress")
	private WebElement spMailAddress;

	@FindBy(how = How.ID, using = "pSelfIntro")
	private WebElement pSelfIntro;

	public ResumeDisplayPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public Object[] getResumeInfo() {
		return new Object[] { this.spName.getText(), this.spSex.getText().equals("男") ? "male" : "female",
				this.spIsMarried.getText().equals("既婚") ? true : false, this.spHometown.getText(),
				this.spMailAddress.getText(), this.pSelfIntro.getText(), };
	}
}
