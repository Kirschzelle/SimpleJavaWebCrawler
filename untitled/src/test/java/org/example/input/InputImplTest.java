package org.example.input;

import org.example.structs.InputArguments;
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
        String simulatedInput = "https://www.example\ndone\n.com\ndone\n3\nen";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        InputArguments inputArguments = input.getInput();
        InputArguments expectedInputArguments = new InputArguments(List.of("https://www.example"), 3, List.of(".com"), "en");

        assertEquals(expectedInputArguments, inputArguments);
        System.setIn(System.in);
    }
}
