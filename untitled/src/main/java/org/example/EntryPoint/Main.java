package org.example.EntryPoint;

import org.example.Crawler.Crawler;
import org.example.Crawler.CrawlerImpl;
import org.example.Input.Input;
import org.example.Input.InputImpl;
import org.example.MDWriter.MDWriter;
import org.example.MDWriter.MDWriterImpl;
import org.example.Structs.CrawlArguments;
import org.example.Translator.Translator;
import org.example.Translator.TranslatorImpl;

public class Main {
    public static void main(String[] args) {
        MDWriter writer = new MDWriterImpl();
        Translator translator = new TranslatorImpl();
        Input input = new InputImpl();
        CrawlArguments arguments = input.GetInput();
        writer.SetFilePath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/outputs/"+arguments.url()+".md");
        writer.CreateFile(arguments);
        Crawler crawler = new CrawlerImpl(translator, writer);
        crawler.Crawl(arguments);
    }
}

