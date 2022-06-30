package com.jin.mvc.demo.callable;

import com.jin.mvc.demo.beans.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author wu.jinqing
 * @date 2021年10月19日
 */
@RestController
@Slf4j
public class MyCallableController {
    @RequestMapping("/call")
    public Callable<Student> call()
    {
        log.info("111");
        return () -> {
            log.info("222");
            Thread.sleep(3000);
            Student student = new Student();
            student.setName("张三");
            student.setAge(20);
            log.info("333");
            return student;
        };
    }
}
