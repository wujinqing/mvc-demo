package com.jin.mvc.demo;

import com.jin.mvc.demo.beans.Student;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author wu.jinqing
 * @date 2021年06月21日
 */
public class Test3 {
    public static void main(String[] args) {
        Resource resource = new ClassPathResource("test3.xml");
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        beanDefinitionReader.loadBeanDefinitions(resource);

        Student student = defaultListableBeanFactory.getBean(Student.class);
        System.out.println(student.getName());
    }
}
