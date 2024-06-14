package com.epam.rd.autotasks.springstatefulcalc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class ResponseHandler {

    public ResponseEntity<HttpStatus> sendResponse(String parameterName, HttpSession session) {
        if (session.getAttribute(parameterName) == null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
