package com.jin.mvc.demo.txt;

import com.alibaba.fastjson.JSON;
import com.jin.mvc.demo.JavaDocHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author wu.jinqing
 * @date 2021年07月19日
 */
public class TextUtil {
    public static void main(String[] args) throws IOException {
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TextUtil.class.getClassLoader().getResourceAsStream("data.txt"))));
        ) {
            String buf = "";

            while(null != (buf = br.readLine()))
            {
                buf = buf.trim();

                if(buf.length() > 0)
                {
//                    System.out.println(buf);
                    String[] arr = buf.split("[\\t]");

                    String s0 = "";
                    String s1 = "";
                    String s2 = "";
                    String s3 = "";

                    if(arr.length > 0)
                    {
                        s0 = arr[0];
                    }

                    if(arr.length > 1)
                    {
                        s1= arr[1];
                    }

                    if(arr.length > 2)
                    {
                        s2 = arr[2];
                    }

                    if(arr.length > 3)
                    {
                        s3 = arr[3];
                    }

                    StringBuilder sb = new StringBuilder();

                    sb.append("/**").append("\n");
                    sb.append("* ").append(s2 + " " + s3).append("\n");
                    sb.append("**/").append("\n");
                    sb.append("private ").append(s1).append(" ").append(s0).append(";").append("\n");

                    System.out.println(sb.toString());
                }
            }
        }

    }
}
