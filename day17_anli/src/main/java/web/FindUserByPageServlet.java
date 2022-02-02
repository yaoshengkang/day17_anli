package web;

import domain.PageBean;
import domain.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "findUserByPageServlet", value = "/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //获取参数
        String currentPage = request.getParameter("currentPage");   //当前页码
        String rows = request.getParameter("rows");                 //每页显示条数

        if(currentPage==null||"".equals(currentPage)){
            currentPage="1";
        }

        if(rows==null||"".equals(rows)){
            rows="5";
        }
        //查询条件
        Map<String, String[]> condition = request.getParameterMap();

        //调用service查询
        UserService service=new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage,rows,condition);

        //将PageBean存入request
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);    //将查询条件存入
        //转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }
}
