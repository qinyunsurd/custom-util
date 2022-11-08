package com.surd.common;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @date
 */
public class Test {
    public static void main(String[] args) {
        Test1 t1 = new Test1();
        Thread t = new Thread(t1);
        Thread tt = new Thread(t1);
        AtomicInteger a = new AtomicInteger(1);
        AtomicInteger b= new AtomicInteger(1);
        System.out.println(a.incrementAndGet());
        System.out.println(a.get());
        System.out.println(b.getAndIncrement());
        System.out.println(b.get());
    }

}

class Test1 implements Runnable{
    int tick=10;
    Lock lock = new ReentrantLock();
    AtomicInteger i = new AtomicInteger(0);
    @Override
    public void run() {
        for (; i.get() < 10; i.incrementAndGet()) {
            System.out.println(Thread.currentThread().getName()+"out"+i);

            lock.lock();
                if (tick > 0)
                System.out.println(tick--);
            lock.unlock();
        }
    }
}