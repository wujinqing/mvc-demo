package com.jin.mvc.demo.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.GregorianCalendar;

/**
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test6 {
    public static void main(String[] args) {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

// The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
        PlaceOfBirth placeOfBirth = new PlaceOfBirth("Serbian");
        tesla.setPlaceOfBirth(placeOfBirth);
        ExpressionParser parser = new SpelExpressionParser();

        Expression exp = parser.parseExpression("name"); // Parse name as an expression
        String name = (String) exp.getValue(tesla);
        System.out.println(name);
// name == "Nikola Tesla"

        exp = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = exp.getValue(tesla, Boolean.class);
        System.out.println(result);
// result == true

        // evals to 1856
        int year = (Integer) parser.parseExpression("birthdate.year + 1900").getValue(tesla);

        String city = (String) parser.parseExpression("placeOfBirth.city").getValue(tesla);

        System.out.println(year);
        System.out.println(city);
    }
}
