package com.jin.mvc.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author wu.jinqing
 * @date 2021年07月01日
 */
public class Test4 {
    public static final String FILE_NAME = "static/p1.png";
    public static final String FONT_NAME = "static/msyh.ttc";

    public static void main(String[] args) {
        try (
                InputStream is1 = Test4.class.getClassLoader().getResourceAsStream(FILE_NAME);
                InputStream is2 = Test4.class.getClassLoader().getResourceAsStream(FONT_NAME);

        ){
            BufferedImage sourceImage = ImageIO.read(is1);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is2);
            font = font.deriveFont(Font.BOLD, 50);
//            Font font = new Font("微软黑雅", Font.BOLD, 50);

            Graphics2D graphics2D = sourceImage.createGraphics();
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(font);
            graphics2D.drawString("张三aad", 100 , 100);

            graphics2D.dispose();

            FileOutputStream fos = new FileOutputStream("C:\\Users\\wujinqing\\Desktop\\p2.png");
            ImageIO.write(sourceImage, "png", fos);

            fos.flush();
            fos.close();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
