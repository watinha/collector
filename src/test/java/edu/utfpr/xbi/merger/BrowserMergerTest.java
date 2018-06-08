package edu.utfpr.xbi.merger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentMatcher;

import edu.utfpr.xbi.merger.BrowserRow;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BrowserMergerTest {

    @Parameters
    public static Collection<Object[]> data() throws Exception {
        return Arrays.asList(new Object[][]{
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                null,
                1, 1, 2, -1, 3, 3
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                null,
                null,
                1, -1, 2, -1, 3, 3
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                null,
                null,
                null,
                1, -1, 2, -1, 3, -1
            },
            {
                null,
                null,
                null,
                null,
                null,
                null,
                -1, -1, -1, -1, -1, -1
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                null,
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                1, 1, -1, 2, 3, 3
            },
            {
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                null,
                null,
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                -1, 1, -1, 2, 3, 3
            },
            {
                null,
                null,
                null,
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                -1, 1, -1, 2, -1, 3
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                1, 1, 2, 2, 3, 3
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,4,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                1, 2, 2, 3, 3, 4
            },
            {
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,4,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[3]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]",
                2, 1, 3, 2, 4, 3
            },
            {
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                null,
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[1]",
                null,
                -1, 1, 1, 2, 2, -1
            },
            {
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[1]",
                null,
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                null,
                1, -1, 2, 1, -1, 2
            },
            {
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[1]",
                null,
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[2]",
                null,
                1, -1, 2, 1, -1, 2
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[13]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[1]",
                null,
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[1]/div[1]",
                null,
                -1, 1, 1, -1, 2, 2
            },
            {
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]/div[2]/div[13]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]/div[2]/br[12]",
                "s/,http://a.com,3,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                "s/,http://a.com,1,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]/div[2]/div[13]",
                "s/,http://a.com,2,s,platform,1,1,1,1,1,1,1,1,1,1,1,/body[2]",
                null,
                1, 1, 2, -1, 3, 2
            },
        });
    }

    @Parameter(0) public String br1_1;
    @Parameter(1) public String br1_2;
    @Parameter(2) public String br1_3;
    @Parameter(3) public String br2_1;
    @Parameter(4) public String br2_2;
    @Parameter(5) public String br2_3;
    @Parameter(6) public int expect1_1;
    @Parameter(7) public int expect1_2;
    @Parameter(8) public int expect2_1;
    @Parameter(9) public int expect2_2;
    @Parameter(10) public int expect3_1;
    @Parameter(11) public int expect3_2;


    public class BrowserRowMatcher implements ArgumentMatcher<BrowserRow> {
        private int id;
        public BrowserRowMatcher (int id) {
            this.id = id;
        }
        public boolean matches(BrowserRow row) {
            if (this.id == -1)
                return row == null;
            return row != null && this.id == row.getId();
        }
        public String toString() {
            return String.format("[BrowserRow %d]", this.id);
        }
    }

    @Test
    public void testMergeWithLessElementsInSecondParameter() throws Exception {
        BufferedReader br1 = mock(BufferedReader.class),
                       br2 = mock(BufferedReader.class);
        BrowserMerger merger = spy(new BrowserMerger(br1, br2));

        when(br1.readLine()).thenReturn(this.br1_1)
                            .thenReturn(this.br1_2)
                            .thenReturn(this.br1_3)
                            .thenReturn(null);
        when(br2.readLine()).thenReturn(this.br2_1)
                            .thenReturn(this.br2_2)
                            .thenReturn(this.br2_3)
                            .thenReturn(null);
        doNothing().when(merger)._header();
        doNothing().when(merger)._merge(argThat(new BrowserRowMatcher(this.expect1_1)),
                                        argThat(new BrowserRowMatcher(this.expect1_2)));
        doNothing().when(merger)._merge(argThat(new BrowserRowMatcher(this.expect2_1)),
                                        argThat(new BrowserRowMatcher(this.expect2_2)));
        doNothing().when(merger)._merge(argThat(new BrowserRowMatcher(this.expect3_1)),
                                        argThat(new BrowserRowMatcher(this.expect3_2)));

        merger.merge();


        if (this.expect1_1 - this.expect1_2 != 0) {
            verify(merger)._merge(argThat(new BrowserRowMatcher(this.expect1_1)),
                                  argThat(new BrowserRowMatcher(this.expect1_2)));
        }
        if (this.expect2_1 - this.expect2_2 != 0) {
            verify(merger)._merge(argThat(new BrowserRowMatcher(this.expect2_1)),
                                  argThat(new BrowserRowMatcher(this.expect2_2)));
        }
        if (this.expect3_1 - this.expect3_2 != 0) {
            verify(merger)._merge(argThat(new BrowserRowMatcher(this.expect3_1)),
                                  argThat(new BrowserRowMatcher(this.expect3_2)));
        }
    }

}
