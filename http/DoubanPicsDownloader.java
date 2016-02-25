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
import org.openqa.selenium.firefox.FirefoxDriver;

public class DoubanPicsDownloader {
	private FirefoxDriver driver;
	private Set<String> set = new HashSet<String>();
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private Set<String> existFileSet = new HashSet<String>();

	@Test
	public void run() {
		System.out.println("========run============");
		initExistFileName();

		driver = new FirefoxDriver();
		driver.get("http://www.douban.com/search?cat=1025&q=%E7%BE%8E%E5%A5%B3%E5%9B%BE%E7%89%87");
		driver.manage().window().maximize();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {

		}

		int loop = 0;
		while (loop < 2) {
			scrollPage();
			String pageSource = driver.getPageSource();
			Document doc2 = Jsoup.parse(pageSource);
			Elements elements = doc2.select("img[src$=.jpg]");
			for (Element element : elements) {
				String href = element.attr("src");
				try {
					if (href.indexOf("photo") != -1) {
						downloadPic(href);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			loop++;
			System.out.println("======swich==end===========" + loop);
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.close();
	}

	public void scrollPage() {
		for (int i = 0; i < 3; i++) {
			driver.executeScript("scroll(0,document.body.scrollHeight)");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadPic(String picUrl) throws ClientProtocolException,
			IOException {
		if (!set.contains(picUrl)) {
			set.add(picUrl);
			String shortName = picUrl.substring(picUrl.lastIndexOf("/") + 1,
					picUrl.length());
			if (!existFileSet.contains(shortName)) {
				HttpGet httpGet = new HttpGet(picUrl);
				CloseableHttpResponse response = httpClient.execute(httpGet);
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					byte b[] = new byte[1024];
					int i = 0;
					File storeFile = new File("c:/test/" + shortName);
					OutputStream outStream = new FileOutputStream(storeFile);
					while ((i = inputStream.read(b)) != -1) {
						outStream.write(b, 0, i);
					}
					inputStream.close();
					outStream.flush();
					outStream.close();
					response.close();
				}
				System.out.println("*****end***one***pic*");
			}
		}
	}

	public void initExistFileName() {
		String path = "c:/test";
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}

		File files[] = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				System.out.println(file.getName() + "is directory");
			} else {
				existFileSet.add(file.getName());
			}
		}
	}

}
