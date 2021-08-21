package com.jin.mvc.demo.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test9 {
    public static void main(String[] args) {
//        ExpressionParser parser = new SpelExpressionParser();
//        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
//
//// Inventions Array
//
//// evaluates to "Induction motor"
//        String invention = parser.parseExpression("inventions[3]").getValue(
//                context, tesla, String.class);
//
//// Members List
//
//// evaluates to "Nikola Tesla"
//        String name = parser.parseExpression("members[0].name").getValue(
//                context, ieee, String.class);
//
//// List and Array navigation
//// evaluates to "Wireless communication"
//        String invention = parser.parseExpression("members[0].inventions[6]").getValue(
//                context, ieee, String.class);
//
//
//        // Officer's Dictionary
//
//        Inventor pupin = parser.parseExpression("officers['president']").getValue(
//                societyContext, Inventor.class);
//
//// evaluates to "Idvor"
//        String city = parser.parseExpression("officers['president'].placeOfBirth.city").getValue(
//                societyContext, String.class);

// setting values
//        parser.parseExpression("officers['advisors'][0].placeOfBirth.country").setValue(
//                societyContext, "Croatia");
    }
}
