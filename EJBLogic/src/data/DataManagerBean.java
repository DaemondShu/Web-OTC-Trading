package data;

import entity.Order;
import entity.Product;
import entity.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private void  filter2Query(Set<Map.Entry<String,Object>> filter,SuperQuery query)
    {
        for (Map.Entry<String,Object> item : filter)
        {
            query.eq(item.getKey(),item.getValue());
        }
    }

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


    public Order getOrder(int orderId)
    {
        return dao.get(Order.class,orderId);
    }

    @Override
    public void saveOrder(Order order)
    {
        dao.insert(order);
        //entityManager.persist(order);
    }

    @Override
    public void saveUser(User user)
    {
        //throw new EJBException("tian na");

        entityManager.persist(user);
    }

    public <E extends Serializable> List<E> superQuery(Class table)
    {
        SuperQuery query = SuperQuery.forClass(Product.class,entityManager);
        return dao.query(query);
    }

    public <E extends Serializable> List<E> superQuery(Class table, Map<String,Object> filter)
    {
        return superQuery(table,filter.entrySet());
    }

    public <E extends Serializable> List<E> superQuery(Class table, Set<Map.Entry<String,Object>> filter)
    {
        SuperQuery query = SuperQuery.forClass(Product.class,entityManager);
        filter2Query(filter,query);
        return dao.query(query);
    }


    public void flush()
    {
        entityManager.flush();
    }

}
