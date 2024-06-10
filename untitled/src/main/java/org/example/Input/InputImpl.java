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
        List<String> urls = GetURL();
        List<String> topLevelDomains = GetTopLevelDomains();
        int depth = GetDepth();
        String targetLanguage = GetTargetLanguage();
        scanner.close();
        return new CrawlArguments(urls, depth, topLevelDomains, targetLanguage);
    }

    private List<String> GetURL() {
        List<String> urls = new ArrayList<>();
        while (true) {
            System.out.print("Please enter a url without the top-level domain\ne.g. https://www.example or type 'done' to finish: ");
            String url = scanner.nextLine();
            String urlPattern = "^(http|https)://(www\\.)?.+$";
            if (url.equalsIgnoreCase("done") && urls.isEmpty()) {
                System.out.println("At least 1 url is required. Please try again");
            } else if (url.equalsIgnoreCase("done")) {
                break;
            } else if (Pattern.matches(urlPattern, url)) {
                urls.add(url);
            } else {
                System.out.println("Invalid URL format. Please try again");
            }
        }
        return urls;
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