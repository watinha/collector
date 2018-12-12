package edu.utfpr.xbi.collector;

import java.util.ArrayList;
import java.util.List;

import edu.utfpr.xbi.collector.BrowserCollector;
import edu.utfpr.xbi.collector.browsers.BrowserConfigurations;
import edu.utfpr.xbi.collector.browsers.Browsers;

public class Collector {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("This command requires one parameter:");
            System.out.println(" - the URL");
            return ;
        }
		String url = args[0];
        List <Browsers> list = new ArrayList <Browsers> ();
        int header = 0;
        // *** MOBILE ***
        list.add(BrowserConfigurations.motoG4("http://192.168.92.1:4723/wd/hub/", header, -1));
        //list.add(BrowserConfigurations.motoZ2("http://192.168.92.1:4723/wd/hub/", header, -1));
        list.add(BrowserConfigurations.iphone8plus("http://192.168.0.18:4723/wd/hub/", header, -1));
        //list.add(BrowserConfigurations.iphone8("http://192.168.0.16:4723/wd/hub/", header, -1));
        list.add(BrowserConfigurations.iphoneSE("http://192.168.92.1:4723/wd/hub/", header, -1));
        // *** DESKTOP ***
        //list.add(BrowserConfigurations.safari("http://192.168.92.1:4444/wd/hub/", 0, -1));
        //list.add(BrowserConfigurations.chromeMac("http://192.168.92.1:4444/wd/hub/", header, -1));
        //list.add(BrowserConfigurations.firefoxMac("http://192.168.92.1:4444/wd/hub/", header, -1));
        //list.add(BrowserConfigurations.chromeWin("http://192.168.0.17:4444/wd/hub", header, -1));
        //list.add(BrowserConfigurations.firefoxWin("http://192.168.0.17:4444/wd/hub", header, -1));
        //list.add(BrowserConfigurations.ie("http://192.168.0.17:4444/wd/hub", header, -1));
        //list.add(BrowserConfigurations.operaWin("http://192.168.0.15:4444/wd/hub", header, -1));
        // *** TABLET ***
        //list.add(BrowserConfigurations.ipadAir2("http://192.168.92.1:4723/wd/hub/", header, -1));
        //list.add(BrowserConfigurations.galaxyTabS2("http://192.168.92.1:4723/wd/hub/", header, -1));

		for	(Browsers browser : list) {
            String target = url;
            int count = 0;
            while (count < 3) {
                count++;
                try {
                    //if (list.indexOf(browser) == 1 || list.indexOf(browser) == 2) {
                    //    target = target.replace("mobile/mobile", "mobile/ios");
                    //}
                    BrowserCollector c = browser.createCollector(target);
                    c.crawl();
                    c.getDriver().close();
                    count = 3;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(
                            String.format("Error in try %d", count));
                    if (count == 3)
                        throw e;
                }
            }
		}

        list.clear();
    }

}
