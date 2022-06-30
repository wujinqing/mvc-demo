package com.jin.mvc.demo;

import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.ChecksumMode;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.int2of5.ITF14Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * @author wu.jinqing
 * @date 2022年03月07日
 */
@Slf4j
public class Test22 {

    final static Base64.Encoder encoder = Base64.getEncoder();

    public static String generateBarcode(String msg)
    {
        //生成高度
        int height = 293;
        //生成宽度
        double width = UnitConv.in2mm(1.0f / height);

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();

        ){
            Code39Bean bean = new Code39Bean();
            bean.setModuleWidth(width);
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);

            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", height, BufferedImage.TYPE_BYTE_BINARY,false, 0);
            bean.generateBarcode(canvas, msg);
            canvas.finish();

            return encoder.encodeToString(out.toByteArray());

        }catch (Exception e)
        {
            log.error("条形码生成出错，e:", e);
        }

        return "";

    }

    public static void main(String[] args) {

        // 生成条形码数字
        String msg = "33301202204120351209756333000000";

        System.out.println(generateBarcode(msg));

    }


}
