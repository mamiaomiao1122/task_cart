package com.yuanling.shopping;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StoreImpl implements Store {
    /**
     * 商铺：使用HashMap,方便获取商品
     */
    public ConcurrentHashMap<String, StoreGood> storeMap;
    /**
     * 购物车
     */
    public ConcurrentHashMap<String, LineItemImpl> cartMap;

    public StoreImpl(ConcurrentHashMap<String, StoreGood> storeMap,
                     ConcurrentHashMap<String, LineItemImpl> cartMap) {
        this.storeMap = storeMap;
        this.cartMap = cartMap;
    }

    synchronized public void addItemToCart(String name, int quantity) {
        if (name == null || name.length() == 0 || quantity == 0) {
            System.out.println("输入的商品数量不对，请重新输入");
            return;
        }
        StoreGood storeGood = storeMap.get(name);
        if (storeGood == null) {
            System.out.println("该店铺还没有此类商品，请耐心等待添加");
            return;
        }
        if ((storeGood != null&&quantity > storeGood.getQuantity())||storeGood.getQuantity()<=0) {
            System.out.println("该店铺还没有充足商品，请耐心等待添加");
            return;
        }else {
            LineItemImpl good;
            if (!cartMap.containsKey(name)) {
                good = new LineItemImpl();
                good.setName(name);
                List<LineItemImpl.PriceAndNumber> list = new ArrayList<LineItemImpl.PriceAndNumber>();
                LineItemImpl.PriceAndNumber priceAndNumber =
                        good.new PriceAndNumber(quantity, storeGood.getCurrentPrice());
                list.add(priceAndNumber);
                good.setSalePriceAndNumber(list);
                good.setQuantity(quantity);
                cartMap.put(name, good);
                int number = storeGood.getQuantity() - quantity;
                storeGood.setQuantity(number);
            } else {
                good = cartMap.get(name);
                good.setQuantity(good.getQuantity() + quantity);
                List<LineItemImpl.PriceAndNumber> list = good.getSalePriceAndNumber();
                boolean flag = false;
                for (LineItemImpl.PriceAndNumber priceAndNumber : list) {
                    if (storeGood.getCurrentPrice().equals(priceAndNumber.getPrice()) ) {
                        priceAndNumber.setNumber(priceAndNumber.getNumber() + quantity);
                        flag = true;
                    }
                }
                if (!flag) {
                    LineItemImpl.PriceAndNumber priceAndNumber =
                            good.new PriceAndNumber(quantity, storeGood.getCurrentPrice());
                    list.add(priceAndNumber);
                }
                int number = storeGood.getQuantity() - quantity;
                storeGood.setQuantity(number);

            }
        }
    }

     public void addItemToStore(String name, long price) {
        if (name == null || name.length() == 0 || price < 0 || price > 1000) {
            System.out.println("输入的商品数量价格不对，请重新输入0-1000");
            return;
        }
        StoreGood storeGood = storeMap.get(name);
        //当前没有任何产品
        if (storeGood == null) {
            storeGood = new StoreGood();
            storeGood.setName(name);
            storeGood.setQuantity(1);
            HashMap<Long, PriceAttribute> priceMap = new HashMap<Long, PriceAttribute>();
            PriceAttribute priceAttribute = new PriceAttribute();
            priceAttribute.setPrice(price);
            priceAttribute.setSalesQuantity(0);
            Date date = new Date(System.currentTimeMillis());
            priceAttribute.setStartTime(date);
            priceAttribute.setCurrentTime(date);
            priceMap.put(price, priceAttribute);
            storeGood.setPrice(priceMap);
            storeGood.setCurrentPrice(price);
            storeMap.put(name, storeGood);
        } else {
            if (storeMap.get(name).getQuantity() + 1 > 20) {
                System.out.println("该商品已经达到上线，请稍后在进行添加");
            }
            storeGood = storeMap.get(name);
            HashMap<Long, PriceAttribute> priceMap = storeMap.get(name).getPrice();
            if (priceMap.get(price) == null) {
                PriceAttribute priceAttribute = new PriceAttribute();
                priceAttribute.setPrice(price);
                priceAttribute.setSalesQuantity(0);
                Date date = new Date(System.currentTimeMillis());
                priceAttribute.setStartTime(date);
                priceAttribute.setCurrentTime(date);
                priceMap.put(price, priceAttribute);
                storeGood.setPrice(priceMap);
            }
            storeGood.setQuantity((storeGood.getQuantity() + 1));
            storeGood.setCurrentPrice(price);
        }
    }

     public List<LineItem> getCartItems() {
        List<LineItem> lineItems = new ArrayList<LineItem>();
        for (String goodName : cartMap.keySet()) {
            LineItemImpl lineItem = new LineItemImpl();
            lineItem.setName(goodName);
            lineItem.setQuantity(cartMap.get(goodName).getQuantity());
            System.out.println("商品：" + goodName +
                    " 数量 " + cartMap.get(goodName).getQuantity());
        }
        return lineItems;
    }

    public long getCartTotal() {

        Long count = 0L;
        for (String goodName : cartMap.keySet()) {
            LineItemImpl item = cartMap.get(goodName);
            List<LineItemImpl.PriceAndNumber> list = item.getSalePriceAndNumber();
            for (LineItemImpl.PriceAndNumber price : list) {
                count = count + price.getNumber() * price.getPrice();
            }
        }
        return count;
    }

    /***
     * 主要获取当前商品获取价格销售情况
     * @param name 商品名称
     */
    public List<PriceAttribute> getSalesQuantity(String name) {
        if (name == null || name.length() == 0) {
            System.out.println("该商品名字输入错误");
            return null;
        }
        StoreGood storeGood = storeMap.get(name);
        if (storeGood == null) {
            System.out.println("该商品为空");
            return null;
        }
        List<PriceAttribute> attributeList = new ArrayList<PriceAttribute>();
        LineItemImpl good = cartMap.get(name);
        if (good == null) {
            //System.out.println("该商品没有任何销售记录");
            return null;
        } else {
            HashMap<Long, PriceAttribute> map = storeGood.getPrice();
            for (Long price : map.keySet()) {
                List<LineItemImpl.PriceAndNumber> list = good.getSalePriceAndNumber();
                for (LineItemImpl.PriceAndNumber catPrice : list) {
                    if (price.equals(catPrice.getPrice())) {
                        PriceAttribute priceAttribute = new PriceAttribute();
                        priceAttribute.setPrice(price);
                        priceAttribute.setSalesQuantity(catPrice.getNumber());
                        priceAttribute.setStartTime(map.get(price).getStartTime());
                        priceAttribute.setCurrentTime(new Date(System.currentTimeMillis()));
                        attributeList.add(priceAttribute);
                    }
                }

            }

        }
        return attributeList;
    }
}
