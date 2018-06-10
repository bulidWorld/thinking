package org.zwx.thinking.cluster.multiple_operate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zwx.thinking.cluster.common.RedisService;

@Component
public class Producer {

    @Autowired
    RedisService redisService;

    void produce(String key, String body) {
        redisService.lstAdd(key, body);
    }

}
