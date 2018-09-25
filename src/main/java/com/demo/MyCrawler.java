package com.demo;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.demo.Down.downLoadFromUrl;

/**
 * @Author Lijc
 * @Description
 * @Date 2018/9/25-11:40
 **/
public class MyCrawler extends WebCrawler {

    private static final Pattern filters = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mpeg|ram|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static final Pattern imgPatterns = Pattern.compile(".*(\\.(mp4))$");

    private static File storageFolder;
    private static String[] crawlDomains;

    public static void configure(String[] domain, String storageFolderName) {
        crawlDomains = domain;

        storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean b =!filters.matcher(href).matches()&&href.contains("pearvideo");
        return b;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        //判断page是否为真正的网页
        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();//页面html内容

            Document doc = Jsoup.parse(html);//采用jsoup解析html，这个大家不会可以简单搜一下

            Pattern pattern = Pattern.compile("http://.+.mp4");
            Matcher matcher = pattern.matcher(doc.toString());


            while (matcher.find()) {
                // get a unique name for storing this image
                String extension = matcher.group().substring(matcher.group().lastIndexOf('.'));
                String hashedName = UUID.randomUUID() + extension;
                try {
                    downLoadFromUrl(matcher.group(),hashedName,"/data");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(matcher.group());
            }


        }else {
            System.out.println("！！！！！！！！！！！！！！！！！");
        }
    }
}
