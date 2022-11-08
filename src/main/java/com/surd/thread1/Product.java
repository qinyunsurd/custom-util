package com.surd.thread1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @date 商品
 */
public class Product {
    private String brand;
    private String name;
    private boolean flag;
    //声明一个锁
    private Lock lock = new ReentrantLock();
    //生产者等待队列
    Condition produceCondition = lock.newCondition();
    //消费者等待队列
    Condition consumeCondition = lock.newCondition();
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public  void setProduct(String brand, String name) {
        lock.lock();
        try {
            if (flag) { // true代表有商品，等着消费者消费
                //生产者阻塞
               produceCondition.await();
            }
            //如果是FALSE 就生产
            this.setBrand(brand);
            this.setName(name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("生产了：" + this.getBrand() + this.getName());
            flag = true;
            consumeCondition.signal();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public  void getProduct() {
        lock.lock();
        try {
            if (!flag){
                try {
                    //wait();
                    consumeCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("消费了：" + this.getBrand() + this.getName());
            flag = false;
            //notify();
            produceCondition.signal();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
