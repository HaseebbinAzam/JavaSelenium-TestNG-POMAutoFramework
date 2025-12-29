package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil; 
	/*
	 * we shouldn't create the object here at the class level, as otherwise they will be an 
	 * object lying unnecessary at this class object in heap memory. Login page is nonstatic.
	 * Classes are only for reference variables. Objects should be created at method or constructed.
	 */
	
	//1. Page Objects -- By Locators
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
    //2. Constructor of the page
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver); //this is written inside the constructor 
		//as this constructor is being called everytime. If the eleUtil object had been created 
		//in one method then its scope is limited to that method only. 
		//And we had to keep creating a new object in everyt method to use eleUtil.
	}
	
	//3. Page Actions
	@Step("getting login page title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("login page title is ..."+title);
		return title;
	}
	
	@Step("getting login page url...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("login page url is ..."+url);
		return url;
	}
	
	@Step("getting the state of forgotpwd link exists...")
	public boolean checkForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("login to application with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("user creds : " + username + ":" + pwd);
		eleUtil.doSendKeys(email, username, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("navigating to register page...")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, TimeUtil.DEFAULT_MEDIUM_TIME);
		return new RegisterPage(driver);
	}
}
