package org.example.entryPoint;

import org.example.crawler.Crawler;
import org.example.crawler.CrawlerImpl;
import org.example.input.Input;
import org.example.input.InputImpl;
import org.example.jsoupDocFetcher.JsoupDocFetcher;
import org.example.jsoupDocFetcher.JsoupDocFetcherImpl;
import org.example.mdWriter.MDWriter;
import org.example.mdWriter.MDWriterImpl;
import org.example.structs.CrawlArguments;
import org.example.structs.InputArguments;
import org.example.translator.Translator;
import org.example.translator.TranslatorImpl;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void run(){
        Input input = new InputImpl();
        String apiKey = input.getAPIKey();
        InputArguments arguments = input.getInput();
        List<Thread> threads = createThreads(arguments, apiKey);
        System.out.println("Crawling...");
        waitOnThreads(threads);
        combineResults(arguments, new MDWriterImpl());
        System.out.println("Done, find results in:"+Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + "results" + ".md" );
    }
    private static void crawlUrl(CrawlArguments arguments, MDWriter writer, Crawler crawler){
        writer.setFilePath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + arguments.url().replace('/', '-') + ".md");
        writer.createFile(arguments);
        crawler.crawl(arguments);
    }

    private static List<Thread> createThreads(InputArguments arguments, String apiKey){
        List<Thread> threads = new ArrayList<>();
        for (String url:arguments.urls()) {
            MDWriter writer = new MDWriterImpl();
            Translator translator = new TranslatorImpl();
            translator.setAPIKey(apiKey);
            JsoupDocFetcher fetcher = new JsoupDocFetcherImpl();
            Thread thread = new Thread(() -> crawlUrl(new CrawlArguments(url, arguments.depth(), arguments.topLevelDomains(), arguments.targetLanguage()),writer, new CrawlerImpl(translator, writer, fetcher)));
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
