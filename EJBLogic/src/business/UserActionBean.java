package business;

import data.DataManager;
import entity.User;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.ejb.*;

/**
 * Created by monkey_d_asce on 16-5-28.
 */

//@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "UserActionEJB")
public class UserActionBean implements UserAction
{
    @EJB
    DataManager dataManager;

    @Resource
    private SessionContext context;

    public UserActionBean()
    {
    }

    /**
     * 登陆检查，成功后返回角色名
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password)
    {
        String result = "";
        try
        {
            User user = dataManager.getUser(username);
            if (password.equals(user.getPassword()))
            {
                result = user.getRole();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 注册检查，成功返回null，失败返回错误信息
     * @param userData
     * @return
     */

//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String register(String userData)
    {
        try
        {
            User user= (User)JSONObject.toBean(JSONObject.fromObject(userData),User.class);
            if (!user.checkRole())
                return "Role can only be one of ['BROKER','TRADER']";
            dataManager.SaveUser(user);
            System.out.println(user.toString());
        }
        catch (Exception e)
        {
           //context.setRollbackOnly();
            e.printStackTrace();
            return "register failed with some reason";
        }
        return null;
    }


}

