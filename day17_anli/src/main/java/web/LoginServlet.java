package web;

import domain.Admin;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String checkCode = request.getParameter("verifycode");

        //获取生成的验证码
        HttpSession session=request.getSession();
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        //删除session中的验证码
        session.removeAttribute("checkCode_session");

        //判断验证码是否正确
        if(checkCode_session!=null && checkCode_session.equalsIgnoreCase(checkCode)){
            //验证码正确，判断用户名和密码是否一直，需连接数据库
            boolean flag=false;

            //使用BeanUtils解决封装用户输入的对象
            Map<String, String[]> map = request.getParameterMap();
            Admin loginUser=new Admin();
            try {
                //这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
                //需要commons-collections jar 包 3.2.2 不要4
                //要求:表单提交时的id值，必须与数据库的每一条属性一一对应
                BeanUtils.populate(loginUser,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            UserService service=new UserServiceImpl();
            Admin admin=service.login(loginUser);


            if(admin==null){
                //登录失败
               flag=false;
            }else{
                //登录成功
               flag=true;
            }


            if(flag){
                //登录成功
                //存储信息，用户信息
                //admin可用于判断是否登陆过
                session.setAttribute("admin",admin);
                //重定向到success.jsp
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }else{
                //登陆失败
                request.setAttribute("login_msg","用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }

        }else{
            //验证码不一致
            //提示信息到request
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);


        }


    }
}
