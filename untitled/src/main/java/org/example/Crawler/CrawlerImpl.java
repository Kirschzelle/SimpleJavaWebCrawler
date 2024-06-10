package org.example.Crawler;

import org.example.JsoupDocFetcher.JsoupDocFetcher;
import org.example.MDWriter.MDWriter;
import org.example.Structs.CrawlArguments;
import org.example.Structs.Heading;
import org.example.Structs.URL;
import org.example.Translator.Translator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrawlerImpl implements Crawler {
    private final Translator translator;
    private final MDWriter mdwriter;
    private final JsoupDocFetcher fetcher;

    public CrawlerImpl(Translator translator, MDWriter writer, JsoupDocFetcher fetcher) {
        this.translator = translator;
        this.mdwriter = writer;
        this.fetcher = fetcher;
    }

    @Override
    public void crawl(CrawlArguments arguments) throws IllegalStateException {
        List<URL> URLsToCrawl = convertCrawlArgumentsToStartingURLs(arguments);
        while (!URLsToCrawl.isEmpty()) {
            URL url = URLsToCrawl.remove(0);
            if (url.level() >= arguments.depth()) {
                addExceededDepthToCrawlResult(url);
                continue;
            }
            try {
                Document content = fetcher.getWebsiteContent(url.value());
                addWebsiteToCrawlResult(url, translateHeadings(getDocumentHeadings(content), arguments.targetLanguage()));
                for (String newUrl : getDocumentUrlsAsStrings(content)) {
                    URLsToCrawl.add(0, new URL(newUrl, url.level() + 1));
                }
            } catch (IOException e) {
                addFailedWebsiteCrawlToResult(url);
            }
        }
    }


    private List<Heading> getDocumentHeadings(Document document) {
        List<Heading> headings = new ArrayList<>();
        for (Element element : document.select("h1, h2, h3, h4, h5, h6")) {
            headings.add(new Heading(element.tag(), element.text()));
        }
        return headings;
    }

    private List<String> getDocumentUrlsAsStrings(Document document) {
        List<String> urls = new ArrayList<>();
        for (Element linkElement : document.select("a[href]")) {
            urls.add(linkElement.attr("abs:href"));
        }
        urls.sort(Collections.reverseOrder());
        return urls;
    }

    private List<Heading> translateHeadings(List<Heading> headings, String language) {
        List<Heading> translatedHeadings = new ArrayList<>();
        for (Heading heading : headings) {
            translatedHeadings.add(new Heading(heading.level(), translator.translate(heading.value(), language)));
        }
        return translatedHeadings;
    }

    private List<URL> convertCrawlArgumentsToStartingURLs(CrawlArguments arguments) {
        List<URL> urls = new ArrayList<>();
        for (String domain : arguments.topLevelDomains()) {
            urls.add(new URL(arguments.url() + domain, 0));
        }
        return urls;
    }

    private void addFailedWebsiteCrawlToResult(URL url) throws IllegalStateException {
        mdwriter.appendFile("\n###FAILED TO LOAD WEBSITE:\n" + url.value(), url.level());
    }

    private void addWebsiteToCrawlResult(URL url, List<Heading> results) throws IllegalStateException {
        mdwriter.appendFile("\nSuccessfully scraped website: " + url.value(), url.level());
        for (Heading heading : results) {
            if (heading.level() == Tag.valueOf("h1")) {
                mdwriter.appendFile("\n# H1:" + heading.value(), url.level());
            } else if (heading.level() == Tag.valueOf("h2")) {
                mdwriter.appendFile("\n## H2:" + heading.value(), url.level());
            } else if (heading.level() == Tag.valueOf("h3")) {
                mdwriter.appendFile("\n### H3:" + heading.value(), url.level());
            } else if (heading.level() == Tag.valueOf("h4")) {
                mdwriter.appendFile("\n#### H4:" + heading.value(), url.level());
            } else if (heading.level() == Tag.valueOf("h5")) {
                mdwriter.appendFile("\n##### H5:" + heading.value(), url.level());
            } else {
                mdwriter.appendFile("\n###### H6:" + heading.value(), url.level());
            }
        }
    }

    private void addExceededDepthToCrawlResult(URL url) {
        mdwriter.appendFile("\n###FOUND URL BUT EXCEEDED CRAWLING DEPTH:" + url.value(), url.level());
    }
}
