package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
       //强制转换
        HttpServletRequest request= (HttpServletRequest) req;

        //获取资源请求路径
        String uri=request.getRequestURI();
        //判断是否包含登录相关资源路径,注意排除css/js/图片/验证码等资源
        if(uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")){
            //包含，放行
            chain.doFilter(req, resp);
        }else{
            //不包含，验证用户是否已经登陆
            Object admin = request.getSession().getAttribute("admin");
            if(admin!=null){
                //登录了，放行
                chain.doFilter(req, resp);
            }else{
                //没有登陆，跳转到登录页面
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }




    }
}
