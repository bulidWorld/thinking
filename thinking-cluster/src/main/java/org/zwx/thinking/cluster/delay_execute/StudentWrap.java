package org.zwx.thinking.cluster.delay_execute;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class StudentWrap implements IDelayedWrap{

    public Student student;

    private CountDownLatch countDownLatch;

    public StudentWrap(Student student, CountDownLatch countDownLatch) {
        this.student = student;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {

        return unit.convert(this.student.workTime, TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        StudentWrap other = (StudentWrap) o;
        if ( o instanceof StudentWrap){
            if (this.student.equals(o)) {
                return 0;
            }
            return this.student.workTime - other.student.workTime > 0 ? 1 : -1;
        }else{
            return -1;
        }

    }

    @Autowired
    public void run() {
        System.out.println(this.student.name + ":" + "交卷");
        countDownLatch.countDown();
    }
}
