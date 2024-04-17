package org.example.JsoupDocFetcher;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface JsoupDocFetcher {
    Document GetWebsiteContent(String url) throws IOException;
}
