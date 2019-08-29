package com.liangzisong.security.core.validate.code;//
//
//
//

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                       //
////////////////////////////////////////////////////////////////////
//
//
//
//
//让需求简单一点，心灵就会更轻松一点；
//让外表简单一点，本色就会更接近一点；
//让过程简单一点，内涵就会更丰富一点；
//
//
//


import com.liangzisong.security.core.properties.ImageCodeProperties;
import com.liangzisong.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: ImageCodeGenerator
 * <p>
 * Description:
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/27 17:25
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {
        ImageCodeProperties imageCodeProperties = securityProperties.getValidateCodeProperties().getImageCodeProperties();

        int w = ServletRequestUtils.getIntParameter(request.getRequest(), "width", imageCodeProperties.getWidth());
        int h = ServletRequestUtils.getIntParameter(request.getRequest(), "height", imageCodeProperties.getHeight());
        int length = imageCodeProperties.getLength();
        Color bgColor = new Color(255, 255, 255);        //设置图片背景
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        /**
         * 写入字符（包括字符字体、颜色、字符种类）
         */
        BufferedImage img = this.createImage(bgColor,w,h);
        Graphics g = img.getGraphics();
        for (int i = 0; i < length; i++) {
            //设置字符
            String ch = this.randomChar(random);
            sb.append(ch);
            //设置画笔颜色
            g.setColor(this.randomColor(random));
            //设置画笔字体
            g.setFont(this.randomFont(random));
            //画出字符
            g.drawString(ch, w / 4 * i, h - 5);
        }
        drawLine(img,random,w,h);
        return new ImageCode(img,sb.toString(),imageCodeProperties.getExpireIn());
    }

    private void drawLine(BufferedImage image,Random random,int w,int h) {
        int num = 3;
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private Font randomFont(Random random) {
        /**
         * 设置 字体、字号、样式
         *
         * @return
         */
        String[] fontName = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑"};
        int[] fontSize = {28, 32, 35, 39, 45};
        int index = random.nextInt(fontName.length);
        //根据随机索引获取字体名
        String name = fontName[index];
        //设置字体样式
        int style = random.nextInt(4);
        index = random.nextInt(fontSize.length);
        //根据索引获取字体大小
        int size = fontSize[index];
        return new Font(name, style, size);
    }

    private BufferedImage createImage(Color bgColor,int w,int h) {
        /*
         * 1.创建图片
         * 2.设置背景色
         */
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //设置画笔颜色
        Graphics2D graphics2d = (Graphics2D) img.getGraphics();
        graphics2d.setColor(bgColor);
        graphics2d.fillRect(0, 0, w, h);
        //填充图片大小的矩形（设置背景色）
        return img;
    }
    private Color randomColor(Random random) {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private String randomChar(Random random) {
        String codes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRESTUVWXYZ0123456789";

        int index = random.nextInt(codes.length());
        //返回随机字符
        return codes.charAt(index) + "";
    }


    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}