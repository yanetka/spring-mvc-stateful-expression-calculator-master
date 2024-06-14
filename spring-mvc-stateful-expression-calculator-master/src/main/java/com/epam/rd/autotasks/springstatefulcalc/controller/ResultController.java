package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.exception.ResultException;
import com.epam.rd.autotasks.springstatefulcalc.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/calc/result")
    public ResponseEntity<String> getResult(@Value("${error.message.variable.not.set}") String errorMessage,
                                            HttpSession session) {
        Map<String, String> parameters = mapVariables(session);
        try {
            String result = resultService.calculate(errorMessage, parameters);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResultException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private Map<String, String> mapVariables(HttpSession session) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String key = sessionAttributeNames.nextElement();
            parameters.put(key, (String) session.getAttribute(key));
        }
        return parameters;
    }
}
