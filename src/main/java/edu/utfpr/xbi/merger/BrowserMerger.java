package edu.utfpr.xbi.merger;

import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.math.BigInteger;

import edu.utfpr.xbi.merger.BrowserRow;
import edu.utfpr.xbi.merger.ComparisonRow;
import edu.utfpr.xbi.merger.image.Comparator;

public class BrowserMerger {
    private PrintWriter writer;
    private Comparator c1;
    private Comparator c2;
    private Comparator c3;
    private BufferedReader br1;
    private BufferedReader br2;

    public void setComparator1(Comparator c1) { this.c1 = c1; }
    public void setComparator2(Comparator c2) { this.c2 = c2; }
    public void setComparator3(Comparator c3) { this.c3 = c3; }
    public void setPrintWriter(PrintWriter writer){ this.writer = writer; }

    public BrowserMerger (BufferedReader br1, BufferedReader br2) {
        this.br1 = br1;
        this.br2 = br2;
    }

    private BrowserRow readNext(BufferedReader br) throws Exception {
        String row = br.readLine();
        if (row == null)
            return null;
        return BrowserRow.readRow(row);
    }

    public boolean compareXPathId (BigInteger id1, BigInteger id2) {
        int base1 = id1.toString().length(),
            base2 = id2.toString().length();
        if (base2 > base1)
            return BigInteger.valueOf(10).pow(base2 - base1).multiply(id1).compareTo(id2) == 1;
        else
            return BigInteger.valueOf(10).pow(base1 - base2).multiply(id2).compareTo(id1) == -1;
    }

    public void merge () throws Exception {
        BrowserRow r1, r2;
        this._header();

        r1 = this.readNext(this.br1);
        r2 = this.readNext(this.br2);
        while (r1 != null || r2 != null) {
            if (r1 != null && (r2 == null || this.compareXPathId(r1.getXPathId(), r2.getXPathId()))) {
                this._merge(r1, null);
                r1 = this.readNext(this.br1);
            }
            if (r2 != null && (r1 == null || this.compareXPathId(r2.getXPathId(), r1.getXPathId()))) {
                this._merge(null, r2);
                r2 = this.readNext(this.br2);
            }

            if (r1 != null && r2 != null && r1.getXPathId().equals(r2.getXPathId())) {
                this._merge(r1, r2);
                r1 = this.readNext(this.br1);
                r2 = this.readNext(this.br2);
            }
        };

    }

    public void _header () {
        this.writer.println(ComparisonRow.header());
    }

    public void _merge (BrowserRow r1, BrowserRow r2) throws Exception {
        ComparisonRow result = new ComparisonRow(r1, r2);
        if (r1 == null || r2 == null) {
            result.setImageDiff(-1);
            result.setChiSquared(-1);
            result.setPHash(-1);
        } else {
            result.setImageDiff(this.c1.compare(
                        r1.getScreenshot(), r2.getScreenshot()));
            result.setChiSquared(this.c2.compare(
                        r1.getScreenshot(), r2.getScreenshot()));
            result.setPHash(this.c3.compare(
                        r1.getScreenshot(), r2.getScreenshot()));
        }
        this.writer.println(result.toRow());
    }
}
