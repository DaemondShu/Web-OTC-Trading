package business;

import data.DataManager;
import entity.Order;
import net.sf.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.xml.crypto.Data;

/**
 * 处理会修改数据的订单操作，比如交易
 * Created by monkey_d_asce on 16-5-29.
 */
@Stateless(name = "OrderActionEJB")
public class LogicActionBean implements LogicAction
{
    @EJB
    DataManager dataManager;

    public LogicActionBean()
    {
    }



    /**
     * 创建一个订单，成功返回null，失败返回错误信息
     * @param data
     * @return
     */
    @Override
    public boolean createOrder(String data)
    {
        try
        {
            Order order = (Order) JSONObject.toBean(JSONObject.fromObject(data),Order.class);
            System.out.println(order.toString());
            order.init();
            dataManager.saveOrder(order);


            dataManager.flush();
        }
        catch (Exception e)
        {
            //context.setRollbackOnly();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查交易
     * @return  完成了几个交易
     */
    @Override
    public int doTrade()
    {
        int result = 0;
        return result;
    }


    @Override
    public boolean cancelOrder()
    {
        return false;
    }


}
