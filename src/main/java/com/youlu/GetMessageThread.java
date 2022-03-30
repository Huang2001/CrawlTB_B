package com.youlu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.youlu.ui.MainUI;
import com.youlu.ui.PopWindow;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetMessageThread extends Thread {
    private static String tbloginURL = "https://login.taobao.com/member/login.jhtml";

    public static volatile LinkedList<String> isbnList = null;


    static private volatile boolean stopFlag = false;

    static private volatile boolean startFlag = false;

    public static void setFlag(boolean f) {
        startFlag = f;
    }

    public static boolean reSetStopFlag()
    {
        stopFlag=!stopFlag;
        return stopFlag;
    }

    static ChromeDriver getWebDriver() {
        ChromeDriver driver = null;
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[] { "--disable-infobars" });
        options.addArguments(new String[] { "--disable-web-security" });
        options.addArguments(new String[] { "--start-maximized" });
        options.addArguments(new String[] { "--no-sandbox" });
        options.addArguments(new String[] { "--disable-blink-features=AutomationControlled" });
        options.addArguments(new String[] { "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36" });
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(options);
        driver.get(tbloginURL);
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void run() {
        ChromeDriver driver = null;
        driver = getWebDriver();
        MainUI.driverList.add(driver);
        while (!startFlag) {
            try {
                TimeUnit.SECONDS.sleep(4L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Crawl crawl = new Crawl();
        crawl.setDriver(driver);
        String isbnNumber = "1323";
        boolean isSuccess = true;
        while (true) {
            if (stopFlag) {
                while (stopFlag) {
                    try {
                        TimeUnit.SECONDS.sleep(5L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isSuccess) {
                synchronized (isbnList) {
                    if (isbnList.size() == 0) {
                        PopWindow.alert("所有商品已爬取完毕！可以导出文件了");
                        return;
                    }
                    isbnNumber = isbnList.pollFirst();
                }
            }

            isSuccess = crawl.function(isbnNumber);

            if (!isSuccess) {
                int failNumber = 1;
                try {
                    TimeUnit.SECONDS.sleep(8L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (!stopFlag && !isSuccess) {
                    failNumber++;
                    if (failNumber > 6) {
                        /*stopFlag = true;
                        PopWindow.alert("由于长时间未处理！程序暂停，请按‘开启’按钮开始");*/
                        PopWindow.alert("由于长时间未处理！程序暂停60分钟");
                        try {
                            TimeUnit.MINUTES.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    try
                    {
                        TimeUnit.SECONDS.sleep(8L);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    isSuccess = crawl.function(isbnNumber);
                }
            }
        }
    }
}

