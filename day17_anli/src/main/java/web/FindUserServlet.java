package web;

import domain.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "findUserServlet", value = "/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String id=request.getParameter("id");
        //调用Service查询
        UserService service=new UserServiceImpl();
        User user=service.findUserById(id);

        //将user存入request
        request.setAttribute("user",user);
        //转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);


    }
}
