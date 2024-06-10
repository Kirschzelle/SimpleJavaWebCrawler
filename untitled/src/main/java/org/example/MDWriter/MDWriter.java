package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

import java.util.List;

public interface MDWriter {
    void createFile(CrawlArguments arguments) throws IllegalStateException;

    void setFilePath(String filePath) throws IllegalArgumentException;

    void appendFile(String content, int level) throws IllegalStateException;

    void combineFiles(List<String> urls) throws IllegalArgumentException;
}
