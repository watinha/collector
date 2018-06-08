package edu.utfpr.xbi.collector.browsers;

import edu.utfpr.xbi.collector.BrowserCollector;

public interface Browsers {
    public BrowserCollector createCollector (String url) throws Exception;
}
