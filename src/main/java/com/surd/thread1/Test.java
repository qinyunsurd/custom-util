package com.surd.thread1;

import lombok.SneakyThrows;


/**
 * @author admin
 * @date 生产者消费者通信
 */
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        Product p = new Product();
        ProducerThread pt = new ProducerThread(p);
        CustomerThread ct = new CustomerThread(p);
        pt.start();
        ct.start();

    }
}


