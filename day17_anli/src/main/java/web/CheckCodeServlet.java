package web;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "checkCodeServlet", value = "/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width=100;
        int height=50;
        //创建验证码对象
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        //设置背景色
        Graphics g = image.getGraphics();   //画笔对象
        g.setColor(Color.GRAY);             //设置画笔颜色
        g.fillRect(0,0,width,height);

        //画边框
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,height-1);

        //生成随机角标
        String str="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";

        StringBuilder sb=new StringBuilder();

        //设置字体
        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体",Font.BOLD,24));

        Random ran=new Random();
        for(int i=1;i<=4;i++){
            int index=ran.nextInt(str.length());
            //获取字符
            char ch=str.charAt(index);
            sb.append(ch);

            g.drawString(ch+"",width/5*i,height*2/3);
        }

        String checkCode_session=sb.toString();
        request.getSession().setAttribute("checkCode_session",checkCode_session);

        //画干扰线
        g.setColor(Color.GREEN);
        //随机生成坐标点
        for(int i=0;i<10;i++){
            int x1=ran.nextInt(width);
            int x2=ran.nextInt(width);
            int y1=ran.nextInt(height);
            int y2=ran.nextInt(height);
            g.drawLine(x1,x2,y1,y2);
        }

        //将图片输出到页面展示
        ImageIO.write(image,"jpg",response.getOutputStream());

    }
}
