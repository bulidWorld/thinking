package org.zwx.thinking.cluster.multiple_operate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zwx.thinking.cluster.common.RedisService;
import org.zwx.thinking.cluster.common.ThreadUtil;

@Component
public class Consumer {

    private static final String prefix = "DEFAULT_PREFIX";

    @Autowired
    private RedisService redisService;

    void consume(String key, Action action) {
        String body = redisService.strGet(key);
        action.actBody(body);
    }

    void consumeInMutltiThread(String body, Action action) {
        ThreadUtil.multiThreadDeal(new RunAction(action, body));
    }
}
