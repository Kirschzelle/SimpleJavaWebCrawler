package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

public interface MDWriter {
    void CreateFile(CrawlArguments arguments) throws IllegalStateException;

    void SetFilePath(String filePath) throws IllegalArgumentException;

    void AppendFile(String content) throws IllegalStateException;
}
