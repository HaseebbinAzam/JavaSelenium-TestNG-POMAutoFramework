package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 400: Design open cart registration page")
@Story("US 401: Registration page features")
@Feature("F53: Feature registration page")
public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	@Description("Setup method to navigate to the registration page.")
	public void regSetup() {
		regPage= loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"Kavi", "selenium", "9876543223", "kavi@123","yes"},
			{"Amit", "sharma", "7876543223", "amit@123","no"},
			{"Praful", "automation", "6876543223", "praful@123","yes"}
		};
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_CSV_NAME);
	}
	
	@Description("Test Case: Verify user registration. This test checks if a user can register successfully using data from the provided data sheet.")
	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "userRegTestDataFromCSV")
	public void userRegisterationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
			Assert.assertTrue(
			  regPage.userRegister(firstName, lastName,StringUtils.getRandomEmailId(), telephone, password, subscribe),
			               AppError.USER_REG_NOT_DONE);
        }
	
	
}	
