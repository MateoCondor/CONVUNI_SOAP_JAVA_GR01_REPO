package ec.edu.monster.views;

import java.util.Scanner;
import java.io.Console;

public class LoginView {
    private final Scanner scanner = new Scanner(System.in);

    public record LoginData(String username, String password) {
    }

    public LoginData showLogin() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Usuario: ");
        String user = scanner.nextLine();

        System.out.print("Contraseña: ");
        String pass = readPassword();

        return new LoginData(user, pass);
    }

    public void showWelcome(String username) {
        System.out.println("\u001B[32m\n[SUCCESS] ¡Bienvenido, " + username + "!\u001B[0m");
    }

    public void showError(String message) {
        System.out.println("\u001B[31m\n[ERROR] " + message + "\u001B[0m");
    }

    private String readPassword() {
        Console console = System.console();

        if (console != null) {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            return scanner.nextLine();
        }
    }
}