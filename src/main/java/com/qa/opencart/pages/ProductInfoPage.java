package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
	
	WebDriver driver;
	ElementUtil eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImagesCount = By.cssSelector("div#content a.thumbnail");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public String getproductHeader() {
		String header = eleUtil.doGetText(productHeader);
		System.out.println("product header ===" +header);
		return header;
	}
	
	public int getProductImagesCount() {
		int imagesCount = 
				eleUtil.waitForVisibilityOfElementsLocated(productImagesCount, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		System.out.println("total images ==" +imagesCount);
		return imagesCount;
	}
	
	
	public Map<String, String> getProductInfoMap() {
		//productMap = new HashMap<String, String>(); //No order
		//productMap= new TreeMap<String, String>();  //Alphabetical order
		productMap = new LinkedHashMap<String, String>(); //Insertion Order
		productMap.put("productname", getproductHeader());
		productMap.put("productimagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForVisibilityOfElementsLocated(productMetaData, TimeUtil.DEFAULT_TIME);
		for(WebElement e:metaList) {
			String metaString = e.getText();
			String meta[]= metaString.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
			
	}
	
	
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String productPrice = priceList.get(0).getText();
		String exTaxprice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", productPrice);
		productMap.put("exTaxPrice", exTaxprice);
	}

}
