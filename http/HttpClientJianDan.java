package com.chen.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class HttpClientJianDan {

	/**
	 * to get the picture by httpClient
	 */
	@Test
	public void run() throws ClientProtocolException, IOException {
		System.out.println("*****run****");
		int picNum = 1;
		String path = "d:/test/";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpPost httpPost = new HttpPost("http://jandan.net/ooxx");
		List<String> picturesUrlList = getPicturesUrlList(1838, 1841);
		System.out.println("*****picturesUrlList****"+picturesUrlList.size());
		for (String picUrl:picturesUrlList){
		HttpGet httpGet = new HttpGet(picUrl);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		System.out.println("*****status**"+response.getStatusLine().getStatusCode());

		if (response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			System.out.println("...start writer...");
		HttpEntity entity = response.getEntity();
		entity.getContentType();
		InputStream inputStream = entity.getContent();
		byte b[] = new byte[1024];
		int i = 0;
		String filePath = path+picNum+".jpg";
		File storeFile = new File(filePath);
		OutputStream outStream = new FileOutputStream(storeFile);
		while ((i = inputStream.read(b)) != -1) {
			outStream.write(b, 0, i);
		}
		inputStream.close();
		outStream.flush();
		outStream.close();
		response.close();
		picNum++;
		System.out.println("...end writer...");
			}
		}
		httpClient.close();
		System.out.println("*****end****");

	}
	
	public List<String> getvisitUrlList(int pageFrom,int pageTo) throws IOException{
		int realPageFrom = 1838;
		int realPageTo = 1839;
		if (pageFrom < pageTo){
			realPageFrom = pageFrom;
			realPageTo = pageTo;
		} else {
			realPageFrom = pageTo;
			realPageTo = pageFrom;	
		}
		
		String prefix = "http://jandan.net/ooxx/page-";
		String suffix = "#comments";
		List<String> urlList = new ArrayList<String>();
		for (int i = realPageFrom ;i<realPageTo;i++){
			String url = prefix+Integer.toString(i)+suffix;
			urlList.add(url);
		}
		return urlList;
	}
	
	public List<String> getPicturesUrlList(int pageFrom, int pageTo) throws IOException{
		 List<String> visitUrlList = getvisitUrlList(pageFrom,pageTo);
		 List<String> picturesUrlList = new ArrayList<String>();
		 for (String url: visitUrlList){
			 Document doc = Jsoup.connect(url)
					 .data("query", "Java")
					 .userAgent("Mozilla")
					 .cookie("auth", "token")
					 .timeout(3000)
					 .post();
			 Elements jpgs = doc.select("a.view_img_link");
			 for (Element element:jpgs){
				 String picUrl = element.attr("href");
				 picturesUrlList.add(picUrl);
				 System.out.println(picUrl);
			 }
		 }
		 return picturesUrlList;
	}
}
