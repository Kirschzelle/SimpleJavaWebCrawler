package org.example.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslatorImplTest {
    private TranslatorImpl translator;

    @BeforeEach
    void setUp() {
        translator = new TranslatorImpl();
    }

    @Test
    void testTranslator(){
        String translatedWord = translator.translate("Kulturwissenschaft", "en");
        assertEquals("Cultural Studies", translatedWord);
    }
}
