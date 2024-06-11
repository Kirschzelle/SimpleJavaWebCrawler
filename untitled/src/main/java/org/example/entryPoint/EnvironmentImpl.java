package org.example.entryPoint;

import org.example.crawler.Crawler;
import org.example.crawler.CrawlerImpl;
import org.example.input.Input;
import org.example.input.InputImpl;
import org.example.jsoupDocFetcher.JsoupDocFetcher;
import org.example.jsoupDocFetcher.JsoupDocFetcherImpl;
import org.example.mdWriter.MDWriter;
import org.example.mdWriter.MDWriterImpl;
import org.example.translator.Translator;
import org.example.translator.TranslatorImpl;

public class EnvironmentImpl implements Environment{
    private final Input input = new InputImpl();
    @Override
    public MDWriter getWriter() {
        return new MDWriterImpl();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public JsoupDocFetcher getFetcher() {
        return new JsoupDocFetcherImpl();
    }

    @Override
    public Translator getTranslator() {
        return new TranslatorImpl();
    }

    @Override
    public Crawler getCrawler(Translator translator, MDWriter writer, JsoupDocFetcher fetcher) {
        return new CrawlerImpl(translator, writer, fetcher);
    }
}
