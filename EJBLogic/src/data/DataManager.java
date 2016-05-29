package data;

import entity.User;

import javax.ejb.Local;

/**
 * Created by monkey_d_asce on 16-5-28.
 */

@Local
public interface DataManager
{
    User getUser(String name);

    void SaveUser(User user) throws Exception;
}
