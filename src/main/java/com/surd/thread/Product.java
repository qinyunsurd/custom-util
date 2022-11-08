package com.surd.thread;

/**
 * @author admin
 * @date 商品
 */
public class Product {
    private String brand;
    private String name;
    private boolean flag;

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

    public synchronized void setProduct(String brand, String name) {
        if (flag) { // true代表有商品，等着消费者消费
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        notify();
    }

    public synchronized void getProduct() {
        if (!flag){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("消费了：" + this.getBrand() + this.getName());
        flag = false;
        notify();
    }
}
