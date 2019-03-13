package edu.utfpr.xbi.merger.image;

import java.io.File;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfFloat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ChiSquaredComparator implements Comparator {

    static {
        //System.out.println(System.getProperty("java.library.path"));
        //System.out.println(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static double compareStatic (File screenshot1, File screenshot2) throws Exception {
        double result = 0;
        Mat base = Highgui.imread(screenshot1.getAbsolutePath()),
            target = Highgui.imread(screenshot2.getAbsolutePath()),
            hist_base = new Mat(),
            hist_target = new Mat();
        MatOfInt histSize = new MatOfInt(256, 256, 256),
                 channels = new MatOfInt(0, 1, 2);
        MatOfFloat ranges = new MatOfFloat(
                0.0f, 256.0f, 0.0f, 256.0f, 0.0f, 256.0f);
        Imgproc.calcHist(Arrays.asList(base), channels, new Mat(),
                hist_base, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(target), channels, new Mat(),
                hist_target, histSize, ranges);
        result = Imgproc.compareHist(
                hist_base, hist_target, Imgproc.CV_COMP_CHISQR);
        base.release();
        target.release();
        hist_base.release();
        hist_target.release();
        histSize.release();
        channels.release();
        return result;
    }

    public double compare (File screenshot1, File screenshot2) throws Exception {
        if (screenshot1 == null && screenshot2 == null)
            return 0;
        if (screenshot1 == null || screenshot2 == null)
            return -1;
        return ChiSquaredComparator.compareStatic(screenshot1, screenshot2);
    }
}
