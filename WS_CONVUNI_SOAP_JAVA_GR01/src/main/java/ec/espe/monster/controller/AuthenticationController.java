package ec.espe.monster.controller;

import ec.espe.monster.model.LoginResponse;
import ec.espe.monster.service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public LoginResponse login(String username, String password) {
        return authenticationService.login(username, password);
    }
}
