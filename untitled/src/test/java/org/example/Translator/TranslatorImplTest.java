package org.example.Translator;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class TranslatorImplTest {
    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = Mockito.mock(Translator.class);
    }
}
