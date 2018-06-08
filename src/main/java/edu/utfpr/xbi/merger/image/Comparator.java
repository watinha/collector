package edu.utfpr.xbi.merger.image;

import java.io.File;

public interface Comparator {
    public double compare (File screenshot1, File screenshot2) throws Exception;
}
