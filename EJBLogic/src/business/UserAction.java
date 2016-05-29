package business;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by monkey_d_asce on 16-5-28.
 */

@Local
public interface UserAction
{
    String login(String username, String password);


    /**
     * 注册检查，成功返回null，失败返回错误信息
     * @param userData
     * @return
     */
    String register(String userData);
}
