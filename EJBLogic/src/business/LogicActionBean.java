package business;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.*;
import data.DataManager;
import entity.Order;
import entity.Product;
import net.sf.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;
import java.util.List;

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


    @Override
    /**
     * 创建一个订单，成功返回null，失败返回错误信息
     * @param data
     * @return
     */

    public boolean createOrder(String data)
    {
        try
        {
            Order order = (Order) JSONObject.toBean(JSONObject.fromObject(data),Order.class);

            System.out.println(order.toString());
            order.init();
            dataManager.saveOrder(order);

            doTrade(order.getProductId());

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

    @Override
    public boolean cancelOrder(int orderId)
    {
        try
        {
            Order order = dataManager.getOrder(orderId);
            order.setStatus("CANCEL");
            dataManager.flush();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 检查交易
     * @return  完成了几个交易
     */
    @Override
    public int doTrade()
    {
        return doTrade(-1);
    }

    /**
     * 检查交易
     * @return  完成了几个交易
     */
    @Override
    public int doTrade(final int productId)
    {

        int result = 0;
        //枚举所有物品,得到目前某个商品的市场价，按商品做循环

        Map<String, Object> pfilter = new HashMap<>();
        Map<String, Object> ofilter = new HashMap<>();
        if (productId >= 0)
            pfilter.put("id", productId);
        List<Product> products = dataManager.superQuery(Product.class, pfilter);

        for (Product product : products)
        {
            System.out.println(product.toString());
            ofilter.put("productId",product.getId());

            //'TODO' -> DOING


            List<Order> orders = dataManager.superQuery(Order.class,ofilter);

            //


            //先要拿到所有todo的订单，看一下状态
        }

        return result;
    }

    /**
     * 得到目前的市场最低价，
     * @param productId 产品id
     * @return
     */
    public Double getMarketPrice(final int productId)
    {
        final Map<String, Object> ofilter = new HashMap<String, Object>(){{
            this.put("productId",productId);
            this.put("status","DOING");
            this.put("sort","price");
            this.put("isSell",1);
        }};

        List<Order> orders = dataManager.superQuery(Order.class,ofilter);

        for (Order order: orders )
        {
            return order.getPrice();
        }


        return null;
    }



   // private boolean checkTodo

}
