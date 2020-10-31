package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wu.jinqing
 * @date 2020年10月30日
 */
@Controller
public class MyController3 {
    @RequestMapping("/getMyUser")
    @ResponseBody
    public User getUser(User user)
    {
        return user;
    }

    @RequestMapping("/getMyUser2")
    @ResponseBody
    public User getUser(@ModelAttribute("userA") User userA, @ModelAttribute("userB") User userB)
    {
        userB.setName(userA.getName() + userB.getName());

        return userB;
    }
}
