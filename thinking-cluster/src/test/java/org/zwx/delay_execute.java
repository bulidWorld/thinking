package org.zwx;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zwx.thinking.cluster.delay_execute.Application;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class delay_execute {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testConnect() {
        redisTemplate.opsForValue().set("test_key", "testVal", 10, TimeUnit.SECONDS);

        Assert.assertEquals(redisTemplate.opsForValue().get("test_key"), "testVal");
    }





}
