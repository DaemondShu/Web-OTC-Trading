package servlet;

import business.QueryAction;
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
 * Created by monkey_d_asce on 16-5-30.
 */
@WebServlet("/Query")
public class Query extends HttpServlet
{
    private static final String ACTION = "action";
    private static final String FILTER = "filter";
    private static final int ERRORCODE = 520;

    private HttpServletRequest request;

    @EJB
    QueryAction queryAction;

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
           // String filterStr =

            switch (val(ACTION))
            {
                case "productList":
                    writer.print(queryAction.productList(val(FILTER)));
                    break;
                case "orderList":
                    writer.print(queryAction.orderList(val(FILTER)));
                    break;
                case "tradeList":
                    writer.print(queryAction.tradeList(val(FILTER)));
                    break;
                case "userList":
                    writer.print(queryAction.userList(val(FILTER)));
                    break;

                //为什么没有用户查询？ 因为查询是直接返回一个列表，会包含数据的所有数据，会出事

                default:
                    throw new LogicException("invalid action");
            }
        }
        catch (LogicException e)
        {
            String t = e.getMessage();
            System.out.println(t);
            response.setStatus(ERRORCODE);
            writer.write(t);
        }
        catch (Exception e)  //内部未知错误,一般是数据库操作错误
        {
            response.setStatus(500);
            writer.write("operation failed");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}
