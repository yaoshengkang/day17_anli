package web;

import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "delUserServlet", value = "/delUserServlet")
public class DelUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String id=request.getParameter("id");
        //调用service删除
        UserService service=new UserServiceImpl();
        service.deleteUser(id);

        //跳转到UserListServlet 重定向
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");

    }
}
