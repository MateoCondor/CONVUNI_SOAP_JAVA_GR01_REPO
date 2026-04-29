package ec.edu.monster.views;

import java.util.Map;
import java.util.Scanner;

public class UnitConversionView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("\n=== MENÚ DE CONVERSIONES ===");
        System.out.println("1. Masa");
        System.out.println("2. Longitud");
        System.out.println("3. Temperatura");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public <T extends Enum<T>> T selectUnit(String label, Class<T> enumClass, Map<T, String> optionsLabels) {
        System.out.println("\nSeleccione unidad de " + label + ":");
        T[] values = enumClass.getEnumConstants();

        for (int i = 0; i < values.length; i++) {
            String optionLabel = values[i].toString();
            if (optionsLabels != null && optionsLabels.containsKey(values[i])) {
                optionLabel = optionsLabels.get(values[i]);
            }
            System.out.println((i + 1) + ". " + optionLabel);
        }

        System.out.print("Opción: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 1;
        }

        int index = Math.max(0, Math.min(choice - 1, values.length - 1));
        return values[index];
    }

    public double getValue() {
        System.out.print("Ingrese el valor a convertir: ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void showResult(String message, double result) {
        System.out.println("\n============================");
        System.out.println("Resultado: " + result);
        System.out.println("Estado: " + message);
        System.out.println("============================");
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    public void showErrorMessage(String message) {
        System.out.println("\u001B[41m\u001B[37m\n[ !!! ERROR DE CONEXIÓN !!! ]\u001B[0m");
        System.out.println("Detalle: " + message);
        System.out.println("Presione Enter para volver al menú...");
        scanner.nextLine();
    }
}