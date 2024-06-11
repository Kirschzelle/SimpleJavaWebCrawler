package org.example.mdWriter;

import org.example.entryPoint.Main;
import org.example.structs.CrawlArguments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MDWriterImpl implements MDWriter {
    private String filePath;

    @Override
    public void createFile(CrawlArguments arguments) throws IllegalStateException {
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
        appendFile("""

                                             aHHHHb.
                                            /`HHHHHHb
                                            > `dHHHHH
                                           /  dHHHHHHb
                                           ; /HHHHHHHH
                                          .-""FHHHHHHH
                                         (    JHHHHHHH.
                                          \\    FHHHHHHb\\
                                         / \\   JHHHHHHH \\
                                       .'   \\   FHHHHHH  \\
                                       `._,  \\   \\HHHHF   Y
                                          |   >   \\HHF`Y  |
                                          | .'   .'Y   |  |
                                          .'   .': |   |  j
                                        .'  _.'  : \\   |  F
                                      .' .-;       .`-.| j
                                     .  ;  :        .  | /
                                     :  (  |......___)/_|)
                                      ```  '""\""-._   _.-'  >scritch<
                                           |       `.'-'_'
                                           |  ` - '/  -'|     >scracth<
                                           |      /     |
                                           |     /|     |
                                           |     ||Krogg|dp\
                """, 0);
        appendFile("\nUrl: "+arguments.url()+"\nDomains: "+arguments.topLevelDomains()+"\nDepth: "+arguments.depth()+"\nLanguage: "+arguments.targetLanguage(),0);
    }

    @Override
    public void setFilePath(String filePath) throws IllegalArgumentException {
        if (!filePath.toLowerCase().endsWith(".md")) {
            throw new IllegalArgumentException("File path must end with '.md'.");
        }
        this.filePath = filePath;
    }

    @Override
    public void appendFile(String content, int level) throws IllegalStateException, IllegalArgumentException {
        if (filePath == null || filePath.isEmpty() || Files.notExists(Path.of(filePath))) {
            throw new IllegalStateException("File path is not set.");
        }
        if (level < 0){
            throw  new IllegalArgumentException("Depth level cannot be smaller than 0.");
        }

        try {
            Files.write(Paths.get(filePath), addDepthVisualToString(content, level).getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to append content to the file.", e);
        }
    }

    @Override
    public void combineFiles(List<String> urls) throws IllegalArgumentException {
        for (String url:urls) {
            try {
                appendFile(readFile(url),0);
            } catch (IOException e) {
                System.out.println("Failed to find results of url: "+url+" \nMaybe file was deleted or moved? Skipping file.");
            }
        }
    }

    private String readFile(String name) throws IOException {
        return Files.readString(Path.of(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + name.replace('/', '-') + ".md"));
    }

    private String addDepthVisualToString(String input, int level){
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
