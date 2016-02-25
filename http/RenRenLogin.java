package com.chen.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RenRenLogin {
	private FirefoxDriver driver;
	private Set<String> downloadSet = new HashSet<String>();
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private Set<String> existFileSet = new HashSet<String>();
	int loop = 0;
	private String path = "d:/test/";
	@Test
	public void run() {
		driver = new FirefoxDriver();
		driver.get("http://www.renren.com/");
		driver.manage().window().maximize();
		WebElement email = driver.findElementById("email");
		email.sendKeys("haichen086@163.com");
		WebElement password = driver.findElementById("password");
		password.sendKeys("haichen086");
		WebElement login = driver.findElementById("login");
		login.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {

		}
		System.out.println("========driver.getPageSource()============"+driver.getPageSource());
		//driver.close();
	}

}
