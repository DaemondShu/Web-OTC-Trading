package business;

/**
 * Created by monkey_d_asce on 16-5-29.
 */
public interface LogicAction
{
    boolean createOrder(String data);

    boolean cancelOrder();

    int doTrade();


    int doTrade(int productId);
}
