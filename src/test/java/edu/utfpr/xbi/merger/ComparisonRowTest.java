package edu.utfpr.xbi.merger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ComparisonRowTest {

    @Test
    public void testArffHeader() throws Exception {
        String header = "@RELATION browserninja.website\n" +
                        "@ATTRIBUTE URL STRING\n" +
                        "@ATTRIBUTE id NUMERIC\n" +
                        "@ATTRIBUTE tagName STRING\n" +
                        "@ATTRIBUTE childsNumber NUMERIC\n" +
                        "@ATTRIBUTE textLength NUMERIC\n" +
                        "@ATTRIBUTE basePlatform STRING\n" +
                        "@ATTRIBUTE targetPlatform STRING\n" +
                        "@ATTRIBUTE baseBrowser STRING\n" +
                        "@ATTRIBUTE targetBrowser STRING\n" +
                        "@ATTRIBUTE baseDPI NUMERIC\n" +
                        "@ATTRIBUTE targetDPI NUMERIC\n" +
                        "@ATTRIBUTE baseScreenshot STRING\n" +
                        "@ATTRIBUTE targetScreenshot STRING\n" +
                        "@ATTRIBUTE baseX NUMERIC\n" +
                        "@ATTRIBUTE targetX NUMERIC\n" +
                        "@ATTRIBUTE baseY NUMERIC\n" +
                        "@ATTRIBUTE targetY NUMERIC\n" +
                        "@ATTRIBUTE baseHeight NUMERIC\n" +
                        "@ATTRIBUTE targetHeight NUMERIC\n" +
                        "@ATTRIBUTE baseWidth NUMERIC\n" +
                        "@ATTRIBUTE targetWidth NUMERIC\n" +
                        "@ATTRIBUTE baseParentX NUMERIC\n" +
                        "@ATTRIBUTE targetParentX NUMERIC\n" +
                        "@ATTRIBUTE baseParentY NUMERIC\n" +
                        "@ATTRIBUTE targetParentY NUMERIC\n" +
                        "@ATTRIBUTE imageDiff NUMERIC\n" +
                        "@ATTRIBUTE chiSquared NUMERIC\n" +
                        "@ATTRIBUTE baseDeviceWidth NUMERIC\n" +
                        "@ATTRIBUTE targetDeviceWidth NUMERIC\n" +
                        "@ATTRIBUTE baseViewportWidth NUMERIC\n" +
                        "@ATTRIBUTE targetViewportWidth NUMERIC\n" +
                        "@ATTRIBUTE xpath STRING\n" +
                        "@ATTRIBUTE baseXpath STRING\n" +
                        "@ATTRIBUTE targetXpath STRING\n" +
                        "@ATTRIBUTE phash NUMERIC\n" +
                        "@ATTRIBUTE previousSiblingLeft NUMERIC\n" +
                        "@ATTRIBUTE previousSiblingTop NUMERIC\n" +
                        "@ATTRIBUTE nextSiblingLeft NUMERIC\n" +
                        "@ATTRIBUTE nextSiblingTop NUMERIC\n" +
                        "@ATTRIBUTE textNodes NUMERIC\n" +
                        "@ATTRIBUTE fontFamily STRING\n" +
                        "@DATA";
        assertEquals(header, ComparisonRow.header());
    }

    @Test
    public void test_getScreenshot_with_null () {
        BrowserRow base = mock(BrowserRow.class),
                   target = mock(BrowserRow.class);

        ComparisonRow row = new ComparisonRow(base, target);

        doReturn(null).when(base).getScreenshot();
        doReturn(null).when(target).getScreenshot();

        assertNull(row.getBaseScreenshot());
        assertNull(row.getTargetScreenshot());
    }
}
