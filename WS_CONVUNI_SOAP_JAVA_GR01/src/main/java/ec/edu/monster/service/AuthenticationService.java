package ec.edu.monster.service;

import ec.edu.monster.model.LoginResponse;

public class AuthenticationService {

    private static final String USERNAME = "MONSTER";
    private static final String PASSWORD = "MONSTER9";

    public LoginResponse login(String username, String password) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return new LoginResponse(true, "Autenticacion correcta.");
        }

        return new LoginResponse(false, "Usuario o clave incorrectos.");
    }
}
