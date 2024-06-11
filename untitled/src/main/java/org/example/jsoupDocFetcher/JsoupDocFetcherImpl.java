package org.example.jsoupDocFetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupDocFetcherImpl implements JsoupDocFetcher {
    public Document getWebsiteContent(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
