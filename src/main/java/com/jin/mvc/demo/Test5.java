package com.jin.mvc.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author wu.jinqing
 * @date 2021年07月01日
 */
public class Test5 {
    public static final String FONT_NAME = "static/msyh.ttc";



    public static void main(String[] args) {
        try (
                InputStream is2 = Test4.class.getClassLoader().getResourceAsStream(FONT_NAME);

        ){
            Font font = Font.createFont(Font.TRUETYPE_FONT, is2);
            font = font.deriveFont(Font.BOLD, 50);
            System.out.println(font.getName());
            System.out.println(font.getSize());

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
