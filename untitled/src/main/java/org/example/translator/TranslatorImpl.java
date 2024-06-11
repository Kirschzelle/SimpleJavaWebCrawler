package org.example.translator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TranslatorImpl implements Translator {
    private String apiKey = "9da3583d25msh728bd7038dcfdddp15dcdajsn71a3bac3efcd";
    private static final String TEXT_TRANSLATOR_ENDPOINT = "google-translate113.p.rapidapi.com";

    @Override
    public String translate(String toTranslate, String targetLanguage) {
        HttpRequest request = buildRequest(toTranslate, targetLanguage);
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return toTranslate;
        }
        if (response.statusCode() != 200) {
            return toTranslate;
        }
        return getTranslatedText(response, toTranslate);
    }

    @Override
    public void setAPIKey(String apiKey){
        this.apiKey = apiKey;
    }

    private HttpRequest buildRequest(String toTranslate, String targetLanguage){
        return HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", TEXT_TRANSLATOR_ENDPOINT)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"from\":\"auto\",\"to\":\"" + targetLanguage + "\",\"text\":\"" + toTranslate + "\"}"))
                .build();
    }

    private String getTranslatedText(HttpResponse<String> response, String toTranslate){
        try {
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getString("trans");
        } catch (JSONException e) {
            return toTranslate;
        }
    }
}
