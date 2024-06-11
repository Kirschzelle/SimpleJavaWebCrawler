package org.example.translator;

public interface Translator {
    String translate(String toTranslate, String targetLanguage);
    void setAPIKey(String apiKey);
}
