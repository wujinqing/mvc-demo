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
public class TextUtil2 {
    public static void main(String[] args) throws IOException {
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TextUtil2.class.getClassLoader()
                        .getResourceAsStream("data2.txt"))));
        ) {
            String buf = "";

            while(null != (buf = br.readLine()))
            {
                buf = buf.trim();

                if(buf.length() > 0)
                {

                    String[] arr = buf.split("=");

                    System.out.println("INSERT INTO `mutms`.`sys_code_info` ( `type`, `language`, `platform`, `code`, `val`, `val_en`, `remark`, `crt_dt`, `opt_dt`, `start_dt`, `end_dt`, `version`, `valid`) VALUES ('I18N', 'zh', 'APP', 'bill_detail_pay_text_"+arr[0].trim()+"', '"+arr[1].trim()+"', NULL, 'flutter_wjq', '2022-04-13 16:00:56', '2022-04-13 16:00:56', NULL, NULL, '0', 1);");


                }
            }
        }

    }
}
