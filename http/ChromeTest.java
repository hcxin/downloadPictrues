package com.chen.http;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

 public class ChromeTest extends TestCase {

   private static ChromeDriverService service;
   private WebDriver driver;

   public static void createAndStartService() {
     service = new ChromeDriverService.Builder()
         .usingDriverExecutable(new File("path/to/my/chromedriver.exe"))
         .usingAnyFreePort()
         .build();
     try {
		service.start();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

   public static void createAndStopService() {
     service.stop();
   }

   public void createDriver() {
     driver = new RemoteWebDriver(service.getUrl(),
         DesiredCapabilities.chrome());
   }

   public void quitDriver() {
     driver.quit();
   }

   public void testGoogleSearch() {
     driver.get("http://www.google.com");
     WebElement searchBox = driver.findElement(By.name("q"));
//     searchBox.sendKeys("webdriver");
//     searchBox.quit();
//     assertEquals("webdriver - Google Search", driver.getTitle());
   }
 }