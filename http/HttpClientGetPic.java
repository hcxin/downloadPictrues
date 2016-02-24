package com.chen.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class HttpClientGetPic {

	/**
	 * to get the picture by httpClient
	 */
	@Test
	public void run() throws ClientProtocolException, IOException {
		System.out.println("*****run****");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// HttpGet("http://blog.goyiyo.com/wp-content/uploads/2012/12/6E0E8516-E1DC-4D1D-8B38-56BDE1C6F944.jpg");
		HttpGet httpGet = new HttpGet("http://images.ali213.net/picfile/pic/2015/06/24/927_2015062413214116.jpg");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		if (response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
		HttpEntity entity = response.getEntity();
		InputStream inputStream = entity.getContent();
		byte b[] = new byte[1024];
		int i = 0;
		File storeFile = new File("D:/test/tiger3.jpg");
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

}
