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
        List<User> userList = dataManager.superQuery(User.class,filterJson.entrySet());
        JsonConfig exclude = new JsonConfig();
        exclude.setExcludes(new String[] {"password"}); //密码不能外泄
        return JSONArray.fromObject(userList,exclude).toString();
    }


}
