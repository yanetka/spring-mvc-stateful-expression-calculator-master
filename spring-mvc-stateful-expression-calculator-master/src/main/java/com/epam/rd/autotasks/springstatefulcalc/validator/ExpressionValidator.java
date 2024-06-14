package com.epam.rd.autotasks.springstatefulcalc.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExpressionValidator {

    private static final String REGEX_MATH = "^(?!.*([a-z]{2}|[*+-/]{2}))[a-z*+-/()\\d]+$";

    public boolean  isValid(String expression) {
        Pattern pattern = Pattern.compile(REGEX_MATH);
        Matcher matcher = pattern.matcher(expression.replaceAll(" ", ""));
        return matcher.matches();
    }
}
