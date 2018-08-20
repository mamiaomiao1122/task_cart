package com.yuanling.shopping;
import java.util.List;
/**
 * 店铺
 */
public interface Store {
    /**
     * 把所指定的商品和相应的数量添加到购物车中。*
     * @param  name String 商品名称，商品名称是商品的唯一标识(id)
     * @param  quantity int 商品数量
     */
    void addItemToCart(String name, int quantity);
    /**
     * 添加所指定的商品和其价格添加到此店铺
     *
     * @param name String 商品名称，商品名称是商品的唯一标识(id)
     * @param price long 商品价格，以'分'为单位 100 = 1元， 10 = 1角
     */
    void addItemToStore(String name, long price);
    /**
     * 得到所有在购物车中的商品。
     *
     */
    List<LineItem> getCartItems();
    /**
     * 得到购物车中所有商品的总价值
     */
    long getCartTotal();
}

