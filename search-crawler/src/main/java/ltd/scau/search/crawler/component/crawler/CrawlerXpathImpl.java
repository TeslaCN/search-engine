package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.commons.PageOption;
import ltd.scau.search.commons.entity.PageStructure;
import ltd.scau.search.commons.service.PageStructureRepository;
import ltd.scau.search.crawler.entity.CrawledPage;
import ltd.scau.search.crawler.util.HtmlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于数据库配置Xpath解析页面
 *
 * @author Weijie Wu
 */
@Component
public class CrawlerXpathImpl implements Crawler {

    private static final Log logger = LogFactory.getLog(CrawlerXpathImpl.class);

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Charset defaultCharset;

    @Autowired
    private PageStructureRepository pageStructureRepository;

    private static final String[] ACCEPT = {
            ContentType.TEXT_HTML.getMimeType(),
            ContentType.TEXT_PLAIN.getMimeType(),
            ContentType.TEXT_XML.getMimeType(),
            ContentType.DEFAULT_TEXT.getMimeType(),
            ContentType.APPLICATION_XHTML_XML.getMimeType(),
    };

    private static final Set<String> acceptMimeType = new HashSet<>(Arrays.asList(ACCEPT));

    @Override
    public CrawledPage crawl(URI uri) throws IOException, DocumentException {

        PageStructure pageStructure = pageStructureRepository.findLongestMatchByHostAndPathRegexPathPattern(uri);

        if (pageStructure == null && (pageStructure = pageStructureRepository.findRegexMatchHostAndPathPattern(uri)) == null) {
            logger.debug("Page Structure Not Found: " + uri);
            return null;
        }

        if (pageStructure.getOptions() == PageOption.IGNORE_CONTENT) {
            logger.debug("Ignore: " + uri);
            return CrawledPage.newPage(uri).when(new Date()).build();
        }

        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);

        StatusLine statusLine = response.getStatusLine();
        int code = statusLine.getStatusCode();

        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.get(entity);
        String mimeType = contentType.getMimeType();

        if (acceptMimeType.stream().noneMatch(mimeType::equalsIgnoreCase)) {
            return CrawledPage.newPage(uri).code(code).when(new Date()).build();
        }

        String html = null;
        byte[] bytes = EntityUtils.toByteArray(entity);
        html = new String(bytes, defaultCharset);

        Charset contentTypeCharset = null;
        if ((contentTypeCharset = contentType.getCharset()) == null && (contentTypeCharset = HtmlHelper.getCharsetInMeta(html)) == null) {
            logger.debug("Unknown Charset of >> " + uri);
            throw new UnsupportedCharsetException(uri.toString());
        }

        if (!defaultCharset.equals(contentTypeCharset)) {
            html = new String(bytes, contentTypeCharset);
        }

        Document document = Jsoup.parse(html, uri.toString());
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);

        String xhtml = document.html();

        org.dom4j.Document xmlDocument = DocumentHelper.parseText(xhtml);

        List<Node> titleNodes = xmlDocument.selectNodes(pageStructure.getTitleXpath());
        List<Node> contentNodes = xmlDocument.selectNodes(pageStructure.getContentXpath());
        List<Node> hrefNodes = xmlDocument.selectNodes(pageStructure.getHrefXpath());
        List<Node> tagNodes = xmlDocument.selectNodes(pageStructure.getTagXpath());

        Set<String> hrefs = hrefNodes.stream().map(Node::getStringValue).filter(s -> s.charAt(0) != '#').collect(Collectors.toSet());

        List<String> titles = titleNodes.stream().map(Node::getStringValue).filter(t -> t != null && !t.trim().isEmpty()).collect(Collectors.toList());
        List<String> contents = contentNodes.stream().map(Node::getStringValue).filter(t -> t != null && !t.trim().isEmpty()).collect(Collectors.toList());
        Set<String> tags = tagNodes.stream().map(Node::getStringValue).filter(t -> t != null && !t.trim().isEmpty()).collect(Collectors.toSet());

        return CrawledPage.newPage(uri).code(code).title(titles).tags(tags).html(html).content(contents).hrefs(hrefs).when(new Date()).build();
    }

}
