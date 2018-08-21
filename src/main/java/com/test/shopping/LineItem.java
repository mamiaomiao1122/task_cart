package com.test.shopping;

public interface LineItem {
    /**
     * 商品名称，是商品的唯一标识，可理解为id
     */
    String getName();
    /**
     * 商品的数量
     */
    int getQuantity();

}
