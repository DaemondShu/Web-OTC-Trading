package business;

import data.DataManager;
import entity.Order;
import entity.Product;
import entity.Trade;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;


/**
 * Created by monkey_d_asce on 16-5-29.
 */
@Stateless(name = "QueryActionEJB")
public class QueryActionBean implements QueryAction
{
    @EJB
    DataManager dataManager;

    public QueryActionBean()
    {
    }

    @Override
    public String orderList(String filter)
    {
        JSONObject filterJson = JSONObject.fromObject(filter);
        JSONArray jsonArray = JSONArray.fromObject(dataManager.superQuery(Order.class,filterJson.entrySet()));
        return jsonArray.toString();
    }


    @Override
    public String productList(String filter)
    {


        JSONObject filterJson = JSONObject.fromObject(filter);
        List<Product> products = dataManager.superQuery(Product.class,filterJson.entrySet());

        JSONArray jsonArray = new JSONArray();

        //添加市场信息
        for (Product product: products )
        {
            JSONObject temp = JSONObject.fromObject(product);
            Double marketPrice = dataManager.getMarketPrice(product.getId());
            String BrokerCompany = dataManager.getUser(product.getBrokerId()).getCompany();
            temp.put("marketPrice",marketPrice == null ? -1 : marketPrice);
            temp.put("broker",BrokerCompany);
            jsonArray.add(temp);
        }

        return jsonArray.toString();
    }

    @Override
    public String tradeList(String filter)
    {
        JSONObject filterJson = JSONObject.fromObject(filter);

        List<Trade> trades = dataManager.superQuery(Trade.class,filterJson.entrySet());
        JSONArray jsonArray = new JSONArray();

        for (Trade trade : trades)
        {
            JSONObject temp = JSONObject.fromObject(trade);

            temp.put("seller",dataManager.getUser(trade.getSellerId()).getCompany());
            temp.put("buyer",dataManager.getUser(trade.getBuyerId()).getCompany());
            temp.put("product",dataManager.getProduct(trade.getProductId()).getName());
            temp.put("broker",dataManager.getUser(trade.getBrokerId()).getCompany());
            jsonArray.add(temp);
        }



        return jsonArray.toString();
    }

    @Override
    public String userList(String filter)
    {
        JSONObject filterJson = JSONObject.fromObject(filter);
        List<User> userList = dataManager.superQuery(User.class,filterJson.entrySet());
        JsonConfig exclude = new JsonConfig();
        exclude.setExcludes(new String[] {"password"}); //密码不能外泄
        return JSONArray.fromObject(userList,exclude).toString();
    }


}
