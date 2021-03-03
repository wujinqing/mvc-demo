package com.jin.mvc.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu.jinqing
 * @date 2021年03月02日
 */
@RestController
public class SessionTestController {
    @RequestMapping("/session")
    public static String session()
    {
        return "this is session";
    }
}
