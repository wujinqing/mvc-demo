package com.jin.mvc.demo.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;
import java.util.Map;

/**
 * Inline Lists
 *  Inline Maps
 *
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test10 {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        // evaluates to a Java list containing the four numbers
//        List numbers = (List) parser.parseExpression("{1,2,3,4}").getValue(context);
//
//        List listOfLists = (List) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue(context);



        // evaluates to a Java map containing the two entries
//        Map inventorInfo = (Map) parser.parseExpression("{name:'Nikola',dob:'10-July-1856'}").getValue(context);
//
//        Map mapOfMaps = (Map) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue(context);



//        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue(context);
//
//// Array with initializer
//        int[] numbers2 = (int[]) parser.parseExpression("new int[]{1,2,3}").getValue(context);
//
//// Multi dimensional array
//        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);



//        // string literal, evaluates to "bc"
//        String bc = parser.parseExpression("'abc'.substring(1, 3)").getValue(String.class);
//
//// evaluates to true
//        boolean isMember = parser.parseExpression("isMember('Mihajlo Pupin')").getValue(
//                societyContext, Boolean.class);


    }
}
