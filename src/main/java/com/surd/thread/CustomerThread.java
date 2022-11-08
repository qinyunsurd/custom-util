package com.surd.thread;

/**
 * @author admin
 * @date
 */
public class CustomerThread extends Thread{
    private Product p;

    public CustomerThread(Product p) {
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
               p.getProduct();
        }
    }
}
