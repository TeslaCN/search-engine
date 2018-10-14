package ltd.scau.search.commons.service.mq;

import ltd.scau.search.commons.entity.mq.Message;
import org.apache.rocketmq.common.protocol.body.ConsumeMessageDirectlyResult;

import java.util.List;

/**
 * @author Weijie Wu
 */
public interface MessageService {

    default List<Message> queryMessageByTopic(final String topic, final long begin, final long end) {
        return queryMessageByTopic(topic, begin, end, null);
    }

    List<Message> queryMessageByTopic(final String topic, final long begin, final long end, final String bodyPattern);

    ConsumeMessageDirectlyResult consumeMessageDirectly(String topic, String msgId, String consumerGroup, String clientId);
}
