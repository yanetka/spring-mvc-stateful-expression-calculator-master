package com.epam.rd.autotasks.springstatefulcalc.service;

import com.epam.rd.autotasks.springstatefulcalc.exception.ResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResultService implements Result {

    private static final String DIGITAL_REGEX = "^[-\\d]*$";
    private static final String LITERAL_REGEX = "[a-z]";

    private final Calculator calculator;

    @Autowired
    public ResultService(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public String calculate(String errorMessage, Map<String, String> parameters) throws ResultException {
        String expressionToBeEvaluated = buildExpression(errorMessage, parameters);
        return processExpression(expressionToBeEvaluated);
    }

    private String processExpression(String expression) {
        List<String> expressionToRPN = calculator.expressionToRPN(expression);
        Integer result = calculator.calculateResult(expressionToRPN);
        return result.toString();
    }

    private String buildExpression(String errorMessage,
                                    Map<String, String> parameters) throws ResultException {
        String expression = parameters.remove("expression");
        expression = expression.replaceAll(" ", "");
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isLetter(expression.charAt(i))) {
                String value = parameters.get(Character.toString(expression.charAt(i)));
                if (value == null || value.equals("")) {
                    throw new ResultException(String.format(errorMessage, expression.charAt(i)));
                } else if (value.matches(DIGITAL_REGEX)) {
                    expression = expression.replaceAll(String.valueOf(expression.charAt(i)), value);
                } else if (value.matches(LITERAL_REGEX)) {
                    expression = expression.replaceAll(String.valueOf(expression.charAt(i--)), value);
                }
            }
        }
        return expression;
    }
}
