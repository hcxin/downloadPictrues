package com.chen.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class JsonDuiTang {

	@Test
	public void run () {
		List<String> picturesUrlList = null;
		try {
			picturesUrlList = getPicUrlListByalbum(64169990);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("..picturesUrlList.."+picturesUrlList.size());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String path = "e:/test/";
		for (String picUrl:picturesUrlList){
		String bigUrl = formatUrlToBigUrl(picUrl);
		HttpGet httpGet = new HttpGet(bigUrl);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("*****status**"+response.getStatusLine().getStatusCode());

		if (response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			System.out.println("...start writer...");
		HttpEntity entity = response.getEntity();
		entity.getContentType();
		InputStream inputStream = null;
		OutputStream outStream =null;
		try {
			inputStream = entity.getContent();
		byte b[] = new byte[1024];
		int i = 0;
		String picName = getPicName(bigUrl);
		String filePath = path+picName;
		File storeFile = new File(filePath);
		outStream = new FileOutputStream(storeFile);
		while ((i = inputStream.read(b)) != -1) {
			outStream.write(b, 0, i);
		}
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
			inputStream.close();
			outStream.flush();
			outStream.close();
			response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("...end writer...");
			}
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("*****end****");
	}
	
	public List<String> getPicUrlListByalbum (int album) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> picUrlList = new ArrayList<String>();
		String albumStr = Integer.toString(album);
		System.out.println("*****run****");
		int pageNum = 20;
		while (true){
		String prefix = "http://www.duitang.com/album/"+albumStr+"/masn/p/";
		String suffix = "/24/";
		String requestUrl = prefix+Integer.toString(pageNum)+suffix;
		System.out.println("*****requestUrl***"+requestUrl);
		
		HttpGet httpGet = new HttpGet(requestUrl);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		//httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		System.out.println("*****status**"+response.getStatusLine().getStatusCode());

		if (response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			System.out.println("...start ok...");
			HttpEntity entity = response.getEntity();
			//System.out.println("..entity.toString().."+EntityUtils.toString(entity));
			String jsonString = EntityUtils.toString(entity);
			JSONObject jsonObj = new JSONObject(jsonString);
			System.out.println("..jsonObj.."+jsonObj);
			JSONObject jsonData = jsonObj.getJSONObject("data");
			System.out.println("..jsonData.."+jsonData.toString());
			JSONArray jsonArray = jsonData.getJSONArray("blogs");
			System.out.println("..jsonArray.length."+jsonArray.length());
			System.out.println("..jsonArray.."+jsonArray.toString());
			if (jsonArray.length()<10){
				break;
			}
			for (int i=0;i<jsonArray.length();i++){
				JSONObject data= (JSONObject)jsonArray.get(i);
				String href = data.get("isrc").toString();
				picUrlList.add(href);
				System.out.println("..href.."+href);
				
			}
			
			
			
//		InputStream inputStream = entity.getContent();
//		byte b[] = new byte[1024];
//		int i = 0;
//		String filePath = path+picNum+".jpeg";
//		File storeFile = new File(filePath);
//		OutputStream outStream = new FileOutputStream(storeFile);
//		while ((i = inputStream.read(b)) != -1) {
//			outStream.write(b, 0, i);
//		}
//		inputStream.close();
//		outStream.flush();
//		outStream.close();
		response.close();
//		picNum++;
//		System.out.println("...end writer...");
			}
		pageNum++;
		}
		httpClient.close();
		return picUrlList;
	}
	
	public String getPicName(String picUrl){
		int lastIndex = picUrl.lastIndexOf("/");
		String picName = picUrl.substring(lastIndex+1, picUrl.length());
		return picName;
	}
	
	public String formatUrlToBigUrl(String picUrl){
		String bigUrl = picUrl.replaceAll("224_0.","700_0.");
		return bigUrl;
	}
	
}
