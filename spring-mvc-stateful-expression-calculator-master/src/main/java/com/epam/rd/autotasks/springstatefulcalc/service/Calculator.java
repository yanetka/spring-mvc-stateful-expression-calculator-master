package com.epam.rd.autotasks.springstatefulcalc.service;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

@Component
public class Calculator {

    private static final String OPERATORS = "+-*/";
    private static final String DELIMITERS = "()" + OPERATORS;

    public List<String> expressionToRPN(String expression) {
        List<String> rpn = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, DELIMITERS, true);
        String prevItem = "";
        String currItem = "";

        while (tokenizer.hasMoreTokens()) {
            currItem = tokenizer.nextToken();
            if (isDelimiter(currItem)) {
                if (currItem.equals("(")) stack.push(currItem);
                else if (currItem.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        rpn.add(stack.pop());
                    }
                    stack.pop();
                } else {
                    if (currItem.equals("-") && (prevItem.equals("") || (isDelimiter(prevItem) && !prevItem.equals(")")))) {
                        // унарный минус
                        currItem = "u-";
                    } else {
                        while (!stack.isEmpty() && (getPriority(currItem) <= getPriority(stack.peek()))) {
                            rpn.add(stack.pop());
                        }
                    }
                    stack.push(currItem);
                }
            } else {
                rpn.add(currItem);
            }
            prevItem = currItem;
        }
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) rpn.add(stack.pop());
        }
        return rpn;
    }

    private boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        return anyMatch(token, DELIMITERS);
    }

    private boolean anyMatch(String token,  String matcher) {
        return IntStream.range(0, matcher.length()).anyMatch(i -> token.charAt(0) == matcher.charAt(i));
    }

    private boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        return anyMatch(token, OPERATORS);
    }

    private int getPriority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public Integer calculateResult(List<String> rpn) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String item : rpn) {
            switch (item) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    Integer b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    Integer b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                case "u-":
                    stack.push(-stack.pop());
                    break;
                default:
                    stack.push(Integer.valueOf(item));
                    break;
            }
        }
        return stack.pop();
    }
}
