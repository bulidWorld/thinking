package org.zwx.thinking.cluster.delay_execute.impl_by_redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Iterator;
import java.util.Set;

@Component
public class TaskExecutor implements ApplicationListener {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String TASK_PRFiX = "task:";


    //注意事务性
    public Task getTask() {

        String key = TASK_PRFiX;
        Task task = null;
        //获取时间的分数应该满足某个条件
        Set taskSet = redisTemplate.opsForZSet().rangeByScore(TASK_PRFiX, Instant.MIN.getEpochSecond(), Instant.now().getEpochSecond(), 0, 1);

        Iterator it = taskSet.iterator();
        while (it.hasNext()) {
            task = (Task) it.next();
        }

        redisTemplate.opsForZSet().remove(key, task);
        return task;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        while (true) {
            Task task = getTask();
            if (task == null) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                consumeTask(task);
            }
        }



    }

    //消费任务
    public void consumeTask(Task task) {
        task.doTask();
    }

    //in second
    public void addTask(Task task, long delay) {
        redisTemplate.opsForZSet().add(TASK_PRFiX, task, Instant.now().getEpochSecond() + delay);
    }
}
