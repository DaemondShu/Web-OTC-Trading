package data;

import entity.Order;
import entity.Product;
import entity.Trade;
import entity.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

/**
 * Created by monkey_d_asce on 16-5-27.
 */

//@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.CONTAINER)
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
            String key = item.getKey();
            switch (key)
            {
                case "sort" :
                    List<String> advCond = (List<String>) item.getValue();
                    query.addOrder(advCond.get(0),advCond.get(1));
                    break;
                case "maxNum":
                    query.setMaxNum((Integer) item.getValue());
                    break;
                case "startNum":
                    query.setStartNum((Integer) item.getValue());
                    break;
                default:
                    query.eq(key,item.getValue());
            }

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

    public User getUser(int id)
    {
        try
        {
            return (User)entityManager.find(User.class,id);
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
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //不加事务，外部连续persist就会因为id没有赋值而出问题,要保证一个事务内id唯一，而且必须事务新建
    public void saveProduct(Product product)
    {
        //entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.flush();
    }

    public Product getProduct(int productId)
    {
        return dao.get(Product.class,productId);
    }



    public void updateOrder(Order order)
    {
        dao.update(order);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveUser(User user)
    {
        //throw new EJBException("tian na");

        entityManager.persist(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTrade(Trade trade)
    {



        entityManager.persist(trade);


        entityManager.flush();

    }

    public Double getMarketPrice(final int productId)
    {
        final Map<String, Object> ofilter = new HashMap<String, Object>(){{
            this.put("productId",productId);
            this.put("status","DOING");
            this.put("sort", Arrays.asList("price","asc"));
            this.put("isSell",1);
            this.put("maxNum",1);
        }};


        List<Order> orders = this.superQuery(Order.class,ofilter);

        for (Order order: orders )
        {
            return order.getPrice();
        }
        return -1.0;
    }


    public <E extends Serializable> List<E> superQuery(Class table)
    {
        SuperQuery query = SuperQuery.forClass(table,entityManager);
        return dao.query(query);
    }

    public <E extends Serializable> List<E> superQuery(Class table, Map<String,Object> filter)
    {
        return superQuery(table,filter.entrySet());
    }

    public <E extends Serializable> List<E> superQuery(Class table, Set<Map.Entry<String,Object>> filter)
    {
        SuperQuery query = SuperQuery.forClass(table,entityManager);
        filter2Query(filter,query);
        return dao.query(query);
    }


    public void flush()
    {
        entityManager.flush();
    }

}
