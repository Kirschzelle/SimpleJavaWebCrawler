package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

public interface MDWriter {
    public void CreateFile(CrawlArguments arguments) throws IllegalStateException;

    public void SetFilePath(String filePath) throws IllegalArgumentException;

    public void AppendFile(String content) throws IllegalStateException;
}
