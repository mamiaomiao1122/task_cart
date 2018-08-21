package com.test.shopping;

import java.util.Date;

/**
 * 该类主要是用于描述在该价格下的销量
 */
public class PriceAttribute {
    /**
     * 价格
     */
    private long price;
    /**
     * 在当前价格下的销量（方便卖家做参考）
     */
    private int salesQuantity;
    /**
     * 上一次跟新的时间（方便卖家做参考）
     */
    Date startTime;
    /**
     * 当前更新的时间
     */
    Date currentTime;
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(int salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "PriceAttribute{" +
                "price=" + price +
                ", salesQuantity=" + salesQuantity +
                ", startTime=" + startTime +
                ", currentTime=" + currentTime +
                '}';
    }
}
