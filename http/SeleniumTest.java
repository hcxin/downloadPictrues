package com.chen.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
	   private FirefoxDriver driver;
	@Test
	public void run(){
		System.out.println("========run============");
		driver = new FirefoxDriver();
		driver.get("http://www.douban.com/search?cat=1025&q=%E7%BE%8E%E5%A5%B3%E5%9B%BE%E7%89%87");
		String pageSource = driver.getPageSource();
		Document doc = Jsoup.parse(pageSource);
		 Elements elements = doc.select("img[src$=.jpg]");
		for (Element element: elements){
			String href = element.attr("src");
			System.out.println("========href============"+href);
		}
		driver.manage().window().maximize();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			
		}
		for (int i=0;i<6;i++){
		driver.executeScript("scroll(0,document.body.scrollHeight)");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String pageSource2 = driver.getPageSource();
		Document doc2 = Jsoup.parse(pageSource2);
		 Elements elements2 = doc2.select("img[src$=.jpg]");
		for (Element element: elements2){
			String href = element.attr("src");
			System.out.println("========href============"+href);
		}
		}
	}
}
