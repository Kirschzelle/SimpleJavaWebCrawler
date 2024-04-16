package org.example.Structs;

import java.net.URL;
import java.util.List;

public record CrawlArguments(String url, int depth, List<String> topLevelDomains, String targetLanguage) {
}
