package com.mycompany.ws_convuni_soap_java_gr01.controller;

import com.mycompany.ws_convuni_soap_java_gr01.model.LoginResponse;
import com.mycompany.ws_convuni_soap_java_gr01.service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public LoginResponse login(String username, String password) {
        return authenticationService.login(username, password);
    }
}
