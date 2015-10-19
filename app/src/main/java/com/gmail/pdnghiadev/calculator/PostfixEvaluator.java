package com.gmail.pdnghiadev.calculator;

import java.text.DecimalFormat;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by PDNghiaDev on 10/16/2015.
 */
public class PostfixEvaluator {
    private String postfixString, outputString;

    public boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    public boolean isSpace(char c) {
        return (c == ' ');
    }

    public void evaluatorPostfix() {
        Stack<Double> evalStack = new Stack<>(); // stack of evaluator
        double leftOperand, rightOperand; // operands
        char c; // the first character of a token
        StringTokenizer parser = new StringTokenizer(postfixString, "+-*/ ", true); // StringTokenizer for the input postfix string
        while (parser.hasMoreTokens()) {
            String token = parser.nextToken(); // get next token
            c = token.charAt(0); // get c

            if ((token.length() == 1) && isOperator(c)) { //if token is an operator
                rightOperand = evalStack.pop();
                leftOperand = evalStack.pop();

                switch (c) {
                    case '+':
                        evalStack.push(leftOperand + rightOperand);
                        break;
                    case '-':
                        evalStack.push(leftOperand - rightOperand);
                        break;
                    case '*':
                        evalStack.push(leftOperand * rightOperand);
                        break;
                    case '/':
                        evalStack.push(leftOperand / rightOperand);
                        break;
                }
            } else if ((token.length() == 1) && isSpace(c)) { // if token was a space, ignore it
                ;
            } else { // otherwise, push the numerical value of the token on the stack
                evalStack.push(Double.valueOf(token));
            }
        }

        // remove final result from stack and output it
        double a = evalStack.pop();
        if (a % 1 == 0){
            output((int)a + ""); // show integer number ex: 3
        }else {
            output(a + ""); // show double number ex: 0.25
        }
    }

    private void output(String s) {
        outputString = s;
    }

    public void setInput(String input) {
        postfixString = input;
    }

    public String getOutput() {
        return outputString;
    }

}
