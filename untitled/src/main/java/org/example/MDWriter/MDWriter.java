package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

public interface MDWriter {
    public void WriteFile(String content, CrawlArguments arguments, String filePath);
}
