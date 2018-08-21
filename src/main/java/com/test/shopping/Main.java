package com.test.shopping;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *该类是主函数,主要实现添加商品和加入购物车的功能,可根据提示在控制台输入
 * (1)实现了Store接口的所有方法
 * (2)并且加了自己的方法 :获得该商品的历史销售记录,这样就可以在加入商品的时候可以查看
 * 当前商品的销售记录
 */
public class Main {
    ConcurrentHashMap<String, StoreGood> storeMap =
            new ConcurrentHashMap<String, StoreGood>();
    ConcurrentHashMap<String, LineItemImpl> cartMap =
            new ConcurrentHashMap<String, LineItemImpl>();
    StoreImpl store = new StoreImpl(storeMap, cartMap);
    static AtomicInteger atomicInteger = new AtomicInteger(100);

    public static void main(String[] args) {
        Main main = new Main();
        while (true){
            System.out.println("选择操作1,添加商品 2 加入购物车 3结束操作");
            Scanner scanner = new Scanner(System.in);
            int n=scanner.nextInt();
            switch (n){
                case 1:main.product();break;
                case 2:main.consum();break;
                default:return;
            }
        }

    }

    //添加商品
    void product() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int n = 0;
        System.out.println("请输入你要添加的商品总数");
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("请输入商品名称以及价格");
            String name = scanner.next();
            int price = scanner.nextInt();
            store.addItemToStore(name, price);
        }

    }

    void consum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要加入购物车的总数");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String name;
            int number;
            System.out.println("可以加入购物车的商品:");
            for (String goodName : storeMap.keySet()) {
                System.out.println("商品：" + goodName +
                        "当前价格 " + storeMap.get(goodName).getCurrentPrice() +
                        " 库存 " + storeMap.get(goodName).getQuantity());
            }
            System.out.println("请输入商品名称以及数量(1-2)");
            name = scanner.next();
            number = scanner.nextInt();
            store.addItemToCart(name, number);
            if (storeMap==null||storeMap.get(name) == null) {
                break;
            }
            System.out.println("购物车的商品为:");
            List<LineItem> lineItems = store.getCartItems();
            System.out.println("购物车的总价格为" + store.getCartTotal());
            List<PriceAttribute> list = store.getSalesQuantity(name);
            if (list != null && list.size() > 0) {
                System.out.println("该商品的历史销售记录如下：");
                for (PriceAttribute priceAttribute : list) {
                    System.out.println("时间：" + priceAttribute.getStartTime() + "---" +
                            priceAttribute.getCurrentTime() + " 价格：" +
                            priceAttribute.getPrice() +
                            " 销售额为 " + priceAttribute.getSalesQuantity());
                }

            }
        }
    }

}

