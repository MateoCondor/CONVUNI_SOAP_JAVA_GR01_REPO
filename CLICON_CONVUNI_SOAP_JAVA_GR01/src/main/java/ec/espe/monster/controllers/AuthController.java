package ec.espe.monster.controllers;

import ec.espe.monster.models.User;
import ec.espe.monster.services.AuthService;
import ec.espe.monster.views.LoginView;

public class AuthController {
    private final AuthService service;
    private final LoginView view;

    public AuthController(AuthService authService, LoginView view) {
        this.service = authService;
        this.view = view;
    }

    public User runLogin() {
        LoginView.LoginData credentials = view.showLogin();
        User user = null;

        try {
            user = service.Login(credentials.username(), credentials.password()).join();

            if (user.isAuth()) {
                view.showWelcome(user.getUsername());
            } else {
                view.showError("Credenciales inválidas.");
            }
        } catch (Exception ex) {
            String errorMessage = (ex.getCause() != null) ? ex.getCause().getMessage() : ex.getMessage();
            view.showError(errorMessage);
        }

        return user;
    }
}