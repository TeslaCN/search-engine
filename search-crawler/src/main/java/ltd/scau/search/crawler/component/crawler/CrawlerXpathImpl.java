package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.commons.PageOption;
import ltd.scau.search.commons.entity.PageStructure;
import ltd.scau.search.commons.service.PageStructureRepository;
import ltd.scau.search.crawler.entity.CrawledPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于数据库配置Xpath解析页面
 *
 * @author Weijie Wu
 */
@Component
public class CrawlerXpathImpl implements Crawler {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Charset defaultCharset;

    @Autowired
    private PageStructureRepository pageStructureRepository;

    @Override
    public CrawledPage crawl(URI uri) throws IOException, DocumentException {

        PageStructure pageStructure = pageStructureRepository.findLongestMatchByHostAndPathRegexPathPattern(uri);

        if (pageStructure == null) {
            return null;
        }

        if (pageStructure.getOptions() == PageOption.IGNORE_CONTENT) {
            return CrawledPage.newPage(uri).when(new Date()).build();
        }

        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);

        StatusLine statusLine = response.getStatusLine();
        int code = statusLine.getStatusCode();

        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, defaultCharset);

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
