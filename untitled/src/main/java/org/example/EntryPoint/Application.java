package org.example.EntryPoint;

import org.example.Crawler.Crawler;
import org.example.Crawler.CrawlerImpl;
import org.example.Input.Input;
import org.example.Input.InputImpl;
import org.example.JsoupDocFetcher.JsoupDocFetcher;
import org.example.JsoupDocFetcher.JsoupDocFetcherImpl;
import org.example.MDWriter.MDWriter;
import org.example.MDWriter.MDWriterImpl;
import org.example.Structs.CrawlArguments;
import org.example.Structs.InputArguments;
import org.example.Translator.Translator;
import org.example.Translator.TranslatorImpl;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void run(){
        Input input = new InputImpl();
        InputArguments arguments = input.getInput();
        List<Thread> threads = createThreads(arguments);
        System.out.println("Crawling...");
        waitOnThreads(threads);
        combineResults(arguments, new MDWriterImpl());
        System.out.println("Done, find results in:"+Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + "results" + ".md" );
    }

    private static void crawlUrl(CrawlArguments arguments, MDWriter writer, Translator translator, JsoupDocFetcher fetcher, Crawler crawler){
        writer.setFilePath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/outputs/" + arguments.url().replace('/', '-') + ".md");
        writer.createFile(arguments);
        crawler.crawl(arguments);
    }

    private static List<Thread> createThreads(InputArguments arguments){
        List<Thread> threads = new ArrayList<>();
        for (String url:arguments.urls()) {
            MDWriter writer = new MDWriterImpl();
            Translator translator = new TranslatorImpl();
            JsoupDocFetcher fetcher = new JsoupDocFetcherImpl();
            Thread thread = new Thread(() -> crawlUrl(new CrawlArguments(url, arguments.depth(), arguments.topLevelDomains(), arguments.targetLanguage()),writer, translator, fetcher, new CrawlerImpl(translator, writer, fetcher)));
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
