package edu.utfpr.xbi.merger;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import edu.utfpr.xbi.merger.image.ChiSquaredComparator;

@RunWith(JUnit4.class)
public class ChiSquaredComparatorTest {

    @Test
    public void test_compare_two_similar_images () throws Exception {
        File image1 = new File("./images/image-1.png"),
             image2 = new File("./images/image-1.png");
        ChiSquaredComparator comparator = new ChiSquaredComparator();
        double result = comparator.compare(image1, image2);
        Assert.assertEquals(0.0f, result, 0.001);
    }

    @Test
    public void test_compare_two_different_images () throws Exception {
        File image1 = new File("./images/image-1.png"),
             image2 = new File("./images/image-2.png");
        ChiSquaredComparator comparator = new ChiSquaredComparator();
        double result = comparator.compare(image1, image2);
        Assert.assertNotEquals(0.0f, result, 0.0);
    }

}

