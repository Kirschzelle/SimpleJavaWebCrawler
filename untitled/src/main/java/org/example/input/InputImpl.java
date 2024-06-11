package org.example.input;

import org.example.structs.InputArguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputImpl implements Input {
    private Scanner scanner;

    @Override
    public InputArguments getInput() {
        scanner = new Scanner(System.in);
        List<String> urls = getURL();
        List<String> topLevelDomains = getTopLevelDomains();
        int depth = getDepth();
        String targetLanguage = getTargetLanguage();
        scanner.close();
        return new InputArguments(urls, depth, topLevelDomains, targetLanguage);
    }

    @Override
    public String getAPIKey(){
        scanner = new Scanner(System.in);
        String apiKey;
        System.out.print("Please enter the API Key for Google Translate (rapidapi.com): ");
        while (true) {
            apiKey = scanner.nextLine();
            if(apiKey.isEmpty()){
                System.out.println("Please enter the API Key: ");
            } else {
                break;
            }
        }
        return apiKey;
    }

    private List<String> getURL() {
        List<String> urls = new ArrayList<>();
        System.out.print("Please enter a url without the top-level domain e.g. https://www.example or type 'done' to finish\n");
        while (true) {
            System.out.print("Enter new url: ");
            String url = scanner.nextLine();
            String urlPattern = "^(http|https)://(www\\.)?.+$";
            if (url.equalsIgnoreCase("done") && urls.isEmpty()) {
                System.out.println("At least 1 url is required. Please try again");
            } else if (url.equalsIgnoreCase("done")) {
                break;
            } else if (Pattern.matches(urlPattern, url)) {
                if(urls.contains(url)){
                    System.out.println("URL already entered.");
                } else {
                    urls.add(url);
                }
            } else {
                System.out.println("Invalid URL format. Please try again");
            }
        }
        return urls;
    }

    private int getDepth() {
        int depth;
        while (true) {
            System.out.print("Please enter an integer to define the depth: ");
            String input = scanner.nextLine();
            try {
                depth = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer to define the depth: ");
            }
        }
        return depth;
    }

    private List<String> getTopLevelDomains() {
        List<String> topLevelDomains = new ArrayList<>();
        List<String> validTopLevelDomains = Arrays.asList(".at", ".com", ".org", ".net", ".gov", ".edu", ".mil", ".info", ".biz", ".io", ".co", ".me", ".tv", ".ca", ".uk", ".au", ".de", ".jp", ".fr", ".cn", ".it");
        System.out.print("Please enter a top-level domain (e.g. .com), or type 'done' to finish\n");
        while (true) {
            System.out.print("Enter new top-level domain: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done") && topLevelDomains.isEmpty()) {
                System.out.println("At least 1 top-level domain is required. Please try again");
            } else if (input.equalsIgnoreCase("done")) {
                break;
            } else if (validTopLevelDomains.contains(input.toLowerCase())) {
                topLevelDomains.add(input);
            } else {
                System.out.println("Invalid top-level domain. Please try again");
            }
        }
        return topLevelDomains;
    }

    private String getTargetLanguage() {
        String targetLanguage;
        List<String> languageCodes = Arrays.asList(
                "af", "sq", "am", "ar", "hy", "as", "ay", "az", "bm", "eu", "be", "bn", "bho", "bs",
                "bg", "ca", "ceb", "zh", "zh-cn", "zh-tw", "zh-sg", "zh-hk", "co", "hr", "cs", "da", "dv",
                "doi", "nl", "en", "eo", "et", "ee", "fil", "fi", "fr", "fy", "gl", "ka", "de", "el", "gn",
                "gu", "ht", "ha", "haw", "he", "iw", "hi", "hmn", "hu", "is", "ig", "ilo", "id", "ga", "it",
                "ja", "jv", "jw", "kn", "kk", "km", "rw", "gom", "ko", "kri", "ku", "ckb", "ky", "lo", "la",
                "lv", "ln", "lt", "lg", "lb", "mk", "mai", "mg", "ms", "ml", "mt", "mi", "mr", "mni-mtei",
                "lus", "mn", "my", "ne", "no", "ny", "or", "om", "ps", "fa", "pl", "pt", "pa", "qu", "ro",
                "ru", "sm", "sa", "gd", "nso", "sr", "st", "sn", "sd", "si", "sk", "sl", "so", "es", "su",
                "sw", "sv", "tl", "tg", "ta", "tt", "te", "th", "ti", "ts", "tr", "tk", "ak", "uk", "ur",
                "ug", "uz", "vi", "cy", "xh", "yi", "yo", "zu"
        );
        while (true) {
            System.out.print("Please enter a target language (e.g. en, ru, de): ");
            targetLanguage = scanner.nextLine();
            if (languageCodes.contains(targetLanguage.toLowerCase())) {
                break;
            } else {
                System.out.println("Invalid target language. Please try again");
            }
        }
        return targetLanguage;
    }
}