package business;

import data.DataManager;
import entity.Order;
import entity.Product;
import entity.Trade;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;


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
        JSONArray jsonArray = JSONArray.fromObject(dataManager.superQuery(Product.class,filterJson.entrySet()));
        return jsonArray.toString();
    }

    @Override
    public String tradeList(String filter)
    {
        JSONObject filterJson = JSONObject.fromObject(filter);
        JSONArray jsonArray = JSONArray.fromObject(dataManager.superQuery(Trade.class,filterJson.entrySet()));
        return jsonArray.toString();
    }

    @Override
    public String userList(String filter)
    {
        JSONObject filterJson = JSONObject.fromObject(filter);
        JSONArray jsonArray = JSONArray.fromObject(dataManager.superQuery(Trade.class,filterJson.entrySet()));
        return jsonArray.toString();
    }


}
