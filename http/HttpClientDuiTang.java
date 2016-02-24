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

public class HttpClientDuiTang {

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
		List<String> picturesUrlList = getPicturesUrlList(61269744,1);
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
		String filePath = path+picNum+".jpeg";
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
	
	public String getAlbumUrl(int albumId) throws IOException{	
		String prefix = "http://www.duitang.com/album/";
		String suffix = "61269744";
		if (Integer.toString(albumId)!=null){
			suffix = Integer.toString(albumId);
		}
		

			String albumUrl = prefix+suffix;
		return albumUrl;
	}
	
	public List<String> getPicturesUrlList(int albumId,int page) throws IOException{
		 String albumUrl = getAlbumUrl(albumId);
		if (page!=1){
			albumUrl = albumUrl+"/#!albumpics-p"+Integer.toString(page);
		}
		System.out.println("albumUrl:"+albumUrl);
		 List<String> picturesUrlList = new ArrayList<String>();
			 Document doc = Jsoup.connect(albumUrl)
					 .data("query", "Java")
					 .userAgent("Mozilla")
					 .cookie("auth", "token")
					 .timeout(30000)
					 .post();
			 Elements jpegs = doc.select("img[src$=.jpeg]");
			 for (Element element:jpegs){
				 String picUrl = element.attr("src");
				 System.out.println(picUrl);
				 if (picUrl.indexOf("item")!=-1){
					 picturesUrlList.add(picUrl);
				 }
				// System.out.println(picUrl);
			 }
		
		 return picturesUrlList;
	}
}
