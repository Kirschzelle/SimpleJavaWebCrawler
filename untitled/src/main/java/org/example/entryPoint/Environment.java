package org.example.entryPoint;

import org.example.crawler.Crawler;
import org.example.input.Input;
import org.example.jsoupDocFetcher.JsoupDocFetcher;
import org.example.mdWriter.MDWriter;
import org.example.translator.Translator;

public interface Environment {
    MDWriter getWriter();
    Input getInput();
    JsoupDocFetcher getFetcher();
    Translator getTranslator();
    Crawler getCrawler(Translator translator, MDWriter writer, JsoupDocFetcher fetcher);
}
