package edu.utfpr.xbi.merger.image;

import java.io.File;
import com.pragone.jphash.jpHash;
import com.pragone.jphash.image.radial.RadialHash;

public class PHashCompartor implements Comparator {
    public double compare (File screenshot1, File screenshot2) throws Exception {
        if (screenshot1 == null && screenshot2 == null)
            return 0;
        if (screenshot1 == null || screenshot2 == null)
            return -1;
        RadialHash hash1 = jpHash.getImageRadialHash(screenshot1),
                   hash2 = jpHash.getImageRadialHash(screenshot2);
        return jpHash.getSimilarity(hash1, hash2);
    }
}
