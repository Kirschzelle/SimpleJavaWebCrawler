package org.example.Translator;

import java.util.List;

public interface Translator {
    public String Translate(String toTranslate, String targetLanguage);
    public List<String> SupportedLanguages();
}
