package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.User;
import com.jin.mvc.demo.service.MyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
@Service
public class MyController implements InitializingBean, EnvironmentAware, ApplicationContextAware {
    @Autowired
    private MyService myService;
    @Value("${uer.name}")
    private String name;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext");
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("environment");
    }

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
