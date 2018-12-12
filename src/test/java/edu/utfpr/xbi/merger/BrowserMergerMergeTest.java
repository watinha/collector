package edu.utfpr.xbi.merger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentMatcher;

import edu.utfpr.xbi.merger.BrowserRow;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class BrowserMergerMergeTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {1}, {3}, {0}
        });
    }

    @Parameter public int count;

    public class BrowserRowMatcher implements ArgumentMatcher<BrowserRow> {
        private int id;
        public BrowserRowMatcher (int id) {
            this.id = id;
        }
        public boolean matches(BrowserRow row) {
            return this.id == row.getId();
        }
        public String toString() {
            return String.format("[BrowserRow %d]", this.id);
        }
    }

    @Test
    public void testMerge () throws Exception {
        BufferedReader br1 = mock(BufferedReader.class),
                       br2 = mock(BufferedReader.class);
        List <String> returnList1 = new ArrayList <String> (),
                      returnList2 = new ArrayList <String> ();
        BrowserMerger merger = spy(new BrowserMerger(br1, br2));
        for (int i = 0; i < this.count; i++) {
            returnList1.add(String.format("s/,http://a.com,%d,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2],12,13,14,15,16,Times", i));
            returnList2.add(String.format("s/,http://a.com,%d,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2],12,13,14,15,16,Times", i));
            doNothing().when(merger)._merge(
                    argThat(new BrowserRowMatcher(i)),
                    argThat(new BrowserRowMatcher(i)));
        }
        for (int i = this.count; i < 7; i++) {
            returnList1.add(null);
            returnList2.add(null);
        }
        when(br1.readLine()).thenReturn(
                returnList1.get(0), returnList1.get(1), returnList1.get(2),
                returnList1.get(3), returnList1.get(4), returnList1.get(5));
        when(br2.readLine()).thenReturn(
                returnList2.get(0), returnList2.get(1), returnList2.get(2),
                returnList2.get(3), returnList2.get(4), returnList2.get(5));
        doNothing().when(merger)._header();

        merger.merge();

        verify(merger)._header();
        verify(br1, times(this.count + 1)).readLine();
        verify(br2, times(this.count + 1)).readLine();
        for (int i = 0; i < this.count; i++) {
            verify(merger)._merge(
                    argThat(new BrowserRowMatcher(i)),
                    argThat(new BrowserRowMatcher(i)));
        }
    }
}
