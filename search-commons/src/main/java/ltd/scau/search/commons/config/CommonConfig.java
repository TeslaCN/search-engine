package ltd.scau.search.commons.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.charset.Charset;

/**
 * @author Weijie Wu
 */
@Configuration
@PropertySource("classpath:commons.properties")
public class CommonConfig {

    @Value("${rocketmq.producerGroup}")
    private String producerGroup;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumerGroup}")
    private String consumerGroup;

    @Value("${rocketmq.consume.threadMin}")
    private Integer threadMin;

    @Value("${rocketmq.consume.threadMax}")
    private Integer threadMax;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.subexpression}")
    private String subExpression;

    @Value("${default.charset}")
    private String defaultCharset;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        return producer;
    }

    @Bean
    public HttpClient httpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(new BasicCookieStore()).setRedirectStrategy(new LaxRedirectStrategy()).build();
        return httpClient;
    }

    @Bean
    public Charset defaultCharset() {
        return Charset.forName(defaultCharset);
    }
}
