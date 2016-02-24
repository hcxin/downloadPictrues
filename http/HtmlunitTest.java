package com.chen.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;

public class HtmlunitTest {
	
	private WebClient webClient;
// http://www.douban.com/photos/album/132816876/?start=0
	@Test
	public void run () throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		System.out.println("*****run****");
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		System.out.println("*****run***11*");
		HtmlPage page = webClient.getPage("http://www.duitang.com/album/56664327");
		List<HtmlAnchor> anchorsList = page.getAnchors();
	    List<DomElement> nodeList = page.getElementsByTagName("img");
		List<String> largeList = new ArrayList<String>(); 
		for(DomElement element:nodeList){
			String href = element.getAttribute("src");
			if (href.indexOf("item")!=-1){
				largeList.add(href);
				System.out.println(href);
			}
			}
		
		 Event event = new Event();
//		for(HtmlAnchor ach:anchorsList){
//			String href = ach.getHrefAttribute();
//			System.out.println(href);
////			if (href.indexOf("large")!=-1){
////				largeList.add(href);
////				System.out.println(href);
////			}
//			}
	//	System.out.println(largeList.size());
	//	page.toString();
	}

}
