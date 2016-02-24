package com.chen.http;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class HtmlunitTest1 {

	private WebClient webClient;
	private WebWindow window;
	public static ScriptEngineManager mgr = new ScriptEngineManager();
    public static ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");

	@Test
	public void run(){
	   webClient = new WebClient(BrowserVersion.FIREFOX_38);
	   webClient.getOptions().setUseInsecureSSL(true);
       //webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);  
       webClient.getOptions().setJavaScriptEnabled(true);
       //webClient.getOptions().setActiveXNative(false);  
       webClient.getOptions().setCssEnabled(false);  
       webClient.getOptions().setThrowExceptionOnScriptError(false);
       //webClient.waitForBackgroundJavaScript(5000);
       webClient.getOptions().setAppletEnabled(true);
       //webClient.setAjaxController(new NicelyResynchronizingAjaxController());
       //webClient.addWebWindowListener(new MyWebWindowListener());
//       JavaScriptEngine engine = new JavaScriptEngine(webClient);
//       webClient.setJavaScriptEngine(engine);
       //webClient.addWebWindowListener(WebWindowListener)
	    try {
			HtmlPage page = webClient.getPage("http://www.douban.com/search?cat=1025&q=%E7%BE%8E%E5%A5%B3%E5%9B%BE%E7%89%87");
		    List<DomElement> nodeList = page.getElementsByTagName("img");
			List<String> largeList = new ArrayList<String>(); 
			for(DomElement element:nodeList){
				String href = element.getAttribute("src");
				if (href.indexOf("photo")!=-1){
					largeList.add(href);
					System.out.println(href);
				}
				}
			
			System.out.println("========size============"+largeList.size());
			//webClient.waitForBackgroundJavaScript(5000);
			System.out.println("======================1111==========================");
			//ScriptResult result = page.executeJavaScript("window.scrollBy(0,15000)");
			ScriptResult result = page.executeJavaScript("window.scrollTo(0,10000);");
			//engine.execute(page, "");
			webClient.waitForBackgroundJavaScript(10000);
			Thread.sleep(10000);
			HtmlPage newPage = (HtmlPage)result.getNewPage();
		    List<DomElement> nodeList1 = newPage.getElementsByTagName("img");
			List<String> largeList1 = new ArrayList<String>(); 
			for(DomElement element:nodeList1){
				String href = element.getAttribute("src");
				if (href.indexOf("photo")!=-1){
					largeList1.add(href);
					System.out.println(href);
				}
				}
			
			System.out.println("========size============"+largeList1.size());
		} catch (Exception e) {
			
		}
	}
	
	public class MyWebWindowListener implements WebWindowListener {

		public void webWindowOpened(WebWindowEvent event) {
			window = event.getWebWindow();
		}

		public void webWindowContentChanged(WebWindowEvent event) {
			
		}

		public void webWindowClosed(WebWindowEvent event) {
			
		}
		
	}
	
	
}
