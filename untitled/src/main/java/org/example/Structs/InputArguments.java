package org.example.Structs;

import java.util.List;

public record InputArguments(List<String>urls, int depth, List<String> topLevelDomains, String targetLanguage) {
}
