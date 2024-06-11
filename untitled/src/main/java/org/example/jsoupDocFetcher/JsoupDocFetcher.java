package org.example.jsoupDocFetcher;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface JsoupDocFetcher {
    Document getWebsiteContent(String url) throws IOException;
}
