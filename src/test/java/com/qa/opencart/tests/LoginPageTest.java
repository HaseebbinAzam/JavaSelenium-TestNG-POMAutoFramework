package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart Application with Shopping Workflow")
@Story("US 101: Design login page for Open Cart Application")
@Feature("F50: Feature login page")
//@Listeners({ExtentReportListener.class, TestAllureListener.class}) //- only report will work, retry is not working here
public class LoginPageTest extends BaseTest {
    
	@Description("checking login page title-----")
	@Severity(SeverityLevel.MINOR)
	@Owner("Mohammad Haseeb A")
	@Issue("PID-123")
	@Feature("login page title feature")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);

	}
	
	@Description("checking login page url-----")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Mohammad Haseeb A")
	@Feature("login page URL feature")
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);

	}
	
	@Description("checking forgt pwd link exist on the logon page-----")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Mohammad Haseeb A")
	@Feature("login page forgotpwd feature")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.checkForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Description("checking user is able to login successfully-----")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Mohammad Haseeb A")
	@Feature("login page login feature")
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		String actAccPageTitle = accPage.getAccountsPageTitle();
		Assert.assertEquals(actAccPageTitle, AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

} 
