package com.jin.mvc.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author wu.jinqing
 * @date 2021年06月18日
 */
public class Test2 {

    public static String drawImage(String sourceBase64, String mergeBase64)
    {
        try {
            byte[] sourceBytes = Base64.getDecoder().decode(sourceBase64);
            byte[] mergeBytes = Base64.getDecoder().decode(mergeBase64);
            ByteArrayInputStream sourceInputStream = new ByteArrayInputStream(sourceBytes);
            ByteArrayInputStream mergeInputStream = new ByteArrayInputStream(mergeBytes);



            BufferedImage sourceImage = ImageIO.read(sourceInputStream);
            BufferedImage mergeImage = ImageIO.read(mergeInputStream);

            Graphics2D graphics2D = sourceImage.createGraphics();

            graphics2D.drawImage(mergeImage, 0, 0, mergeImage.getWidth(), mergeImage.getHeight(), null);
            graphics2D.drawString("aaa", 0 , 0);


            graphics2D.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(sourceImage, "png", outputStream);

            String targetBase64 = new String(Base64.getEncoder().encode(outputStream.toByteArray()), "UTF-8");

            return targetBase64;
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return "";

    }



    public static void main(String[] args) throws Exception {

        String sourceString = "";

        String mergeString = "";
        String target = drawImage(sourceString, mergeString);

        System.out.println(target);
    }
}
