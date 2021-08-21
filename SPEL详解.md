# SPEL: Spring Expression Language

``` 
The expression language supports the following functionality:

Literal expressions

Boolean and relational operators

Regular expressions

Class expressions

Accessing properties, arrays, lists, and maps

Method invocation

Relational operators

Assignment

Calling constructors

Bean references

Array construction

Inline lists

Inline maps

Ternary operator

Variables

User-defined functions

Collection projection

Collection selection

Templated expressions

```

## SpelParserConfiguration SPEL配置


## Expressions in Bean Definitions：#{ \<expression string> }



``` 
#{ T(java.lang.Math).random() * 100.0 }

#{ systemProperties['user.region'] }

#{ numberGuess.randomNumber }

@Value("#{ systemProperties['user.region'] }")


```


``` 
lt (<)

gt (>)

le (<=)

ge (>=)

eq (==)

ne (!=)

div (/)

mod (%)

not (!).



and (&&)

or (||)

not (!)


All of the textual operators are case-insensitive.
```



## The #this and #root Variables

``` 
The #this variable is always defined and refers to the current evaluation object 
(against which unqualified references are resolved). The #root variable is always 
defined and refers to the root context object. Although #this may vary as components 
of an expression are evaluated, #root always refers to the root. The following examples 
show how to use the #this and #root variables:
```

## Bean References: @

``` 
ExpressionParser parser = new SpelExpressionParser();
StandardEvaluationContext context = new StandardEvaluationContext();
context.setBeanResolver(new MyBeanResolver());

// This will end up calling resolve(context,"something") on MyBeanResolver during evaluation
Object bean = parser.parseExpression("@something").getValue(context);
```

## Ternary Operator (If-Then-Else)
``` 

"false ? 'trueExp' : 'falseExp'"
```

## The Elvis Operator

``` 
"name?:'Unknown'"



String name = "Elvis Presley";
String displayName = (name != null ? name : "Unknown");



```

## Safe Navigation Operator

``` 
"placeOfBirth?.city"
```

## Collection Selection
> .?[selectionExpression]

> .^[selectionExpression]

> .$[selectionExpression]

> To obtain the first element matching the selection, the syntax is .^[selectionExpression]. To obtain the last matching selection, the syntax is .$[selectionExpression].
``` 
"members.?[nationality == 'Serbian']"

"map.?[value<27]"

```

## Collection Projection

> .![projectionExpression]

``` 
"members.![placeOfBirth.city]"
```


## Expression templating

``` 
"random number is #{T(java.lang.Math).random()}"
```

































































































































































































































































































































































































































































































