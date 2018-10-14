package ltd.scau.search.search.web;

import ltd.scau.search.commons.entity.Mission;
import ltd.scau.search.commons.entity.ResponseData;
import ltd.scau.search.commons.entity.mq.Message;
import ltd.scau.search.commons.mq.producer.MissionProducer;
import ltd.scau.search.commons.service.mq.MessageService;
import org.apache.rocketmq.common.protocol.body.ConsumeMessageDirectlyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author Weijie Wu
 */
@RestController
public class MissionController {

    @Autowired
    private MissionProducer missionProducer;

    @Autowired
    private MessageService messageService;

    @Value("${rocketmq.consumerGroup}")
    private String consumerGroup;

    @Value("${rocketmq.topic}")
    private String topic;

    @PostMapping("/mission")
    public ResponseData postMission(String uris) {
        try {
            missionProducer.submit(Mission.create(Arrays.stream(uris.split("\\|")).map(URI::create).toArray(URI[]::new)));
            return ResponseData.aData().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.aData().succeed(false).message(e.getMessage()).build();
        }
    }

    @DeleteMapping("/mission")
    public ResponseData deleteMession(String msgId) {
        try {
            ConsumeMessageDirectlyResult result = messageService.consumeMessageDirectly(topic, msgId, consumerGroup, null);
            return ResponseData.aData().data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.aData().succeed(false).message(e.getMessage()).build();
        }
    }

    @GetMapping("/missions")
    public ResponseData missions(@RequestParam long begin, @RequestParam long end, String pattern) {
        try {
            List<Message> messages = messageService.queryMessageByTopic(topic, begin, end, pattern);
            return ResponseData.aData().data(messages).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.aData().succeed(false).message(e.getMessage()).build();
        }
    }
}
