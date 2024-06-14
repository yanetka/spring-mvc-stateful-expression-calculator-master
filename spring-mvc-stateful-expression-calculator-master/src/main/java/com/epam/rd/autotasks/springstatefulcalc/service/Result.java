package com.epam.rd.autotasks.springstatefulcalc.service;

import com.epam.rd.autotasks.springstatefulcalc.exception.ResultException;

import java.util.Map;

public interface Result {

    public String calculate(String errorMessage, Map<String, String> parametersMap) throws ResultException;
}
