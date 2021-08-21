package com.jin.mvc.demo.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Operators
 *
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test11 {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        // evaluates to true
        boolean trueValue = parser.parseExpression("2 == 2").getValue(Boolean.class);

// evaluates to false
        boolean falseValue = parser.parseExpression("2 < -5.0").getValue(Boolean.class);

// evaluates to true
        boolean trueValue2 = parser.parseExpression("'black' < 'block'").getValue(Boolean.class);

        System.out.println(trueValue);
        System.out.println(falseValue);
        System.out.println(trueValue2);

        // evaluates to false
        boolean falseValue1 = parser.parseExpression(
                "'xyz' instanceof T(Integer)").getValue(Boolean.class);

// evaluates to true
        boolean trueValue1 = parser.parseExpression(
                "'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);

// evaluates to false
        boolean falseValue3 = parser.parseExpression(
                "'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);


        // -- AND --

// evaluates to false
        boolean falseValue4 = parser.parseExpression("true and false").getValue(Boolean.class);

// evaluates to true
        String expression = "isMember('Nikola Tesla') and isMember('Mihajlo Pupin')";
//        boolean trueValue4 = parser.parseExpression(expression).getValue(societyContext, Boolean.class);

// -- OR --

// evaluates to true
        boolean trueValue5 = parser.parseExpression("true or false").getValue(Boolean.class);

// evaluates to true
        String expression5 = "isMember('Nikola Tesla') or isMember('Albert Einstein')";
//        boolean trueValue6 = parser.parseExpression(expression).getValue(societyContext, Boolean.class);

// -- NOT --

// evaluates to false
        boolean falseValue7 = parser.parseExpression("!true").getValue(Boolean.class);

// -- AND and NOT --
        String expression8 = "isMember('Nikola Tesla') and !isMember('Mihajlo Pupin')";
//        boolean falseValue9 = parser.parseExpression(expression).getValue(societyContext, Boolean.class);

    }
}
