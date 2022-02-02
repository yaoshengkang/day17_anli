package web;

import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "delSelectedServlet", value = "/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取所有uid
        String[] ids=request.getParameterValues("uid");
        //调用Service删除
        UserService service = new UserServiceImpl();
        service.delSelectedUser(ids);

        //跳转到UserListServlet 重定向
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");


    }
}
