package edu.utfpr.xbi.merger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import edu.utfpr.xbi.merger.BrowserRow;

import java.util.Arrays;
import java.util.Collection;
import java.math.BigInteger;

@RunWith(Parameterized.class)
public class BrowserRowToIdTest {
    @Parameters
    public static Collection<Object[]> data() throws Exception {
        return Arrays.asList(new Object[][]{
            { "/body[1]/div[1]", new BigInteger("101") }, // 100 + 1
            { "/body[2]/div[2]", new BigInteger("202") }, // 200 + 2
            { "/body[2]", new BigInteger("2") }, // 2
            { "/body[2]/div[13]", new BigInteger("213") }, // 200 + 13
            { "/body[2]/div[13]/span[1]", new BigInteger("21301") }, // 20000 + 1300 + 1
            { "/body[2]/div[6]/footer[1]/div[1]/div[1]/address[5]/p[1]/small[1]/a[1]", new BigInteger("20601010105010101") },
            { "/body[2]/g-raised-button[2]", new BigInteger("202") },
            { "/body[2]/g:ratingbadge[5]", new BigInteger("205") },
        });
    }

    @Parameter(0) public String xpath;
    @Parameter(1) public BigInteger id;

    @Test
    public void test_converts_xpath_to_id () throws Exception {
        String row = String.format(
            "somefolder/,http://abobrinha.com,1,a,Android,1,2,3,4,5,6,7,8,9.0,10,11,%s",
            this.xpath);
        BrowserRow r = BrowserRow.readRow(row);
        assertEquals(this.id, r.getXPathId());
    }
}
