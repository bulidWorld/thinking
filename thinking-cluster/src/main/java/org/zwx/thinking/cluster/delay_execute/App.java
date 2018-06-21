package org.zwx.thinking.cluster.delay_execute;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;

public class App {

    public static void main(String[] args) {
        DelayQueue<IDelayedWrap> delayQueue = new DelayQueue();

        int n = 50;
        CountDownLatch countDownLatch = new CountDownLatch(n + 1);

        for (int i = 0; i < n; i++) {
            Student student = new Student();
            student.workTime = 30 * 60;
            student.name = "name" + i;
            student.cls = i + "";

            StudentWrap wrapper = new StudentWrap(student, countDownLatch);
            delayQueue.put(wrapper);

        }

        Teacher teacher = new Teacher();
        teacher.name = "mr main teacher";

        Thread teacherThread = new Thread( ()-> {
            IDelayedWrap wrapper = null;
            try {
                while (!Thread.interrupted()) {
                    wrapper = delayQueue.take();
                    wrapper.run();
                }
                wrapper = delayQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        EndExam endExam = new EndExam(delayQueue, countDownLatch, teacher, teacherThread);
        delayQueue.put(endExam);

        teacherThread.start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
