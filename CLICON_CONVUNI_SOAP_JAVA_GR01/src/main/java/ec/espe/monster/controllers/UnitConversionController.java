package ec.espe.monster.controllers;

import java.util.Map;

import ec.espe.monster.enums.LengthUnit;
import ec.espe.monster.enums.MassUnit;
import ec.espe.monster.enums.TemperatureUnit;
import ec.espe.monster.models.LengthConversion;
import ec.espe.monster.models.MassConversion;
import ec.espe.monster.models.TemperatureConversion;
import ec.espe.monster.models.UnitConversionResult;
import ec.espe.monster.services.UnitConversionService;
import ec.espe.monster.views.UnitConversionView;

public class UnitConversionController {
    private final UnitConversionService service;
    private final UnitConversionView view;

    public UnitConversionController(UnitConversionService service, UnitConversionView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            int option = view.showMainMenu();
            switch (option) {
                case 1 -> handleMass();
                case 2 -> handleLength();
                case 3 -> handleTemperature();
                case 4 -> exit = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void handleTemperature() {
        try {
            TemperatureUnit from = view.selectUnit("ORIGEN", TemperatureUnit.class, null);
            TemperatureUnit to = view.selectUnit("DESTINO", TemperatureUnit.class, null);
            double value = view.getValue();

            TemperatureConversion model = new TemperatureConversion(from, to, value);

            UnitConversionResult result = service.convertTemperature(model).join();

            view.showResult(result.message(), result.value());
        } catch (Exception ex) {
            view.showErrorMessage(extractMessage(ex));
        }
    }

    private void handleMass() {
        try {
            Map<MassUnit, String> optionsLabels = Map.of(
                    MassUnit.Grams, "Gramos",
                    MassUnit.Kilograms, "Kilogramos",
                    MassUnit.Milligrams, "Miligramos",
                    MassUnit.Ounces, "Onzas",
                    MassUnit.Pounds, "Libras",
                    MassUnit.Tons, "Toneladas");

            MassUnit from = view.selectUnit("ORIGEN", MassUnit.class, optionsLabels);
            MassUnit to = view.selectUnit("DESTINO", MassUnit.class, optionsLabels);
            double value = view.getValue();

            MassConversion model = new MassConversion(from, to, value);

            UnitConversionResult result = service.convertMass(model).join();

            view.showResult(result.message(), result.value());
        } catch (Exception ex) {
            view.showErrorMessage(extractMessage(ex));
        }
    }

    private void handleLength() {
        try {
            Map<LengthUnit, String> optionsLabels = Map.of(
                    LengthUnit.Centimeters, "Centímetros",
                    LengthUnit.Feet, "Pies",
                    LengthUnit.Inches, "Pulgadas",
                    LengthUnit.Kilometers, "Kilómetros",
                    LengthUnit.Meters, "Metros",
                    LengthUnit.Miles, "Millas",
                    LengthUnit.Millimeters, "Milímetros",
                    LengthUnit.Yards, "Yardas");

            LengthUnit from = view.selectUnit("ORIGEN", LengthUnit.class, optionsLabels);
            LengthUnit to = view.selectUnit("DESTINO", LengthUnit.class, optionsLabels);
            double value = view.getValue();

            LengthConversion model = new LengthConversion(from, to, value);

            UnitConversionResult result = service.convertLength(model).join();

            view.showResult(result.message(), result.value());
        } catch (Exception ex) {
            view.showErrorMessage(extractMessage(ex));
        }
    }

    private String extractMessage(Exception ex) {
        return (ex.getCause() != null) ? ex.getCause().getMessage() : ex.getMessage();
    }
}