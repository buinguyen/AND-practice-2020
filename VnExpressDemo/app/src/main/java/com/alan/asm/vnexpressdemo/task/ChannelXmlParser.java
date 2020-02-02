package com.alan.asm.vnexpressdemo.task;

import android.util.Log;

import com.alan.asm.vnexpressdemo.model.Channel;
import com.alan.asm.vnexpressdemo.model.ImageType;
import com.alan.asm.vnexpressdemo.model.RssItem;
import com.alan.asm.vnexpressdemo.utils.DateTimeUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelXmlParser {

    private final static String TAG = "ChannelXmlParser";

    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String LINK = "link";
    private static final String TITLE = "title";
    private static final String ITEM = "item";
    private static final String URL = "url";

    public static Channel parseRss(String rssFeed) {
        Channel channel = null;

        List<RssItem> rssItemList = new ArrayList<>();

        InputStream stream = null;
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            XmlPullParser parser = parserFactory.newPullParser();

            stream = new URL(rssFeed).openConnection().getInputStream();
            parser.setInput(stream, null);

            int eventType = parser.getEventType();
            boolean isFinished = false;
            RssItem rssItem = null;

            while (eventType != XmlPullParser.END_DOCUMENT && !isFinished) {
                String tagName = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equalsIgnoreCase(CHANNEL)) {
                            channel = new Channel();
                        } else if (tagName.equalsIgnoreCase(ITEM)) {
                            rssItem = new RssItem();
                        } else if (rssItem != null) {
                            if (tagName.equalsIgnoreCase(LINK)) {
                                rssItem.setLink(parser.nextText());
                            } else if (tagName.equalsIgnoreCase(DESCRIPTION)) {
                                rssItem.setDescription(parser.nextText().trim());
                            } else if (tagName.equalsIgnoreCase(PUB_DATE)) {
                                rssItem.setPubDate(parser.nextText(), DateTimeUtil.DATE_TEXT_1);
                            } else if (tagName.equalsIgnoreCase(TITLE)) {
                                rssItem.setTitle(parser.nextText().trim());
                            }
                        } else {
                            if (tagName.equalsIgnoreCase(TITLE)) {
                                if (channel != null) channel.setTitle(parser.nextText().trim());
                            } else if (tagName.equalsIgnoreCase(URL)) {
                                if (channel != null) channel.setImageUrl(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equalsIgnoreCase(ITEM) && rssItem != null) {
                            getDataFromDescription(rssItem);
                            rssItemList.add(rssItem);
                            rssItem = null;
                        } else if (tagName.equalsIgnoreCase(CHANNEL)) {
                            isFinished = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.e(TAG, "Parsing errors!");
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Closing stream errors!");
                }
            }
        }
        if (channel != null) {
            channel.setRssItems(rssItemList);
        }
        return channel;
    }

    private static final Pattern pattern1 = Pattern.compile("data-original=\"(.*)\"");
    private static final Pattern pattern2 = Pattern.compile("src=\"(.*)\"");
    private static final String GIF_EXTENSION = ".gif";

    private static Boolean isGif(String urlStr) {
        return urlStr != null && urlStr.contains(GIF_EXTENSION);
    }

    public static void getDataFromDescription(RssItem rssItem) {
        if (rssItem == null) return;
        String description = rssItem.getDescription();
        if (description == null) return;

        Matcher matcher1 = pattern1.matcher(description);
        if (matcher1.find()) {
            setImageUrl(rssItem, matcher1.group(1));
        }
        Matcher matcher2 = pattern2.matcher(description);
        if (matcher2.find()) {
            setImageUrl(rssItem, matcher2.group(1));
        }
        String newDescription = description.replace("<![CDATA[", "")
                .replaceAll("<a(.*)</a>", "")
                .replace("]]>", "")
                .replace("</br>", "")
                .trim();
        rssItem.setDescription(newDescription);
    }

    private static void setImageUrl(RssItem rssItem, String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        if (url != null) {
            rssItem.setImageType(isGif(urlStr) ? ImageType.GIF : ImageType.OTHERS);
            rssItem.setImageUrl(urlStr);
        }
    }

    public static String buildWebImageContent(String imageUrl) {
        return "<!DOCTYPE html><html><body><img src=\""
                + imageUrl
                + "\" alt=\"Smileyface\" width=\"100%\" height=\"100%\"></body></html>";
    }
}