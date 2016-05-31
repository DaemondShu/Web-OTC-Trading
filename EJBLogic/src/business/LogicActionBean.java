package business;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.*;
import data.DataManager;
import entity.Order;
import entity.Product;
import entity.Trade;
import net.sf.json.JSONObject;
import sun.security.util.Resources_it;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            /*
            if (order.equals("LIMIT"))
                order.setCondition();*/
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
     * TODO 事务管理
     * @return  完成了几个交易
     */
    @Override
    public int doTrade(final int productId)
    {
//        try
//        {
            int result = 0;
            //枚举所有物品,得到目前某个商品的市场价，按商品做循环

            Map<String, Object> pfilter = new HashMap<>();

            if (productId >= 0)
                pfilter.put("id", productId);
            List<Product> products = dataManager.superQuery(Product.class, pfilter);

            for (Product product : products)
            {

                //Sell 'TOD' -> 'DOING'
                int count = updateTodoOrder(product.getId())+updateDoingOrder(product.getId());

                while (count >0)
                {
                    count = updateTodoOrder(product.getId())+updateDoingOrder(product.getId());
                }
                //do Trade in DOING

                //


                //先要拿到所有todo的订单，看一下状态
            }

            return result;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return -1;
//        }
    }

    /**
     * 更新TODO状态的卖订单到doing
     * @return 更新的订单条目数
     */
    private int updateTodoOrder(final int productId)
    {
        int count= 0;
        final Map<String, Object> ofilter = new HashMap<String, Object>(){{
            this.put("productId",productId);
            this.put("status","TODO");
            this.put("isSell",1);
        }};

        double marketPrice = dataManager.getMarketPrice(productId);

        List<Order> orders = dataManager.superQuery(Order.class,ofilter);
        for ( Order order: orders )
        {
            if (order.getType().equals("MARKET"))
            {
                order.setStatus("DOING");
                count++;
            }
            else
            {
                double[] conditions = getMinMax(order.getCondition());
                //System.out.println(order.getPrice());
                if (conditions[0] <= marketPrice  && marketPrice <= conditions[1])
                {
                    order.setStatus("DOING");
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 更新DOING状态的买卖订单之间到建立交易
     * @return 更新的订单条目数
     */

    private int updateDoingOrder(final int productId)
    {
        int count= 0;
        final Map<String, Object> buyfilter = new HashMap<String, Object>(){{
            this.put("productId",productId);
            this.put("status","DOING");
            this.put("isSell",0);
            this.put("sort",Arrays.asList("time","asc"));  //按时间顺序
        }};

        final Map<String, Object> sellfilter = new HashMap<String, Object>(){{
            this.put("productId",productId);
            this.put("status","DOING");
            this.put("isSell",1);
            this.put("sort",Arrays.asList("price","asc"));  //按时间顺序
        }};

        List<Order> buyOrders = dataManager.superQuery(Order.class,buyfilter);
        List<Order> sellOrders = dataManager.superQuery(Order.class,sellfilter);


        for (Order buyOrder : buyOrders)
        {
            for (Order sellOrder : sellOrders )
                if (sellOrder.getSurplusVol() > 0)   //TODO 优化
                {

                    double[] buyConditions = getMinMax(buyOrder.getCondition());
                    if (buyConditions[0] <= sellOrder.getPrice()  && sellOrder.getPrice() <= buyConditions[1])
                    {
                        if (createTrade(sellOrder,buyOrder))
                            count++;
                    }
                    if (buyOrder.getSurplusVol() <=0 ) break;
                }
        }

        return count;
    }


    /**
     * 在两笔订单之间发起交易
     * @param sellOrder
     * @param buyOrder
     * @return
     */
    private boolean createTrade(Order sellOrder, Order buyOrder)
    {
        try
        {
            Integer sellVol = sellOrder.getSurplusVol();
            Integer buyVol = buyOrder.getSurplusVol();
            int quantity = min(sellVol,buyVol);
            //sellVol -= quantity;
            //sellOrder -= quantity;
            sellOrder.setSurplusVol(sellVol - quantity);
            buyOrder.setSurplusVol(buyVol-quantity);

            dataManager.updateOrder(sellOrder);
            dataManager.updateOrder(buyOrder);

            Trade trade = new Trade();
            trade.init(buyOrder.getId(),sellOrder.getId(),quantity);
           // trade.setId(6);
            dataManager.saveTrade(trade);

            System.out.println("4455556666");

            //dataManager.flush();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private int min(int a, int b)
    {
        return a < b ? a : b;
    }

    private double[] getMinMax(String s)
    {
        String[] resultStr = new String[]{"",""};
        int iter = 0;
        int len = s.length();
        int k=0;


        while (iter < len && k < 2)
        {
            char c = s.charAt(iter);
            if (c == ',')
            {
                k++;
            }
            else resultStr[k] += c;
            iter ++;
        }

        double[] result = new double[]{0,2147483647};

        if (resultStr[0].length()>0)
            result[0] = Double.parseDouble(resultStr[0]);
        if (resultStr[1].length()>0)
            result[1] = Double.parseDouble(resultStr[1]);

        return result;
    }

}
