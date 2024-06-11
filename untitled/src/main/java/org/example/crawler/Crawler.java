package org.example.crawler;

import org.example.structs.CrawlArguments;

public interface Crawler {
    void crawl(CrawlArguments arguments) throws IllegalStateException;
}
