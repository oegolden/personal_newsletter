package com.ogolden.personal_newsletter.Utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import io.github.cdimascio.dotenv.Dotenv;

public class WebScrapper {

    public static class ScrapedDocStruct {
        protected String title;
        protected List<String> metadata;
        protected List<String> image_links;
        protected String datetime;
        protected String body;

        @Override
        public String toString() {
            return "Title: " + title + "\n" +
            "Datetime: " + datetime + "\n" +
            "Metadata:\n" + String.join("\n", metadata.stream().map(m -> "  " + m).toList()) + "\n" +
            "Images:\n" + String.join("\n", image_links.stream().map(i -> "  " + i).toList()) + "\n" +
            "Body: " + body;
        }
    }

    public static List<String> SITES = List.of("HACKER_NEWS");

    private static Document createDoc(String site) throws IOException{
        Document doc;
        try {
            doc = Jsoup.connect(site).get();
        } catch(Exception e){
            throw new IOException(e);
        }
        return doc;
    }

    //site here comes from environment variables is HACKER_NEWS
    public static List<String> getLinks(String site) throws IOException {
        Dotenv dotenv = Dotenv.load();
        //actual https link of the site i.e. hackernews.com
        String siteLink = dotenv.get(site);
        Document doc = createDoc(siteLink);
        Elements links = doc.select("tr.athing.submission");
        return links.stream().map(element -> Objects.requireNonNull(element.selectFirst("span.titleline > a")).attr("href")).collect(Collectors.toList());
    }

    public static ScrapedDocStruct getDocumentText(String link) throws IOException{
        Document doc = createDoc(link);

            ScrapedDocStruct result = new ScrapedDocStruct();

            // TITLE
            result.title = firstNonEmpty(
                    doc.select("meta[property=og:title]").attr("content"),
                    doc.title(),
                    doc.select("h1").first() != null ? Objects.requireNonNull(doc.select("h1").first()).text() : ""
            );

            // METADATA
            List<String> metadata = new ArrayList<>();
            addIfNotEmpty(metadata, "author", firstNonEmpty(
                    doc.select("meta[name=author]").attr("content"),
                    doc.select("meta[property=article:author]").attr("content"),
                    doc.select("[rel=author]").text(),
                    doc.select("[itemprop=author]").text(),
                    doc.select("[class*=author]").text()
            ));
            addIfNotEmpty(metadata, "description", firstNonEmpty(
                    doc.select("meta[name=description]").attr("content"),
                    doc.select("meta[property=og:description]").attr("content")
            ));
            addIfNotEmpty(metadata, "publisher",
                    doc.select("meta[property=og:site_name]").attr("content")
            );
            addIfNotEmpty(metadata, "keywords",
                    doc.select("meta[name=keywords]").attr("content")
            );
            result.metadata = metadata;

            // IMAGE LINKS
            List<String> images = new ArrayList<>();
            String ogImage = doc.select("meta[property=og:image]").attr("content");
            if (!ogImage.isEmpty()) images.add(ogImage);
            Elements imgs = doc.select("article img, main img, [class*=content] img");
            if (imgs.isEmpty()) imgs = doc.select("img");
            for (Element img : imgs) {
                String src = img.absUrl("src");
                if (src.isEmpty()) src = img.absUrl("data-src");
                if (!src.isEmpty() && !images.contains(src)) images.add(src);
            }
            result.image_links = images;

            // DATETIME
            result.datetime = firstNonEmpty(
                    doc.select("meta[property=article:published_time]").attr("content"),
                    doc.select("meta[name=date]").attr("content"),
                    doc.select("time[datetime]").attr("datetime"),
                    doc.select("[itemprop=datePublished]").attr("content")
            );

            // BODY
            Element bodyEl = firstElement(doc,
                    "article", "main", "[role=main]",
                    "[class*=article-body]", "[class*=post-content]", "[class*=entry-content]"
            );
            if (bodyEl == null) bodyEl = doc.body();
            bodyEl.select("script, style, nav, header, footer, aside, iframe").remove();
            result.body = bodyEl.text();

            return result;
    }

    private static String firstNonEmpty(String... candidates) {
        for (String s : candidates) {
            if (s != null && !s.trim().isEmpty()) return s.trim();
        }
        return "";
    }

    private static void addIfNotEmpty(List<String> list, String key, String value) {
        if (value != null && !value.trim().isEmpty()) list.add(key + ": " + value.trim());
    }

    private static Element firstElement(Document doc, String... queries) {
        for (String q : queries) {
            Element el = doc.selectFirst(q);
            if (el != null) return el;
        }
        return null;
    }

    public static void main( String[] args) throws IOException {
        String firstLink = getLinks(SITES.get(0)).get(0);
        System.out.println(getDocumentText(firstLink));
    }


}
