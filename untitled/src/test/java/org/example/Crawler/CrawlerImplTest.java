package org.example.Crawler;

import org.example.Structs.CrawlArguments;
import org.example.MDWriter.MDWriter;
import org.example.Translator.Translator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

public class CrawlerImplTest {

    private CrawlerImpl crawler;
    private MDWriter mdWriter;
    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = Mockito.mock(Translator.class);
        mdWriter = Mockito.mock(MDWriter.class);
        crawler = spy(new CrawlerImpl(translator, mdWriter)); // Spy on the real CrawlerImpl object
    }

    @Test
    void testCrawl() throws IOException {
        List<String> domains = new ArrayList<>();
        domains.add(".com");
        domains.add(".at");
        CrawlArguments arguments = new CrawlArguments("https://www.example", 3, domains, "dn");

        Elements mockElements1 = mock(Elements.class);
        when(mockElements1.size()).thenReturn(3);
        Element header1 = createMockHeader(Tag.valueOf("h1"), "Header 1");
        Element header2 = createMockHeader(Tag.valueOf("h2"), "Header 2");
        Element header3 = createMockHeader(Tag.valueOf("h3"), "Header 3");
        Element header4 = createMockHeader(Tag.valueOf("h4"), "Header 4");
        Element header5 = createMockHeader(Tag.valueOf("h5"), "Header 5");
        Element header6 = createMockHeader(Tag.valueOf("h6"), "Header 6");
        when(mockElements1.get(0)).thenReturn(header1);
        when(mockElements1.get(1)).thenReturn(header2);
        when(mockElements1.get(2)).thenReturn(header3);
        Iterator<Element> mockIterator1 = mock(Iterator.class);
        when(mockIterator1.hasNext()).thenReturn(true, true, true, false);
        when(mockIterator1.next()).thenReturn(header1, header2, header3);
        when(mockElements1.iterator()).thenReturn(mockIterator1);


        Elements mockElements2 = mock(Elements.class);
        when(mockElements2.size()).thenReturn(3);
        when(mockElements2.get(3)).thenReturn(header4);
        when(mockElements2.get(4)).thenReturn(header5);
        when(mockElements2.get(5)).thenReturn(header6);
        Iterator<Element> mockIterator2 = mock(Iterator.class);
        when(mockIterator2.hasNext()).thenReturn(true, true, true, false);
        when(mockIterator2.next()).thenReturn(header4, header5, header6);
        when(mockElements2.iterator()).thenReturn(mockIterator2);

        Elements mockLinks1 = mock(Elements.class);
        when(mockLinks1.size()).thenReturn(1);
        Element link1 = createMockLink("https://www.example.com", "https://www.example.com");
        Element link2 = createMockLink("https://www.example.at", "https://www.example.at");
        when(mockLinks1.get(0)).thenReturn(link1);
        Iterator<Element> mockIterator3 = mock(Iterator.class);
        when(mockIterator3.hasNext()).thenReturn(true, false);
        when(mockIterator3.next()).thenReturn(link1);
        when(mockLinks1.iterator()).thenReturn(mockIterator3);

        Elements mockLinks2 = mock(Elements.class);
        when(mockLinks2.size()).thenReturn(2);
        when(mockLinks2.get(0)).thenReturn(link1);
        when(mockLinks2.get(1)).thenReturn(link2);
        Iterator<Element> mockIterator4 = mock(Iterator.class);
        when(mockIterator4.hasNext()).thenReturn(true, true, false);
        when(mockIterator4.next()).thenReturn(link1, link2);
        when(mockLinks2.iterator()).thenReturn(mockIterator4);

        Document mockDocument1 = mock(Document.class);
        when(mockDocument1.select("h1, h2, h3, h4, h5, h6")).thenReturn(mockElements1);
        when(mockDocument1.select("a[href]")).thenReturn(mockLinks1);

        Document mockDocument2 = mock(Document.class);
        when(mockDocument2.select("h1, h2, h3, h4, h5, h6")).thenReturn(mockElements2);
        when(mockDocument2.select("a[href]")).thenReturn(mockLinks2);

        doReturn(mockDocument1).when(crawler).GetWebsiteContent("https://www.example.com");
        doReturn(mockDocument2).when(crawler).GetWebsiteContent("https://www.example.at");

        crawler.Crawl(arguments);

        verify(mdWriter, times(11)).AppendFile(anyString(), anyInt());
    }

    private Element createMockHeader(Tag tag, String text) {
        Element mockHeader = mock(Element.class);
        when(mockHeader.tag()).thenReturn(tag);
        when(mockHeader.text()).thenReturn(text);
        return mockHeader;
    }

    private Element createMockLink(String href, String text) {
        Element mockLink = mock(Element.class);
        when(mockLink.attr("abs:href")).thenReturn(href);
        when(mockLink.text()).thenReturn(text);
        return mockLink;
    }
}