package org.example.Structs;

import java.util.List;

public record CrawlArguments(List<String> urls, int depth, List<String> topLevelDomains, String targetLanguage) {
}
