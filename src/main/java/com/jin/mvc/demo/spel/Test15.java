package com.jin.mvc.demo.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test15 {
    public static void main(String[] args) {
        // create an array of integers
        List<Integer> primes = new ArrayList<Integer>();
        primes.addAll(Arrays.asList(2,3,5,7,11,13,17));

// create parser and set variable 'primes' as the array of integers
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("primes", primes);

// all prime numbers > 10 from the list (using selection ?{...})
// evaluates to [11, 13, 17]
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#primes.?[#this>10]").getValue(context);





//        Method method = ...;
//
//        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
//        context.setVariable("myFunction", method);




//        ExpressionParser parser = new SpelExpressionParser();
//
//        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
//        context.setVariable("reverseString",
//                StringUtils.class.getDeclaredMethod("reverseString", String.class));
//
//        String helloWorldReversed = parser.parseExpression(
//                "#reverseString('hello')").getValue(context, String.class);

    }


//    String falseString = parser.parseExpression(
//            "false ? 'trueExp' : 'falseExp'").getValue(String.class);


//    ExpressionParser parser = new SpelExpressionParser();
//
//    String name = parser.parseExpression("name?:'Unknown'").getValue(new Inventor(), String.class);
//System.out.println(name);  // 'Unknown'
}
