package web;

import domain.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
//已用FindUserByPageServlet代替
@WebServlet(name = "userListServlet", value = "/userListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用UserService完成查询
        UserService service=new UserServiceImpl();
        List<User> users = service.findAll();
        //将list存入request域
        request.setAttribute("users",users);
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }
}
