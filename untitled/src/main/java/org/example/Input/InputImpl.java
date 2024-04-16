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
        int depth = GetDepth();
        List<String> topLevelDomains = GetTopLevelDomains();
        String targetLanguage = GetTargetLanguage();
        scanner.close();
        return new CrawlArguments(url, depth, topLevelDomains, targetLanguage);
    }

    private String GetURL() {
        String url;
        while (true) {
            System.out.print("Please enter a url without the top-level domain\n e.g. https://www.example");
            url = scanner.nextLine();
            String urlPattern = "^https://www\\..+$";
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
            System.out.print("Please enter an integer to define the depth");
            String input = scanner.nextLine();
            try {
                depth = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer to define the depth");
            }
        }
        return depth;
    }

    private List<String> GetTopLevelDomains() {
        List<String> topLevelDomains = new ArrayList<>();
        List<String> validTopLevelDomains = Arrays.asList(".com", ".org", ".net", ".gov", ".edu", ".mil", ".info", ".biz", ".io", ".co", ".me", ".tv", ".ca", ".uk", ".au", ".de", ".jp", ".fr", ".cn", ".it");
        while (true) {
            System.out.print("Please enter a top-level domain (e.g., .com), or type 'done' to finish");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
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
        List<String> supportedLanguages = new ArrayList<>();
        while (true) {
            System.out.print("Please enter a target language to translate");
            targetLanguage = scanner.nextLine();
            if (supportedLanguages.contains(targetLanguage.toLowerCase())) {
                break;
            } else {
                System.out.println("Invalid target language. Please try again");
            }
        }
        return targetLanguage;
    }
}