package org.example.Input;

import org.example.Structs.CrawlArguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputImpl implements Input {
    private Scanner scanner;

    @Override
    public CrawlArguments GetInput() {
        scanner = new Scanner(System.in);
        String url = GetURL();
        List<String> topLevelDomains = GetTopLevelDomains();
        int depth = GetDepth();
        String targetLanguage = GetTargetLanguage();
        scanner.close();
        return new CrawlArguments(url, depth, topLevelDomains, targetLanguage);
    }

    private String GetURL() {
        String url;
        while (true) {
            System.out.print("Please enter a url without the top-level domain\ne.g. https://www.example: ");
            url = scanner.nextLine();
            String urlPattern = "^(http|https)://(www\\.)?.+$";
            if (Pattern.matches(urlPattern, url)) {
                break;
            } else {
                System.out.println("Invalid URL format. Please try again");
            }
        }
        return url;
    }

    private int GetDepth() {
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

    private List<String> GetTopLevelDomains() {
        List<String> topLevelDomains = new ArrayList<>();
        List<String> validTopLevelDomains = Arrays.asList(".at", ".com", ".org", ".net", ".gov", ".edu", ".mil", ".info", ".biz", ".io", ".co", ".me", ".tv", ".ca", ".uk", ".au", ".de", ".jp", ".fr", ".cn", ".it");
        while (true) {
            System.out.print("Please enter a top-level domain (e.g. .com), or type 'done' to finish: ");
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

    private String GetTargetLanguage() {
        String targetLanguage;
        List<String> languageCodes = Arrays.asList(
                "ar", "bg", "bn", "bs", "ca", "cnr", "cs", "cy", "da", "de",
                "el", "en", "es", "et", "eu", "fi", "fr", "fr-CA", "ga",
                "gu", "he", "hi", "hr", "hu", "id", "it", "ja", "kn", "ko",
                "lt", "lv", "ml", "mr", "ms", "mt", "nb", "ne", "nl", "pa",
                "pl", "pt", "ro", "ru", "si", "sk", "sl", "sr", "sv", "ta",
                "te", "th", "tr", "uk", "ur", "vi", "zh", "zh-TW"
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