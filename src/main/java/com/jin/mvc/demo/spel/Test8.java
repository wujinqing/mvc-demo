package com.jin.mvc.demo.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test8 {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

// evals to "Hello World"
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();

        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();

// evals to 2147483647
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();

        Object nullValue = parser.parseExpression("null").getValue();

        System.out.println(helloWorld);
        System.out.println(avogadrosNumber);
        System.out.println(maxValue);
        System.out.println(trueValue);
        System.out.println(nullValue);
    }
}
