package LxxMaven.WebTest;

import org.testng.annotations.Test;

public class NewTest3 {

	@Test(groups = { "Fail", })
	public void makeError() {

		int invalid = 100 / 0;

		System.out.println(invalid);
	}

}
