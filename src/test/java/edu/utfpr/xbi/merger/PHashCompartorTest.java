package edu.utfpr.xbi.merger;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import edu.utfpr.xbi.merger.image.PHashCompartor;

@RunWith(JUnit4.class)
public class PHashCompartorTest {

    @Test
    public void test_compare_two_similar_images () throws Exception {
        File image1 = new File("./images/image-1.png"),
             image2 = new File("./images/image-1.png");
        PHashCompartor comparator = new PHashCompartor();
        double result = comparator.compare(image1, image2);
        Assert.assertEquals(1.0f, result, 0.2);
    }

    @Test
    public void test_compare_two_different_images () throws Exception {
        File image1 = new File("./images/image-1.png"),
             image2 = new File("./images/image-2.png");
        PHashCompartor comparator = new PHashCompartor();
        double result = comparator.compare(image1, image2);
        Assert.assertNotEquals(1.0f, result, 0.2);
    }

}
