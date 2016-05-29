package data;

import entity.Order;
import entity.Product;
import entity.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

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

    List<Product> getProductList();

    List<Product> getProduct(Map<String,Object> filter);
}
