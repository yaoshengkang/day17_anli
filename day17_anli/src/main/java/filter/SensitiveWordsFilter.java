package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    //敏感词汇.txt
    private List<String> list=new ArrayList<String >();

    public void init(FilterConfig config) throws ServletException {

        try {
            //获取文件真实路径
            ServletContext servletContext = config.getServletContext();
            //src下文件目录写法
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");

            //读取文件,默认为解码GBK，文件也要为GBK
            BufferedReader br=new BufferedReader(new FileReader(realPath));
            //将文件中的每一行数据添加到list中
            String line=null;
            while((line=br.readLine())!=null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //创建代理对象,增强getParameter方法
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParameter方法
                //判断方法名为getParameter
                if(method.getName().equals("getParameter")){
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(req, args);
                    if(value!=null){
                        for(String str:list){
                            if(value.contains(str)){
                                value=value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }

                //判断方法名为getParameterMap
                if(method.getName().equals("getParameterMap")){
                    //增强返回值
                    //获取返回值
                    Map<String, String[]> map = (Map<String, String[]>) method.invoke(req, args);
                    if(map.size()>0){
                        for(String str:list){
                            for(String keySet:map.keySet()){
                                if(map.get(keySet)[0].contains(str)){
                                    map.get(keySet)[0]=map.get(keySet)[0].replaceAll(str,"***");
                                }
                            }
                        }
                    }
                    return map;
                }

                //判断方法名为getParameterValue
                if(method.getName().equals("getParameterValues")){
                    //增强返回值
                    //获取返回值
                    String[] s = (String[]) method.invoke(req, args);
                    if(s.length>0){
                        for(String str:list){
                           for(int i=0;i<s.length;i++){
                               if(s[i].contains(str)){
                                   s[i]=s[i].replaceAll(str,"***");
                               }
                           }
                        }

                    }
                    return s;
                }

                return method.invoke(req,args);
            }
        });


        chain.doFilter(proxy_req, resp);


    }
}
