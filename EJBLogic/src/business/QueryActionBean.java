package business;

import data.DataManager;
import net.sf.json.JSONArray;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

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

    /**
     * 返回符合条件的Order的json数组字符串，
     * @return
     */
    public String QueryOrder(String filter)
    {

        return "[]";
    }


    public String productList(String filter)
    {
        JSONArray jsonArray = JSONArray.fromObject(dataManager.getProductList());
        return jsonArray.toString();
    }

}
