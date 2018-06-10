package org.zwx.thinking.cluster.distribute_lock;

import java.util.concurrent.CountDownLatch;

public class DistributeLock {


    private CountDownLatch limitLantch = new CountDownLatch(1);

    private LockResourceApi lockResourceApi;


    /**
     * 锁住资源
     * @param resource
     * @return
     */
    public boolean lock(Object resource){

        while (true) {
            try {
                boolean bres = lockResourceApi.lockResource(resource);
                if (bres) {
                    //加锁成功的情况下重新定义countDown
                    limitLantch = new CountDownLatch(1);
                    return true;
                } else {
                    limitLantch.await();
                }
            } catch (Exception e) {
                try {
                    limitLantch.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }

    }


    /**
     *
     * @param resource
     */
    public void releaseLock(Object resource) {
        try {
            boolean resp = lockResourceApi.releaseResource(resource);
            if (resp) {
                System.out.println("释放锁资源成功");
            }
        } catch (Exception e) {
            System.out.println("释放锁资源失败，等待锁的超时机制自动失效");
        }finally {
            limitLantch.countDown();
        }
    }
}
