package com.youlu;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.youlu.ui.PopWindow;
import com.youlu.ui.TableUI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Crawl {

    private StringBuilder url = new StringBuilder("");

    private ChromeDriver driver = null;

    private String url1 = "https://s.taobao.com/search?q=";

    private String url2 = "-tmall&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&sort=sale-desc";

    public static volatile int priceIndex=2;

    static volatile int sleepTime = 2200;

    public void setDriver(ChromeDriver driver) {
        this.driver = driver;
    }


    private void getData(String isbnNumber)
    {
        List<WebElement> elements1=driver.findElementsByXPath("//img[@class='J_ItemPic img']");//get title from tab : <image> 's
        List<WebElement> elements2=driver.findElementsByXPath("//div[@class='price g_price g_price-highlight']/strong");// get price
        List<WebElement> elements3=driver.findElementsByXPath("//div[@class='deal-cnt']");//get deal
        int deal = 0;
        float price=Float.MAX_VALUE;
        String title = null;
        int i;
        for( i=0;i<=priceIndex;i++)
        {
           String var1=elements3.get(i).getText();
           String dealStringVar=var1.substring(0, var1.indexOf("人"));
           dealStringVar=dealStringVar.replaceAll("\\+","");
           int dealVar=Integer.parseInt(dealStringVar);
           if(dealVar>0)
           {
               float priceVar=Float.parseFloat(elements2.get(i).getText());
               if(priceVar<price)
               {
                   deal=dealVar;
                   price=priceVar;
                   title=elements1.get(i).getAttribute("alt");
               }
           }
           else
           {
               break;
           }
        }
        System.out.println(isbnNumber+"  price:"+price+"  deal:"+deal+"  title:"+title);
        if(price>Float.MAX_VALUE/2)
        {
            FileWR.writeLine(isbnNumber, null, null, null, true);
        }
        else
        {
            FileWR.writeLine(isbnNumber, String.valueOf(price), title, String.valueOf(deal), false);
            TableUI.addBook(new Book(isbnNumber,String.valueOf(price),title,String.valueOf(deal)));
        }

    }


    public boolean function(String isbnNumber)
    {
        this.url.delete(0, this.url.length());
        this.url.append(this.url1);
        this.url.append(isbnNumber);
        this.url.append(this.url2);
        try
        {
            this.driver.get(this.url.toString());
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            PopWindow.alert("获取页面源码出现异常，可能是网络的问题！");
            return false;
        }

        try
        {
            getData(isbnNumber);
        }
        catch (IndexOutOfBoundsException e)
        {
             System.out.println("没有item！");
             String content=driver.getPageSource();

             if(content.contains("休息")||content.contains("滑块"))
             {
                 System.out.println("有滑块！");
                 return false;
             }
             System.out.println(content);
        }
        catch (Exception e)
        {
            if (driver.getPageSource().contains("item-not-found")) {
                return true;
            }
            e.printStackTrace();
            PopWindow.alert("获取标题，价格错误！可能出现滑块");
            System.out.println(driver.getPageSource());

            try
            {
                this.driver.get(this.url.toString());
            } catch (Exception e1) {
                PopWindow.alert("获取页面源码出现异常，可能是网络的问题！");
            }
            return false;
        }
        return true;
    }


    /*public boolean function(String isbnNumber)
    {
        this.url.delete(0, this.url.length());
        this.url.append(this.url1);
        this.url.append(isbnNumber);
        this.url.append(this.url2);
        try {
            this.driver.get(this.url.toString());
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.pageSource = this.driver.getPageSource();
        } catch (Exception e) {
            PopWindow.alert("获取页面源码出现异常，可能是网络的问题！");
            return false;
        }
        try {
            if (!getMinPrice(this.pageSource)) {
                FileWR.writeLine(isbnNumber, null, null, null, true);
                return true;
            }
            this.getTit = getTitle(this.pageSource);
            FileWR.writeLine(isbnNumber, this.getPrice, this.getTit, this.dealString, false);
            TableUI.addBook(new Book(isbnNumber,getPrice,getTit,dealString));
        } catch (Exception e) {
            if (this.pageSource.contains("item-not-found")) {
                return true;
            }
            PopWindow.alert("获取标题，价格错误！可能出现滑块");
            try {
                this.driver.get(this.url.toString());
            } catch (Exception e1) {
                PopWindow.alert("获取页面源码出现异常，可能是网络的问题！");
            }
            return false;
        }
        return true;
    }*/
}

