package org.example.Crawler;

import org.example.Structs.CrawlArguments;

public interface Crawler {
    void crawl(CrawlArguments arguments) throws IllegalStateException;
}
