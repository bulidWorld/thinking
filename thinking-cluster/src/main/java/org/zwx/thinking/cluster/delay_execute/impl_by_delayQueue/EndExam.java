package org.zwx.thinking.cluster.delay_execute.impl_by_delayQueue;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class EndExam implements IDelayedWrap{

    public DelayQueue<IDelayedWrap> students;

    public Teacher teacher;

    private CountDownLatch countDownLatch;

    private Thread teacherThread;

    public EndExam(DelayQueue<IDelayedWrap> students, CountDownLatch countDownLatch, Teacher teacher, Thread thread) {
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacher = teacher;
        this.teacherThread = thread;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {
        return unit.convert(this.teacher.workTime, TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        EndExam other = (EndExam) o;
        if ( o instanceof EndExam){
            if (this.teacher.equals(o)) {
                return 0;
            }
            return this.teacher.workTime - other.teacher.workTime > 0 ? 1 : -1;
        }else{
            return -1;
        }

    }

    @Autowired
    public void run() {


        this.teacherThread.interrupt();
        Iterator<IDelayedWrap> iterator = students.iterator();

        while (iterator.hasNext()) {
            IDelayedWrap wrap = iterator.next();
            wrap.run();
        }

        System.out.println(this.teacher.name + ":" + "强制交卷");
        countDownLatch.countDown();
    }
}
