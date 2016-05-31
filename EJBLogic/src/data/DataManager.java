package data;

import entity.Order;
import entity.Product;
import entity.Trade;
import entity.User;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by monkey_d_asce on 16-5-28.
 */

@Local
public interface DataManager
{
    User getUser(String name);

    void saveUser(User user) throws Exception;

    void saveOrder(Order order);

    void flush();

//    List<Product> getProducts();
//
//    List<Product> getProducts(Map<String,Object> filter);
//
//    List<Product> getProducts(Set<Map.Entry<String,Object>> filter);
//
//    List<Order> getOrders();
//
//    List<Order> getOrders(Map<String,Object> filter);
//
//    List<Order> getOrders(Set<Map.Entry<String,Object>> filter);

    <E extends Serializable> List<E> superQuery(Class table, Set<Map.Entry<String,Object>> filter);

    <E extends Serializable> List<E> superQuery(Class table);

    <E extends Serializable> List<E> superQuery(Class table, Map<String,Object> filter);

    Order getOrder(int orderId);

    Double getMarketPrice(final int productId);

    void saveTrade(Trade trade);

    User getUser(int id);

    void updateOrder(Order order);
}
