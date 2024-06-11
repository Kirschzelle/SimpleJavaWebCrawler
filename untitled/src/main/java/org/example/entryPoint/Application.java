package org.example.entryPoint;

import org.example.crawler.Crawler;
import org.example.jsoupDocFetcher.JsoupDocFetcher;
import org.example.mdWriter.MDWriter;
import org.example.structs.CrawlArguments;
import org.example.structs.InputArguments;
import org.example.translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void run(Environment environment){
        String apiKey = environment.getInput().getAPIKey();
        InputArguments arguments = environment.getInput().getInput();
        List<Thread> threads = createThreads(arguments, apiKey, environment);
        System.out.println("Crawling...");
        waitOnThreads(threads);
        combineResults(arguments, environment.getWriter());
        System.out.println("Done, find results in:"+Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + "results" + ".md" );
    }
    private static void crawlUrl(CrawlArguments arguments, MDWriter writer, Crawler crawler){
        writer.setFilePath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + arguments.url().replace('/', '-') + ".md");
        writer.createFile(arguments);
        crawler.crawl(arguments);
    }

    private static List<Thread> createThreads(InputArguments arguments, String apiKey, Environment environment){
        List<Thread> threads = new ArrayList<>();
        for (String url:arguments.urls()) {
            MDWriter writer = environment.getWriter();
            Translator translator = environment.getTranslator();
            translator.setAPIKey(apiKey);
            JsoupDocFetcher fetcher = environment.getFetcher();
            Thread thread = new Thread(() -> crawlUrl(new CrawlArguments(url, arguments.depth(), arguments.topLevelDomains(), arguments.targetLanguage()),writer, environment.getCrawler(translator, writer, fetcher)));
            thread.start();
            threads.add(thread);
        }
        return threads;
    }

    private static void waitOnThreads(List<Thread> threads){
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static void combineResults(InputArguments arguments, MDWriter writer){
        writer.setFilePath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + "results" + ".md");
        writer.createFile(new CrawlArguments(arguments.urls().toString(),arguments.depth(),arguments.topLevelDomains(), arguments.targetLanguage()));
        writer.combineFiles(arguments.urls());
    }
}
