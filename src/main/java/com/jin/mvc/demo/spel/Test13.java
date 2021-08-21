package com.jin.mvc.demo.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test13 {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        Inventor inventor = new Inventor();
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        parser.parseExpression("name").setValue(context, inventor, "Aleksandar Seovic");

// alternatively
        String aleks = parser.parseExpression(
                "name = 'Aleksandar Seovic'").getValue(context, inventor, String.class);




        Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);

        Class stringClass = parser.parseExpression("T(String)").getValue(Class.class);

        boolean trueValue = parser.parseExpression(
                "T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR")
                .getValue(Boolean.class);



        Inventor einstein = parser.parseExpression(
                "new org.spring.samples.spel.inventor.Inventor('Albert Einstein', 'German')")
                .getValue(Inventor.class);

// create new Inventor instance within the add() method of List
//        parser.parseExpression("Members.add(new org.spring.samples.spel.inventor.Inventor('Albert Einstein', 'German'))").getValue(societyContext);


    }
}
