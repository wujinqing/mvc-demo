package com.jin.mvc.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu.jinqing
 * @date 2020年11月06日
 */
@Data
public class MyTest {
    private String eventId = "xxxid";
    private String statusKey = "status_var";
    private String messageKey;
    private String pagelocationKep = "";

    private Map<String, String> map; // pagelocation: url, status_var: "xx", xxxid： "",

    public static void main(String[] args) {
        MyTest myTest = new MyTest();

        myTest.setEventId("confirmEvent");
        myTest.setStatusKey("Status_Var");
        myTest.setMessageKey("Message_Var");
        myTest.setPagelocationKep("pagelocation");

        Map<String, String> map = new HashMap<>();

        map.put("pagelocation", "http://www.baidu.com");
        map.put("Status_Var", "确定");
        map.put("Message_Var", "内容");

        myTest.setMap(map);

        String s = JSON.toJSONString(myTest);

        System.out.println(s);


    }
}
