package business;

import data.DataManager;
import entity.Order;
import net.sf.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
            order.setStatus("Done");
            dataManager.flush();
            //ok: add to cache
        }
        catch (Exception e)
        {
            //context.setRollbackOnly();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean cancelOrder()
    {
        return false;
    }


}
