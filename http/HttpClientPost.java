package com.chen.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class HttpClientPost {

	/**
	 * to get the picture by httpClient
	 */
	@Test
	public void run() throws ClientProtocolException, IOException {
		System.out.println("*****run****");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpPost httpPost = new HttpPost("http://jandan.net/ooxx");
		HttpPost httpPost = new HttpPost("http://jandan.net/ooxx/page-1838#comments");
		CloseableHttpResponse response = httpClient.execute(httpPost);
	
		System.out.println("*****run****"+response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_MOVED_TEMPORARILY){  
//			Locale locale = response.getLocale();
			
		Header[] headers = response.getHeaders("Location");
		if (headers!=null && headers.length>0){
			String urlStr=headers[0].toString();
			System.out.println("*****urlStr****"+urlStr);
			int index = urlStr.indexOf("http");
			String reditectUrl = urlStr.substring(index);
			System.out.println("*****redirectUrl****"+reditectUrl);
			String realReditectUrl = URLDecoder.decode(reditectUrl, "utf-8");
			System.out.println("*****realReditectUrl****"+realReditectUrl);
			response = doPost(response);
		}
		System.out.println("*****run****"+response.getStatusLine().getStatusCode());
		}
		if (response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
		HttpEntity entity = response.getEntity();
		entity.getContentType();
		System.out.println("*****run****"+entity.toString());
		InputStream inputStream = entity.getContent();
		byte b[] = new byte[1024];
		int i = 0;
		File storeFile = new File("D:/test/test1.txt");
		OutputStream outStream = new FileOutputStream(storeFile);
		while ((i = inputStream.read(b)) != -1) {
			outStream.write(b, 0, i);
		}
		inputStream.close();
		outStream.flush();
		outStream.close();
		httpClient.close();
		response.close();
		}
		System.out.println("*****end****");

	}

	  public CloseableHttpResponse doPost (CloseableHttpResponse response) throws ClientProtocolException, IOException{
		  
		  String realReditectUrl = null;
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_MOVED_TEMPORARILY){  
			Header[] headers = response.getHeaders("Location");
			if (headers!=null && headers.length>0){
				String urlStr=headers[0].toString();
				System.out.println("*****urlStr****"+urlStr);
				int index = urlStr.indexOf("http");
				String reditectUrl = urlStr.substring(index);
				System.out.println("*****redirectUrl****"+reditectUrl);
				realReditectUrl = URLDecoder.decode(reditectUrl, "utf-8");
				System.out.println("*****realReditectUrl****"+realReditectUrl);
				response = doPost(response);
			}
			System.out.println("*****run****"+response.getStatusLine().getStatusCode());
			}
		  
			if (realReditectUrl!=null){
				
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(realReditectUrl);
			 response = httpClient.execute(httpPost);
			httpClient.close();
			response.close();
			}
			return response;
	  }
}
