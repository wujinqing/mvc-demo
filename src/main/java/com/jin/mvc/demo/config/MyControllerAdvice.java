package com.jin.mvc.demo.config;

import com.jin.mvc.demo.User;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu.jinqing
 * @date 2020年10月30日
 */
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public User customException(Exception e) {
        e.printStackTrace();
        User user = new User();

        user.setId(1);
        user.setName("zhan san张三Exception");
        user.setAge(30);

        return user;
    }

    /**
     * 全局数据绑定
     * @return
     */
    @ModelAttribute(name = "user")
    public User mydata() {
        User user = new User();

        user.setId(2);
        user.setName("李四");
        user.setAge(45);

        return user;
    }

    /**
     * 全局数据预处理
     *
     * @param binder
     */
    @InitBinder("userA")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("a.");
    }

    @InitBinder("userB")
    public void initBinder2(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("b.");
    }
}
