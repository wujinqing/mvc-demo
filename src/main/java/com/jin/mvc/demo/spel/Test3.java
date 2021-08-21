package com.jin.mvc.demo.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test3 {
    public static void main(String[] args) {
//        String s = "hello world";
//        System.out.println(s.getBytes());
        ExpressionParser parser = new SpelExpressionParser();

// invokes 'getBytes()'
        Expression exp = parser.parseExpression("'Hello World'.bytes");
//        Expression exp = parser.parseExpression("'Hello World'.getBytes()");
        byte[] bytes = (byte[]) exp.getValue();
        System.out.println(bytes.length);
    }
}
