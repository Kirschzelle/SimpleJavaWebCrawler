package org.example.Input;

import org.example.Structs.CrawlArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputImplTest {

    private InputImpl input;

    @BeforeEach
    void setUp() {
        input = new InputImpl();
    }

    @Test
    void testInput() {
        String simulatedInput = "https://www.example\n.com\ndone\n3\nen";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        CrawlArguments crawlArguments = input.getInput();

        assertEquals("https://www.example", crawlArguments.url());
        assertEquals(3, crawlArguments.depth());
        assertEquals(List.of(".com"), crawlArguments.topLevelDomains());
        assertEquals("en", crawlArguments.targetLanguage());
        System.setIn(System.in);
    }
}
