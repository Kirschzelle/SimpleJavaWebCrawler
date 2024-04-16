package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

public interface MDWriter {
    void CreateFile(CrawlArguments arguments) throws IllegalStateException;

    void SetFilePath(String filePath) throws IllegalArgumentException;

    public void AppendFile(String content, int level) throws IllegalStateException;
}
