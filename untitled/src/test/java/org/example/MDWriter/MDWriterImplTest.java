package org.example.MDWriter;

import org.example.EntryPoint.Main;
import org.example.Structs.CrawlArguments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MDWriterImplTest {

    MDWriter writer;
    CrawlArguments arguments;
    String txtFilePath;
    String mdFilePath;

    @BeforeEach
    void setUp() {
        writer = new MDWriterImpl();
        List<String> domains = new ArrayList<>();
        domains.add(".com");
        domains.add(".at");
        arguments = new CrawlArguments("https://www.example", 1, domains, "dn");
        txtFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + arguments.url().replace('/', '-') + ".txt";
        mdFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + arguments.url().replace('/', '-') + ".md";
    }

    @AfterEach
    void cleanUp() {
        try {
            Files.deleteIfExists(Path.of(mdFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWriterFilePathAndCreate() {
        Assertions.assertThrows(IllegalStateException.class, () -> writer.CreateFile(arguments));
        Assertions.assertThrows(IllegalArgumentException.class, () -> writer.SetFilePath(txtFilePath));
        writer.SetFilePath(mdFilePath);
        writer.CreateFile(arguments);
        Assertions.assertTrue(Files.exists(Path.of(mdFilePath)));
    }

    @Test
    void testWriterAppend() {
        Assertions.assertThrows(IllegalStateException.class, () -> writer.AppendFile("test", 3));
        writer.SetFilePath(mdFilePath);
        Assertions.assertThrows(IllegalStateException.class, () -> writer.AppendFile("test", 3));
        writer.CreateFile(arguments);
        Assertions.assertThrows(IllegalArgumentException.class, () -> writer.AppendFile("test", -1));
        writer.AppendFile("\ntest", 0);
        writer.AppendFile("\ntest", 3);
        Assertions.assertDoesNotThrow(() -> {
                    BufferedReader reader = new BufferedReader(new FileReader(mdFilePath));
                    String line;
                    String lastLine = null;
                    while ((line = reader.readLine()) != null) {
                        lastLine = line;
                    }
                    reader.close();
                    Assertions.assertTrue(lastLine != null && lastLine.trim().equals("---test"));
                }
        );
    }
}
