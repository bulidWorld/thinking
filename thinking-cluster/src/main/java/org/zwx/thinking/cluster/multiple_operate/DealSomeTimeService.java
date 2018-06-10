package org.zwx.thinking.cluster.multiple_operate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zwx.thinking.cluster.common.ConfigUtils;
import org.zwx.thinking.cluster.common.RedisService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class DealSomeTimeService {

    private static final String AFTER_SOME_TIME = "AFTER_SOME_TIME";


    private ConfigUtils configUtils;

    private RedisService redisService;

    @Autowired
    private Consumer consumer;


    @Autowired
    private Producer producer;

    private void proceduceBody(String body) {
        String someTime = configUtils.getConfig(AFTER_SOME_TIME);
        long afterSecond = Long.valueOf(someTime);

        Instant now = Instant.now();
        long willSec = now.getEpochSecond() + afterSecond;


        String key = AFTER_SOME_TIME + ":" + willSec;

        producer.produce(key, body);
    }


    private void consumeBody() {
        String key = AFTER_SOME_TIME + ":" + Instant.now().getEpochSecond();
        List<String> lst = redisService.lstGetAndRemove(key);

        lst.forEach(body ->{
            consumer.consumeInMutltiThread(body, (str)->{System.out.print(str); return Optional.empty();});
        });
    }

}
