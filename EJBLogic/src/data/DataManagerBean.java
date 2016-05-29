package data;

import entity.Order;
import entity.User;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public DataManagerBean()
    {

    }

    @Override
    public User getUser(String name)
    {
        try
        {
            Query query = entityManager.createQuery("select u from User u where u.username=:username");
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

    public void saveOrder(Order order)
    {
        entityManager.persist(order);
    }

    public void flush()
    {
        entityManager.flush();
    }


}
