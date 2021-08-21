package com.jin.mvc.demo.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test14 {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        context.setVariable("newName", "Mike Tesla");

        parser.parseExpression("name = #newName").getValue(context, tesla);
        System.out.println(tesla.getName());  // "Mike Tesla"
    }
}
