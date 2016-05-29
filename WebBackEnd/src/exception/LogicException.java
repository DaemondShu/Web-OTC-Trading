package exception;

/**
 * Created by monkey_d_asce on 16-5-28.
 */

/**
 * 代表逻辑EJB上的操作失败，而不是代码的错误
 */
public class LogicException extends Exception
{
    public LogicException(String msg)
    {
        super(msg);
    }

}
