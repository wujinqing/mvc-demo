package com.jin.mvc.demo;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu.jinqing
 * @date 2020年10月22日
 */
@SpringBootApplication
@RestController
@RequestMapping("/user")
@ServletComponentScan
public class Bootstrap {
    @Value("${uer.name}")
    private String name;
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

    @RequestMapping("/getUser")
    public User getUser(@RequestParam("id") int id)
    {
        User user = new User();

        user.setId(id);
        user.setName("zhan san张三");
        user.setAge(30);

        return user;
    }

    @RequestMapping("/getException")
    public User getException() throws Exception
    {
        throw new Exception("exp");

    }


}
