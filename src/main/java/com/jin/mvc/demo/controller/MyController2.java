package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.User;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author wu.jinqing
 * @date 2020年10月28日
 */
@RestController
public class MyController2 {
    @RequestMapping("/getUser4")
    public User getUser(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    @RequestMapping("/getUser5")
    public User getUser(@RequestBody User user1)
    {
        User user = new User();

        user.setId(user1.getId());
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    @RequestMapping(path = "/getUser6", method = {RequestMethod.GET, RequestMethod.POST})
    public User getUser6(@RequestBody User user1)
    {
        User user = new User();

        user.setId(user1.getId());
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    /**
     * 匹配参数id的值是21，name参数要出现，age参数不能出现
     * @param id
     * @return
     */
    @RequestMapping(path = "/getUser7", params = {"id=21", "name", "!age"})
    public User getUser7(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    /**
     * 匹配头信息，content-type满足text/*，必须出现key为My-Header的头，并且不能出现可以为My-Other-Header的头
     * @param id
     * @return
     */
    @RequestMapping(path = "/getUser8", headers = {"content-type=text/*", "My-Header", "!My-Other-Header"})
    public User getUser8(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    /**
     * 匹配请求头里面Content-Type的值,
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getUser9", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public User getUser9(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }

    /**
     * 匹配请求头里面Accept的值
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getUser10", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public User getUser10(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三2");
        user.setAge(30);

        return user;
    }
}
