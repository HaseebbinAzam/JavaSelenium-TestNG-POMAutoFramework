package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;
/**
 * @author Mohammad Haseeb A.
 */
public class ElementUtil  {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void nullCheck(String value) {
		if(value==null) {
			throw new ElementException("VALUE IS NULL"+ value);
		}
	}
	
	private void highlightElement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)){
			jsUtil.flash(element);
		}
	}
	
	
	public  void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	@Step("Entering value{1} in text field using locator: {0} and waiting for element with:{2} secs")
	public  void doSendKeys(By locator, String value, int timeOut) {
		nullCheck(value);
		waitForElementVisible(locator, timeOut).clear();
		waitForElementVisible(locator, timeOut).sendKeys(value);
	} 
    
	@Step("Entering value{1} in text field using locator: {0}")
	public  void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	@Step("finding the element using locator: {0} ")
	public WebElement getElement(By locator) {
		try {
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;
		}
		catch(NoSuchElementException e) {
			System.out.println("Element is not present on the page.." + locator);
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	
	@Step("clcik on element using locator: {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
		
	}
	
	
	public  String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	@Step("checking the state of locator: {0} is displayed or not...")
	public boolean doIsDisplayed(By locator) {
		try {
		boolean flag = getElement(locator).isDisplayed();
		System.out.println("Element with locator= " + locator + " is dispalyed");
		return flag;
		}
		catch(NoSuchElementException e) {
			System.out.println("Element with locator: " + locator + " is NOT displayed" );
			return false;
		}
	}
	
	
	public boolean isElementDisplayed(By locator) {
		int elementCount = getElementsCount(locator);
		if(elementCount==1) {
			System.out.println("Single element with locator= " + locator + " is dispalyed");
			return true;
		}
		else {
			System.out.println("Zero or Multiple elements with locator= " + locator + " is dispalyed");
			return false;
		}
	}
	
	
	public boolean isElementDisplayed(By locator, int expectedCount) {
		int elementCount = getElementsCount(locator);
		if(elementCount==expectedCount) {
			System.out.println("element with locator= " + locator + " is dispalyed with occurrence of: " + elementCount);
			return true;
		}
		else {
			System.out.println("Zero or Multiple elements with locator= " + locator + " is dispalyed with occurrence of: " + elementCount);
			return false;
		}
	}

	
	public List<String> getElementsTextsList(By locator) {
		int textCounts = 0;
		int blankTextCounts =0;
		List<WebElement> elementsList = getElements(locator);
		List<String> elementsTextList = new ArrayList<String>();
		for(WebElement e : elementsList) {
			String text = e.getText();
			if(text.length()!=0) {
				elementsTextList.add(text);
				textCounts += 1;
			}
			else {
				blankTextCounts += 1;
			}
			
		}
		System.out.println("No of elements with text = " +textCounts );
		System.out.println("No of elements without text = " +blankTextCounts );
		
		return elementsTextList;
	}
	
	
	public List<String> getElementAttributeList(By locator, String attrName) {
		List<WebElement> elementList = getElements(locator);
		List<String> attrValList = new ArrayList<String>();
		
		for(WebElement e : elementList) {
			String attrVal = e.getAttribute(attrName);
			if(attrVal!=null && attrVal.length()!=0) {
				attrValList.add(attrVal);
				System.out.println(attrVal);
			}
		}
		return attrValList;
	}
	
	
	
	
	 //*********************Select drop down utils***********************
	
	
	public  void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);;
	}
	
	
	public  void doSelectByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);;
	}

	public  void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);;
	}
	
	
	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
		
		
	}
	
	
	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}
	
	public void selectValueFromDropDown(By locator, String OptionText) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		
		for(WebElement e : optionsList ) {
			String text = e.getText();
			if(text.equals(OptionText.trim())) {
				e.click();
				break;
			}
		}
	}
	
	
	public void selectValueFromDropDownwithoutSelectClass(String OptionText, By locator ) {
		List<WebElement> optionsList = getElements(locator);	
		for(WebElement e : optionsList) {
			String text = e.getText();
			if(text.equals(OptionText)) {
				e.click();
				break;
			}
			
		}
	}
	
	
	
	public void doSearch(By searchField, String searchKey, By suggestions, String value) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(5000);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		for(WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if(text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	
	//***************************Actions utils***************************
	
	public void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}
	
	
	public void doDragAndDrop(By sourcelocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourcelocator), getElement(targetLocator)).perform();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	/**
	 * This method is used to enter value in the text field with a pause.
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public  void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}
	
	/**
	 * This method is used to enter value in the text field with a pause of 500 ms (by default).
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}
	
	
	public void level4MenuSubMenuHandlingUsingClick(By level1, String level2, String level3, String level4)
			throws InterruptedException {
		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(By.linkText(level2))).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(By.linkText(level3))).perform();
		Thread.sleep(1000);
		doClick(By.linkText(level4));
	}
	
	

	public void level4MenuSubMenuHandlingUsingClick(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	public void level4MenuSubMenuHandlingUsingMouseHover(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		Actions act = new Actions(driver);

		act.moveToElement(getElement(level1)).perform();
		Thread.sleep(1000);

		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}
	
	
	
	//********************Wait Utils***************************//
	
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				 .pollingEvery(Duration.ofSeconds(intervalTime))
				 .ignoring(NoSuchElementException.class)
				  .withMessage("===element is not found===");
		
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElementsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));	
		
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForVisibilityOfElementsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));	
		}catch (Exception e) {
			return List.of();//return empty arraylist
		}
	}
			
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	
	public String waitForURLContains(String urlFraction, int timeOut ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try{
			if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		}
		}catch (TimeoutException e){
			System.out.println("url not found");	
		}
		return driver.getCurrentUrl();
	}
	
	public String waitForURLToBe(String urlToBe, int timeOut ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try{
			if(wait.until(ExpectedConditions.urlToBe(urlToBe))) {
			return driver.getCurrentUrl();
		}
		}catch (TimeoutException e){
			System.out.println("url not found");	
		}
		return driver.getCurrentUrl();
	}
	
	public String waitForTitleContains(String titleFraction, int timeOut ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try{
			if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		}catch (TimeoutException e){
			System.out.println("title not found");	
		}
		return driver.getTitle();
	}
	
	@Step("waiting for the title and capturing it...")
	public String waitForTitleToBe(String titleVal, int timeOut ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try{
			if(wait.until(ExpectedConditions.titleIs(titleVal))) {
			return driver.getTitle();
		}
		}catch (TimeoutException e){
			System.out.println("title not found");	
		}
		return driver.getTitle();
	}
	
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());	
	}
	
	public Alert waitForJSAlert(int timeOut, int intervalTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				 .pollingEvery(Duration.ofSeconds(intervalTime))
				 .ignoring(NoAlertPresentException.class)
				  .withMessage("===Alert is not found===");
		return wait.until(ExpectedConditions.alertIsPresent());	
	}
	
	public String getAlertText(int timeOut) {
		Alert alert = waitForJSAlert(timeOut);
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}
	
	public void acceptJSAlert(int timeOut) {
		 waitForJSAlert(timeOut).accept();
	}
	
	public void dismissJSAlert(int timeOut) {
		 waitForJSAlert(timeOut).dismiss();
	}
	
	public void alertSendKeys(int timeOut, String value) {
		Alert alert = waitForJSAlert(timeOut);
		alert.sendKeys(value);
		alert.accept();
	}
	
	
	//wait for frames/iframes :
	
	
	/**
	 * An expectation for checking whether the given frame is available to switch to.
	 If the frame is available it switches the given driver to the specified frame.
	 * @param frameLocator
	 * @param timeOut
	 */
	public void waitForFrame(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));	
	}
	
	
	public void waitForFrame(By frameLocator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				 .pollingEvery(Duration.ofSeconds(intervalTime))
				 .ignoring(NoSuchFrameException.class)
				  .withMessage("===Frame is not found===");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));	
	}
	
	public void waitForFrameByIndex(By frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));	
	}
	
	public void waitForFrameByIDOrName(By frameIDOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));	
	}
	
	public void waitForFrameByWebElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));	
	}
	
	
	public boolean waitForWindowsToBe(int totalWindows, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
		
	}
	
	
	public void isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
		
		if(Boolean.parseBoolean(flag)) {
			System.out.println("page is completely loaded");
		}
		else {
			throw new RuntimeException("page is not loaded");
		}
		
	}
	
}
