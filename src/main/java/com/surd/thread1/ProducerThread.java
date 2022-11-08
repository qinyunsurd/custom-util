package com.surd.thread1;

/**
 * @author admin
 * @date 生产者
 */
public class ProducerThread extends Thread {
    private Product p;

    public ProducerThread(Product p) {
        this.p = p;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
                if (i%2==0){
                    p.setProduct("费列罗","巧克力");
                }else {
                    p.setProduct("哈尔滨","啤酒");
                }
        }
    }


}
