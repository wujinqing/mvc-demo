package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.User;
import com.jin.mvc.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu.jinqing
 * @date 2020年11月04日
 */
@RestController
public class MyController4 {
    @Autowired
    private MyService myService;

    @RequestMapping("/doSomething")
    @ResponseBody
    public String doSomething()
    {
        return myService.doSomething();
    }
}
