package org.zwx.thinking.cluster.distribute_lock;

/**
 * 添加与释放资源锁的api，具体实现可以用resids或者zookeeper
 */
public interface LockResourceApi {

    boolean lockResource(Object source) throws Exception;

    boolean releaseResource(Object source) throws Exception;
}
