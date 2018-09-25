package com.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class JsonP {
    public static void main(String[] args) throws IOException {
        SocketAddress sa = new InetSocketAddress("127.0.0.1", 1088);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, sa);
        Document doc = Jsoup
                .connect("https://avgle.com/embed/0bca4c03854cd635cbba")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1")
                .timeout(50000)
                .proxy(proxy)
                .get();

        System.out.println(doc.toString());

    }
}
