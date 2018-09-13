package edu.utfpr.xbi.merger;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentMatcher;

import edu.utfpr.xbi.merger.BrowserRow;
import edu.utfpr.xbi.merger.image.Comparator;

import org.junit.Test;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BrowserMergerSingleRowTest {

    @Parameters
    public static Collection<Object[]> data() throws Exception {
        return Arrays.asList(new Object[][]{
            {
                spy(BrowserRow.readRow(
                        "somefolder/android/,http://abobrinha.com,1,a,Android 2.0 - Chrome,220,1013,19,90,8,993,1080,360,3.0,0,12,/body")),
                spy(BrowserRow.readRow(
                        "somefolder/iphonese/,http://abobrinha.com,1,a,iOS - iPhone SE,220,1013,19,90,8,993,1080,360,3.0,0,12,/div")),
                "http://abobrinha.com,1,a,0,12," +
                     "\"Android 2.0 - Chrome\",\"iOS - iPhone SE\",null,null," +
                     "3.0,3.0,somefolder/android/1.png,somefolder/iphonese/1.png," +
                     "220,220,1013,1013,19,19,90,90,8,8,993,993,0.0,0.0,1080,1080,360,360,/div,/body,/div,0.5",
                0f, 0f, "somefolder/android/1.png", "somefolder/iphonese/1.png", 0.5
            },
            {
                spy(BrowserRow.readRow(
                        "result/motog4/,http://pepino.com,12,SVG,Android - MotoG4 - Chrome,226,1020,27,98,17,1003,1080,360,3.0,3,19,/a")),
                spy(BrowserRow.readRow(
                        "result/iphonese/,http://pepino.com,12,SVG,iOS - iPhone SE - Safari,220,1013,19,90,8,993,720,360,2.0,3,19,/a")),
                "http://pepino.com,12,SVG,3,19," +
                     "\"Android - MotoG4 - Chrome\",\"iOS - iPhone SE - Safari\",null,null," +
                     "3.0,2.0,result/motog4/12.png,result/iphonese/12.png," +
                     "226,220,1020,1013,27,19,98,90,17,8,1003,993,0.0,0.0,1080,720,360,360,/a,/a,/a,0.0",
                0f, 0f, "result/motog4/12.png", "result/iphonese/12.png", 0.0
            },
            {
                spy(BrowserRow.readRow(
                        "result/motog4/,http://pepino.com,12,SVG,Android - MotoG4 - Chrome,226,1020,27,98,17,1003,1080,360,3.0,3,19,/a")),
                spy(BrowserRow.readRow(
                        "result/iphonese/,http://pepino.com,12,SVG,iOS - iPhone SE - Safari,220,1013,19,90,8,993,720,360,2.0,3,19,/b")),
                "http://pepino.com,12,SVG,3,19," +
                     "\"Android - MotoG4 - Chrome\",\"iOS - iPhone SE - Safari\",null,null," +
                     "3.0,2.0,result/motog4/12.png,result/iphonese/12.png," +
                     "226,220,1020,1013,27,19,98,90,17,8,1003,993,12345.0,333.0,1080,720,360,360,/b,/a,/b,0.3",
                12345f, 333f, "result/motog4/12.png", "result/iphonese/12.png", 0.3
            },
            {
                spy(BrowserRow.readRow(
                        "somefolder/android/,http://abobrinha.com,1,a,Android 2.0 - Chrome,220,1013,19,90,8,993,1080,360,3.0,0,12,/a")),
                spy(BrowserRow.readRow(
                        "somefolder/iphonese/,http://abobrinha.com,1,a,iOS - iPhone SE,220,1013,19,90,8,993,1080,360,3.0,0,12,/a")),
                "http://abobrinha.com,1,a,0,12," +
                     "\"Android 2.0 - Chrome\",\"iOS - iPhone SE\",null,null," +
                     "3.0,3.0,somefolder/android/1.png,somefolder/iphonese/1.png," +
                     "220,220,1013,1013,19,19,90,90,8,8,993,993,2345.0,123.0,1080,1080,360,360,/a,/a,/a,0.4",
                2345f, 123f, "somefolder/android/1.png", "somefolder/iphonese/1.png", 0.4
            },
            {
                spy(BrowserRow.readRow(
                        "result/motog4/,http://pepino.com,12,SVG,Android - MotoG4 - Chrome,214,1006,11,82,0,983,1080,360,3.0,3,19,/a")),
                spy(BrowserRow.readRow(
                        "result/iphonese/,http://pepino.com,12,SVG,iOS - iPhone SE - Safari,220,1013,19,90,9,993,720,360,2.0,3,19,/a")),
                "http://pepino.com,12,SVG,3,19," +
                     "\"Android - MotoG4 - Chrome\",\"iOS - iPhone SE - Safari\",null,null," +
                     "3.0,2.0,result/motog4/12.png,result/iphonese/12.png," +
                     "214,220,1006,1013,11,19,82,90,0,9,983,993,12345.0,333.0,1080,720,360,360,/a,/a,/a,0.7",
                12345f, 333f, "result/motog4/12.png", "result/iphonese/12.png", 0.7
            },
            {
                spy(BrowserRow.readRow(
                        "result/motog4/,http://pepino.com,12,SVG,Android - MotoG4 - Chrome,214,1006,11,82,0,983,1080,360,3.0,3,19,/a")),
                null,
                "http://pepino.com,12,SVG,3,19," +
                     "\"Android - MotoG4 - Chrome\",\"null\",null,null," +
                     "3.0,-1.0,result/motog4/12.png,null," +
                     "214,-1,1006,-1,11,-1,82,-1,0,-1,983,-1,-1.0,-1.0,1080,-1,360,-1,/a,/a,null,-1.0",
                12345f, 333f, "result/motog4/12.png", "result/iphonese/12.png", -1.0
            },
            {
                null,
                spy(BrowserRow.readRow(
                        "result/motog4/,http://pepino.com,12,SVG,Android - MotoG4 - Chrome,214,1006,11,82,0,983,1080,360,3.0,3,19,/a")),
                "http://pepino.com,12,SVG,3,19," +
                     "\"null\",\"Android - MotoG4 - Chrome\",null,null," +
                     "-1.0,3.0,null,result/motog4/12.png," +
                     "-1,214,-1,1006,-1,11,-1,82,-1,0,-1,983,-1.0,-1.0,-1,1080,-1,360,/a,null,/a,-1.0",
                12345f, 333f, "result/iphonese/12.png", "result/motog4/12.png", -1.0
            }
        });
    }

    @Parameter public BrowserRow browserRow1;
    @Parameter(1) public BrowserRow browserRow2;
    @Parameter(2) public String comparisonRow;
    @Parameter(3) public double imageDiff;
    @Parameter(4) public double chiSquared;
    @Parameter(5) public String filename1;
    @Parameter(6) public String filename2;
    @Parameter(7) public double phash;

    public class FileMatcher implements ArgumentMatcher<File> {
        private String filename;
        public FileMatcher (String name) {
            this.filename = name;
        }
        public boolean matches(File f) {
            return this.filename.equals(f.getPath());
        }
        public String toString() {
            return "[FILE]";
        }
    }

    @Test
    public void testShouldBrowserRows() throws Exception {
        BufferedReader br1 = mock(BufferedReader.class),
                       br2 = mock(BufferedReader.class);
        BrowserMerger merger = spy(new BrowserMerger(br1, br2));
        PrintWriter pw = mock(PrintWriter.class);
        Comparator c1 = mock(Comparator.class),
                   c2 = mock(Comparator.class),
                   c3 = mock(Comparator.class);
        File file1 = mock(File.class),
             file2 = mock(File.class);

        if (this.browserRow1 != null)
            doReturn(file1).when(this.browserRow1).getScreenshot();
        if (this.browserRow2 != null)
            doReturn(file2).when(this.browserRow2).getScreenshot();
        doReturn(this.filename1).when(file1).getPath();
        doReturn(this.filename2).when(file2).getPath();
        doNothing().when(pw).println(anyString());
        doReturn(this.imageDiff).when(c1).compare(
                argThat(new FileMatcher(this.filename1)), argThat(new FileMatcher(this.filename2)));
        doReturn(this.chiSquared).when(c2).compare(
                argThat(new FileMatcher(this.filename1)), argThat(new FileMatcher(this.filename2)));
        doReturn(this.phash).when(c3).compare(
                argThat(new FileMatcher(this.filename1)), argThat(new FileMatcher(this.filename2)));

        merger.setComparator1(c1);
        merger.setComparator2(c2);
        merger.setComparator3(c3);
        merger.setPrintWriter(pw);
        merger._merge(this.browserRow1, this.browserRow2);
        verify(pw).println(this.comparisonRow);
    }
}
