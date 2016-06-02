package servlet;

import business.UserAction;
import exception.LogicException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by monkey_d_asce on 16-5-28.
 */
@WebServlet("/User")
public class User extends HttpServlet
{
    private static final String ACTION = "action";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USERDATA = "userData";
    private static final int ERRORCODE = 520;

    @EJB(name = "UserAction")
    private UserAction userAction;

    private HttpServletRequest request;


    private String val(String key)
    {
        String temp = request.getParameter(key);
        return (temp == null) ? "" : temp;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.request = request;
        //request.getSession(true);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        try
        {
            switch (val(ACTION))
            {
                case "login":
                    String role = userAction.login(val(USERNAME),val(PASSWORD));
                    if (role == "")
                        throw new LogicException("login failed");
                    writer.write(role);
                    break;

                case "register":
                    String error = userAction.register(val(USERDATA));
                    if (error != null)
                        throw new LogicException(error);
                    break;
                default:
                    throw new LogicException("invalid action");
            }
        }

        catch (LogicException e)  //逻辑错误
        {
            String t = e.getMessage();
            System.out.println(t);
            response.setStatus(ERRORCODE);
            writer.write(t);
        }

        catch (Exception e)  //内部未知错误
        {

            response.setStatus(500);
            writer.write("operation failed");
        }

        writer.flush();
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
        
    }
}
