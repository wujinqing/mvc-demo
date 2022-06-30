package com.jin.mvc.demo.txt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wu.jinqing
 * @date 2021年07月19日
 */
public class TextUtil3 {
    public static void main(String[] args) throws IOException {
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TextUtil3.class.getClassLoader()
                        .getResourceAsStream("data3.txt"))));
        ) {
            String buf = "";

            while(null != (buf = br.readLine()))
            {
                buf = buf.trim();

                if(buf.length() > 0)
                {

                    String comment = "";
                    String fieldName = "";

                    int idx = buf.indexOf("\t");

                    comment = buf;
                    fieldName = buf.substring(0, idx);

                    StringBuilder sb = new StringBuilder();

                    sb.append("/*").append("\n");
                    sb.append("").append(comment).append("\n");
                    sb.append("*/").append("\n");
                    sb.append("private ").append("String").append(" ").append(fieldName).append(";").append("\n");

                    System.out.println(sb.toString());




                }
            }
        }

    }
}
