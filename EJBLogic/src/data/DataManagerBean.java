package data;

import entity.Order;
import entity.Product;
import entity.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by monkey_d_asce on 16-5-27.
 */

//@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "DataManagerEJB")
public class DataManagerBean implements DataManager
{
    public enum Status
    {
        TODO, DOING, DONE , CANCEL;
    }

    public enum Type
    {
        MARKET, LIMIT, STOP, CANCEL;
    }

    public enum Rule
    {
        ADMIN, BROKER, TRADER
    }


    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;
    @EJB
    private SuperJPA dao;

    public DataManagerBean()
    {

    }

    @Override
    public User getUser(String name)
    {
        try
        {
            javax.persistence.Query query = entityManager.createQuery("select u from User u where u.username=:username");
            query.setParameter("username",name);
            return (User)query.getSingleResult();
        }
        catch (Exception e)
        {
            return null;
        }

    }


    public void saveUser(User user)
    {
        //throw new EJBException("tian na");

        entityManager.persist(user);
    }

    public List<Product> getProductList()
    {
        SuperQuery query = SuperQuery.forClass(Product.class,entityManager);
        List<Product> result = dao.query(query);
        return result;
    }

    public List<Product> getProduct(Map<String,Object> filter)
    {
        SuperQuery query = SuperQuery.forClass(Product.class,entityManager);

        for (Map.Entry<String,Object> item : filter.entrySet() )
        {
            query.eq(item.getKey(),item.getValue());
        }

        List<Product> result = dao.query(query);

        return result;
    }



    public void saveOrder(Order order)
    {
        dao.insert(order);
        //entityManager.persist(order);
    }

    public void getOrderList()
    {

    }

    public void getMarketPrice(int productId)
    {

    }


    public void flush()
    {
        entityManager.flush();
    }

}
