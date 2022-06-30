package com.jin.mvc.demo.controller;

import com.jin.mvc.demo.beans.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * @author wu.jinqing
 * @date 2021年11月08日
 */
@Controller
public class MyController6 {
    @GetMapping("/events")
    public ResponseBodyEmitter handle() throws IOException {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        Student student = new Student();

        student.setAge(10);
        student.setName("zhansan");
        emitter.send(student);

        emitter.complete();
        // Save the emitter somewhere..
        return emitter;
    }
}
