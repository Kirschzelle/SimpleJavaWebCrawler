package org.example.Crawler;

import org.example.Structs.CrawlArguments;

public interface Crawler {
    void Crawl(CrawlArguments arguments) throws IllegalStateException;
}
