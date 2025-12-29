package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;


public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil; 
	
	private By firstName = By.id("input-firstname");
	private By lastName =  By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[@class='radio-inline' and normalize-space(.)='Yes']/input");
	private By subscribeNo = By.xpath("//label[@class='radio-inline' and normalize-space(.)='No']/input");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By successMsg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);	
	}

	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password, 
			String subscribe) {
		
		eleUtil.doSendKeys(this.firstName, firstName, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMesg = eleUtil.waitForElementVisible(successMsg, TimeUtil.DEFAULT_MEDIUM_TIME).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}else {
			return false;
		}
	}
	
	
}
