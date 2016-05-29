package business;

/**
 * Created by monkey_d_asce on 16-5-29.
 */
public interface QueryAction
{
    /**
     * 返回符合条件的Order的json数组字符串，
     * @return
     */
    String QueryOrder(String filter);

    String productList(String filter);
}
