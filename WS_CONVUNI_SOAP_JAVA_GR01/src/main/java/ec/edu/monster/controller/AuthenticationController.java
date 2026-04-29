package ec.edu.monster.controller;

import ec.edu.monster.model.LoginResponse;
import ec.edu.monster.service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public LoginResponse login(String username, String password) {
        return authenticationService.login(username, password);
    }
}
