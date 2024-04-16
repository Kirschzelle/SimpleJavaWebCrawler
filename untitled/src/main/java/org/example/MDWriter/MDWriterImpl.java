package org.example.MDWriter;

import org.example.Structs.CrawlArguments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MDWriterImpl implements MDWriter {
    private String filePath;

    @Override
    public void CreateFile(CrawlArguments arguments) throws IllegalStateException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("File path is not set.");
        }

        Path directory = Paths.get(filePath).getParent();
        if (directory != null) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create directories.", e);
            }
        }

        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create the file.", e);
        }
    }

    @Override
    public void SetFilePath(String filePath) throws IllegalArgumentException {
        if (!filePath.toLowerCase().endsWith(".md")) {
            throw new IllegalArgumentException("File path must end with '.md'.");
        }
        this.filePath = filePath;
    }

    @Override
    public void AppendFile(String content) throws IllegalStateException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("File path is not set.");
        }

        try {
            Files.write(Paths.get(filePath), content.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to append content to the file.", e);
        }
    }
}
