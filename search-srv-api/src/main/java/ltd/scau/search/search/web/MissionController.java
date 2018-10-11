package ltd.scau.search.search.web;

import ltd.scau.search.commons.entity.Mission;
import ltd.scau.search.commons.entity.ResponseData;
import ltd.scau.search.commons.mq.producer.MissionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * @author Weijie Wu
 */
@RestController
public class MissionController {

    @Autowired
    private MissionProducer missionProducer;

    @PostMapping("/mission")
    public ResponseData postMission(List<String> uri) {
        try {
            missionProducer.submit(Mission.create(uri.stream().map(URI::create).toArray(URI[]::new)));
            return ResponseData.aData().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.aData().succeed(false).message(e.getMessage()).build();
        }
    }
}
