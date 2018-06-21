package org.zwx.thinking.cluster.delay_execute.impl_by_redis;

import java.io.Serializable;

public class Task implements Serializable {
    public Long id;

    public String content;

    public void doTask() {
        System.out.println("do Task:" + content);
    }
}
