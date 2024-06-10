package org.example.Structs;

import java.util.List;

public record CrawlArguments(String url, int depth, List<String> topLevelDomains, String targetLanguage) {
}
