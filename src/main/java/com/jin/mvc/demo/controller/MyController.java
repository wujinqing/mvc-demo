package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author wu.jinqing
 * @date 2020年10月23日
 */
@Controller
public class MyController implements InitializingBean {
    @RequestMapping("/getUser2")
    public String getUser(Model model, @RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三");
        user.setAge(30);

        model.addAttribute(user);

        return "user";
    }
    @RequestMapping(path = "/getUser3", method = {RequestMethod.GET, RequestMethod.POST})
    public String getUser(Model model, @RequestParam("id") int id, UriComponentsBuilder uriComponentsBuilder)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三");
        user.setAge(30);

        model.addAttribute(user);

        return "user";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
