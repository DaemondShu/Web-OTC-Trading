package data;

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

    @Resource
    private SessionContext context;


    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;

    public DataManagerBean()
    {

    }

    public User getUser(String name)
    {
        Query query = entityManager.createQuery("select u from User u where u.username=:username");
        query.setParameter("username",name);
        return (User)query.getSingleResult();
    }


    public void SaveUser(User user) throws Exception
    {
        //throw new EJBException("tian na");
        entityManager.persist(user);
    }


}
