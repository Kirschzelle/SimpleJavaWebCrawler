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
            Files.deleteIfExists(Paths.get(filePath));
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create the file.", e);
        }
        AppendFile("\n                             aHHHHb.\n" + "                            /`HHHHHHb\n" + "                            > `dHHHHH\n" + "                           /  dHHHHHHb\n" + "                           ; /HHHHHHHH\n" + "                          .-\"\"FHHHHHHH\n" + "                         (    JHHHHHHH.\n" + "                          \\    FHHHHHHb\\\n" + "                         / \\   JHHHHHHH \\\n" + "                       .'   \\   FHHHHHH  \\\n" + "                       `._,  \\   \\HHHHF   Y\n" + "                          |   >   \\HHF`Y  |\n" + "                          | .'   .'Y   |  |\n" + "                          .'   .': |   |  j\n" + "                        .'  _.'  : \\   |  F\n" + "                      .' .-;       .`-.| j\n" + "                     .  ;  :        .  | /\n" + "                     :  (  |......___)/_|)\n" + "                      ```  '\"\"\"\"-._   _.-'  >scritch<\n" + "                           |       `.'-'_'\n" + "                           |  ` - '/  -'|     >scracth<\n" + "                           |      /     |\n" + "                           |     /|     |\n" + "                           |     ||Krogg|dp", 0);
        AppendFile("\nUrl: "+arguments.url()+"\nDomains: "+arguments.topLevelDomains()+"\nDepth: "+arguments.depth()+"\nLanguage: "+arguments.targetLanguage(),0);
    }

    @Override
    public void SetFilePath(String filePath) throws IllegalArgumentException {
        if (!filePath.toLowerCase().endsWith(".md")) {
            throw new IllegalArgumentException("File path must end with '.md'.");
        }
        this.filePath = filePath;
    }

    @Override
    public void AppendFile(String content, int level) throws IllegalStateException, IllegalArgumentException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("File path is not set.");
        }
        if (level < 0){
            throw  new IllegalArgumentException("Depth level cannot be smaller than 0.");
        }

        try {
            Files.write(Paths.get(filePath), AddDepthVisualToString(content, level).getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to append content to the file.", e);
        }
    }

    private String AddDepthVisualToString(String input, int level){
        StringBuilder toInsert = new StringBuilder();
        toInsert.append("\n");
        toInsert.append("-".repeat(Math.max(0, level)));

        String[] parts = input.split("\\n");

        StringBuilder modifiedString = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            modifiedString.append(parts[i]);
            if (i < parts.length - 1) {
                modifiedString.append(toInsert);
            }
        }

        return modifiedString.toString();
    }
}
