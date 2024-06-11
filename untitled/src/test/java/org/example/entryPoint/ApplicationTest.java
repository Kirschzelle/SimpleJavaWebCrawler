package org.example.entryPoint;

import org.example.crawler.Crawler;
import org.example.jsoupDocFetcher.JsoupDocFetcher;
import org.example.mdWriter.MDWriter;
import org.example.structs.CrawlArguments;
import org.example.structs.InputArguments;
import org.example.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings({"JavaReflectionMemberAccess"})
class ApplicationTest {

    private MDWriter mockWriter;
    private Translator mockTranslator;
    private JsoupDocFetcher mockFetcher;
    private Crawler mockCrawler;

    @BeforeEach
    void setUp() {
        mockWriter = mock(MDWriter.class);
        mockTranslator = mock(Translator.class);
        mockFetcher = mock(JsoupDocFetcher.class);
        mockCrawler = mock(Crawler.class);
    }

    @Test
    void testCombineResults() throws Exception {
        InputArguments inputArguments = new InputArguments(
                Arrays.asList("http://example.com", "http://example.org"),
                1,
                new ArrayList<>(),
                "en"
        );

        Method combineResultsMethod = Application.class.getDeclaredMethod("combineResults", InputArguments.class, MDWriter.class);
        combineResultsMethod.setAccessible(true);

        combineResultsMethod.invoke(null, inputArguments, mockWriter);

        verify(mockWriter).createFile(any(CrawlArguments.class));
        verify(mockWriter).combineFiles(inputArguments.urls());
    }

    @Test
    void testCrawlUrl() throws Exception {
        CrawlArguments crawlArguments = new CrawlArguments("http://example.com", 1, new ArrayList<>(), "en");

        Method crawlUrlMethod = Application.class.getDeclaredMethod("crawlUrl", CrawlArguments.class, MDWriter.class, Translator.class, JsoupDocFetcher.class, Crawler.class);
        crawlUrlMethod.setAccessible(true);

        crawlUrlMethod.invoke(null, crawlArguments, mockWriter, mockTranslator, mockFetcher, mockCrawler);

        verify(mockWriter).createFile(crawlArguments);
        verify(mockCrawler).crawl(crawlArguments);
    }

    @Test
    void testCreateThreads() throws Exception {
        InputArguments inputArguments = new InputArguments(
                Arrays.asList("http://example.com", "http://example.org"),
                1,
                new ArrayList<>(),
                "en"
        );

        Method createThreadsMethod = Application.class.getDeclaredMethod("createThreads", InputArguments.class);
        createThreadsMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Thread> threads = (List<Thread>) createThreadsMethod.invoke(null, inputArguments);

        assertEquals(2, threads.size());
        for (Thread thread : threads) {
            assertTrue(thread.isAlive());
        }
    }
}