package com.test.shopping;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

/**
 * 该类用于描述购物车的商品信息
 */
public class LineItemImpl implements LineItem {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品数量
     */
    private int quantity;
    /**
     * 成交时候的价格(因为价格不一样，所以得保存)
     */
    private List<PriceAndNumber> salePriceAndNumber;
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<PriceAndNumber> getSalePriceAndNumber() {
        return salePriceAndNumber;
    }

    public void setSalePriceAndNumber(List<PriceAndNumber> salePriceAndNumber) {
        this.salePriceAndNumber = salePriceAndNumber;
    }

    class PriceAndNumber{
        private int number;
        private Long price;
        PriceAndNumber(int number,Long price){
            this.number=number;
            this.price=price;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public int getNumber() {
            return number;
        }

        public Long getPrice() {
            return price;
        }
    }
}

