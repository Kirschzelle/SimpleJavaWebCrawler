package org.example.Translator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TranslatorImpl implements Translator {
    private static final String API_KEY = "9da3583d25msh728bd7038dcfdddp15dcdajsn71a3bac3efcd";
    private static final String TEXT_TRANSLATOR_ENDPOINT = "text-translator-v2.p.rapidapi.com";

    @Override
    public String Translate(String toTranslate, String targetLanguage) {
        String sourceLanguage = GetSourceLanguage(toTranslate);
        if (sourceLanguage.equals(targetLanguage)) {
            return toTranslate;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://text-translator-v2.p.rapidapi.com/api/translate/text"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", TEXT_TRANSLATOR_ENDPOINT)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\n    \"text\": \"" + toTranslate + "\",\n    \"source\": \"" + sourceLanguage + "\",\n    \"target\": \"" + targetLanguage + "\"\n}"))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonResponse = new JSONObject(response.body());
        String status = jsonResponse.getString("status");
        if ("success".equals(status)) {
            JSONObject payload = jsonResponse.getJSONObject("payload");
            JSONArray translations = payload.getJSONArray("translations");
            JSONObject translationInfo = translations.getJSONObject(0);
            return translationInfo.getString("translation");
        }
        return toTranslate;
    }

    private String GetSourceLanguage(String toTranslate) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://text-translator-v2.p.rapidapi.com/api/translate/detect"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", TEXT_TRANSLATOR_ENDPOINT)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\n    \"text\": \"" + toTranslate + "\"\n}"))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonResponse = new JSONObject(response.body());
        String status = jsonResponse.getString("status");
        if ("success".equals(status)) {
            JSONObject payload = jsonResponse.getJSONObject("payload");
            JSONArray languages = payload.getJSONArray("languages");
            JSONObject languageInfo = languages.getJSONObject(0);
            JSONObject language = languageInfo.getJSONObject("language");
            return language.getString("language");
        }
        return "en";
    }
}
