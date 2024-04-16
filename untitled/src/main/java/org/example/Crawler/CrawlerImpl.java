package org.example.Crawler;

import org.example.MDWriter.MDWriter;
import org.example.MDWriter.MDWriterImpl;
import org.example.Structs.CrawlArguments;
import org.example.Structs.Heading;
import org.example.Translator.Translator;
import org.example.Translator.TranslatorImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlerImpl implements Crawler {
    private Translator translator = new TranslatorImpl();
    private MDWriter mdwriter = new MDWriterImpl();

    @Override
    public void Crawl(CrawlArguments arguments) throws IllegalStateException {
        List<String> URLsToCrawl = ConvertCrawlArgumentsToStartingURLs(arguments);
        while (!URLsToCrawl.isEmpty()) {
            String url = URLsToCrawl.remove(0);
            try {
                Document content = GetWebsiteContent(url);
                AddWebsiteToCrawlResult(url, TranslateHeadings(GetDocumentHeadings(content), arguments.targetLanguage()));
                URLsToCrawl.addAll(GetDocumentUrls(content));
            } catch (IOException e) {
                AddFailedWebsiteCrawlToResult(url);
            }
        }
    }

    private Document GetWebsiteContent(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private List<Heading> GetDocumentHeadings(Document document) {
        List<Heading> headings = new ArrayList<>();
        for (Element element : document.select("h1, h2, h3, h4, h5, h6")) {
            headings.add(new Heading(element.tag(), element.text()));
        }
        return headings;
    }

    private List<String> GetDocumentUrls(Document document) {
        List<String> urls = new ArrayList<>();
        for (Element linkElement : document.select("a[href]")) {
            urls.add(linkElement.attr("abs:href"));
        }
        return urls;
    }

    private List<Heading> TranslateHeadings(List<Heading> headings, String language) {
        List<Heading> translatedHeadings = new ArrayList<>();
        for (Heading heading : headings) {
            translatedHeadings.add(new Heading(heading.level(), translator.Translate(heading.value(), language)));
        }
        return translatedHeadings;
    }

    private List<String> ConvertCrawlArgumentsToStartingURLs(CrawlArguments arguments) {
        List<String> urls = new ArrayList<>();
        for (String domain : arguments.topLevelDomains()) {
            urls.add(arguments.url() + domain);
        }
        return urls;
    }

    private void AddFailedWebsiteCrawlToResult(String url) throws IllegalStateException {
        mdwriter.AppendFile("###FAILED TO LOAD WEBSITE:\n" + url + "\n");
    }

    private void AddWebsiteToCrawlResult(String url, List<Heading> results) throws IllegalStateException {
        mdwriter.AppendFile("                             aHHHHb.\n" + "                            /`HHHHHHb\n" + "                            > `dHHHHH\n" + "                           /  dHHHHHHb\n" + "                           ; /HHHHHHHH\n" + "                          .-\"\"FHHHHHHH\n" + "                         (    JHHHHHHH.\n" + "                          \\    FHHHHHHb\\\n" + "                         / \\   JHHHHHHH \\\n" + "                       .'   \\   FHHHHHH  \\\n" + "                       `._,  \\   \\HHHHF   Y\n" + "                          |   >   \\HHF`Y  |\n" + "                          | .'   .'Y   |  |\n" + "                          .'   .': |   |  j\n" + "                        .'  _.'  : \\   |  F\n" + "                      .' .-;       .`-.| j\n" + "                     .  ;  :        .  | /\n" + "                     :  (  |......___)/_|)\n" + "                      ```  '\"\"\"\"-._   _.-'  >scritch<\n" + "                           |       `.'-'_'\n" + "                           |  ` - '/  -'|     >scracth<\n" + "                           |      /     |\n" + "                           |     /|     |\n" + "                           |     ||Krogg|dp");
        mdwriter.AppendFile("Successfully scraped website: " + url + "\n");
        for (Heading heading : results) {
            if (heading.level() == Tag.valueOf("h1")) {
                mdwriter.AppendFile("# H1:" + heading.value() + "\n");
            } else if (heading.level() == Tag.valueOf("h2")) {
                mdwriter.AppendFile("## H2:" + heading.value() + "\n");
            } else if (heading.level() == Tag.valueOf("h3")) {
                mdwriter.AppendFile("### H3:" + heading.value() + "\n");
            } else if (heading.level() == Tag.valueOf("h4")) {
                mdwriter.AppendFile("#### H4:" + heading.value() + "\n");
            } else if (heading.level() == Tag.valueOf("h5")) {
                mdwriter.AppendFile("##### H5:" + heading.value() + "\n");
            } else {
                mdwriter.AppendFile("###### H6:" + heading.value() + "\n");
            }
        }
    }
}
