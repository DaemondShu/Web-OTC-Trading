package business;

/**
 * Created by monkey_d_asce on 16-5-29.
 */
public interface QueryAction
{

    String orderList(String filter);

    String productList(String filter);

    String tradeList(String filter);

    String userList(String filter);
}
