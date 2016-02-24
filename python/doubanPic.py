import time
import urllib2
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
#driver = webdriver.PhantomJS()
driver = webdriver.Firefox()
driver.get('http://www.douban.com/search?cat=1025&q=%E7%BE%8E%E5%A5%B3%E5%9B%BE%E7%89%87')
#print driver.page_source
staticInfo = driver.page_source
print "==============static page start================="
soup = BeautifulSoup(staticInfo, "html.parser")
picLinks = soup.find_all("img")
for link in picLinks:
    href = link["src"]
    print href
print "==============static page end==================="
driver.execute_script("window.scrollBy(0,document.body.scrollHeight)","")
time.sleep(10)
print "==============dynamic page start================"
asyncInfo = driver.page_source
soup = BeautifulSoup(asyncInfo, "html.parser")
picLinks = soup.find_all("img")
for link in picLinks:
    href = link["src"]
    print href
print "==============dynamic page end=================="
#print driver.page_source
driver.quit()
