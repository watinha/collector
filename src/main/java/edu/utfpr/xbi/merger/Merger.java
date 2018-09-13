package edu.utfpr.xbi.merger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import edu.utfpr.xbi.merger.image.Comparator;
import edu.utfpr.xbi.merger.image.ImageDiffComparator;
import edu.utfpr.xbi.merger.BrowserMerger;
import edu.utfpr.xbi.merger.image.ChiSquaredComparator;
import edu.utfpr.xbi.merger.image.PHashCompartor;

import org.opencv.core.Core;

public class Merger {
    public static void main (String args[]) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Comparator c = new ImageDiffComparator();
        Comparator c2 = new ChiSquaredComparator();
        Comparator c3 = new PHashCompartor();

        String [] comparisons = {
//            "ipadAir2-galaxyTabS2"
//            "chrome_win-firefox_win",
//            "chrome_win-ie",
            "motog4-iphonese",
            "motog4-iphone8plus",
//            "motog4-motoz2",
//            "iphonese-iphone8",
//            "chrome_win-opera_win",
//            "chrome_win-chrome_mac",
//            "chrome_win-firefox_mac",
//            "chrome_win-safari",
//            "chrome_mac-safari"
        };

        for (int i = 0; i < comparisons.length; i++) {
            String [] browsers = comparisons[i].split("-");
            String browser1 = browsers[0],
                   browser2 = browsers[1];
            BufferedReader br1 = new BufferedReader(new FileReader(
                               String.format("results/%s/results.csv", browser1))),
                           br2 = new BufferedReader(new FileReader(
                               String.format("results/%s/results.csv", browser2)));
            PrintWriter writer = new PrintWriter(new File(
                        String.format("results/%s-%s.arff", browser1, browser2)));
            BrowserMerger merger = new BrowserMerger(br1, br2);
            merger.setComparator1(c);
            merger.setComparator2(c2);
            merger.setComparator3(c3);
            merger.setPrintWriter(writer);
            merger.merge();
            br1.close();
            br2.close();
            writer.close();
        }
    }
}
