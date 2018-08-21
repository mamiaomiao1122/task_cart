package com.test.shopping;

import java.util.HashMap;

/**
 * 该类主要是描述相关的商品信息
 */
public class StoreGood extends LineItemImpl {
    /**
     *  商品的价格，每次加入购物车的时候使用最新的价格
     */
    private HashMap<Long,PriceAttribute> price;
    /**
     * 当前价格
     */
    private Long currentPrice;
    public HashMap<Long,PriceAttribute> getPrice() {
        return price;
    }

    public void setPrice(HashMap<Long,PriceAttribute> price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StoreGood{" +
                "price=" + price +
                '}';
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }
}
