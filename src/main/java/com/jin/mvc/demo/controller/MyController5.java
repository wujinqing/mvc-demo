package com.jin.mvc.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jin.mvc.demo.MyData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu.jinqing
 * @date 2020年12月01日
 */
@RestController
public class MyController5 {
    @RequestMapping("/getSeason")
    public MyData getSeason(@RequestBody MyData myData)
    {
        System.out.println(JSON.toJSONString(myData));

        return myData;
    }

}
