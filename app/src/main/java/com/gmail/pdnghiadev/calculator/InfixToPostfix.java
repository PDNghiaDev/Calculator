package com.gmail.pdnghiadev.calculator;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by PDNghiaDev on 10/13/2015.
 */
public class InfixToPostfix {

    private boolean isOperator(char c) { // Tell whether c is an operator.
        return c == '+'  ||  c == '-'  ||  c == '*'  ||  c == '/';
    }



    private boolean isSpace(char c) {  // Tell whether c is a space.
        return (c == ' ');
    }


    private boolean lowerPrecedence(char op1, char op2) {
        // whether op1 has lower precedence than op2, where op1 is an
        // operator on the left and op2 is an operator on the right.
        // op1 and op2 are assumed to be operator characters (+,-,*,/,^).

        switch (op1) {

            case '+':
            case '-':
                return !(op2=='+' || op2=='-') ;

            case '*':
            case '/':
                return true;

            default:  // (shouldn't happen)
                return false;
        }
    }

    public String convertToPostfix(String infix) {

        Stack<String> operatorStack = new Stack<>();  // the stack of operators

        char c;       // the first character of a token

        StringTokenizer parser = new StringTokenizer(infix,"+-*/^() ",true);
        // StringTokenizer for the input string

        StringBuilder postfix = new StringBuilder(infix.length());

        // Process the tokens.
        while (parser.hasMoreTokens()) {

            String token = parser.nextToken();          // get the next token
            // and let c be
            c = token.charAt(0);         // the first character of this token

            if ( (token.length() == 1) && isOperator(c) ) {    // if token is
                //  an operator

                while (!operatorStack.empty() &&
                        !lowerPrecedence((operatorStack.peek()).charAt(0), c))
                    // (Operator on the stack does not have lower precedence, so
                    //  it goes before this one.)

                    postfix.append(" ").append(operatorStack.pop());

                    operatorStack.push(token);// Push this operator onto the stack.

            }
            else if ( (token.length() == 1) && isSpace(c) ) {    // else if token was a space ignore it
                ;
            }
            else {  // (it is an operand)
                postfix.append(" ").append(token);  // output the operand
            }

        }

        // Output the remaining operators on the stack.
        while (!operatorStack.empty())
            postfix.append(" ").append(operatorStack.pop());

        // Return the result.

        return postfix.toString();

    }
}
