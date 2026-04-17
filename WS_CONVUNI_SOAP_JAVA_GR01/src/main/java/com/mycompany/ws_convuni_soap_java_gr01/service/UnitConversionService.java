package com.mycompany.ws_convuni_soap_java_gr01.service;

import com.mycompany.ws_convuni_soap_java_gr01.model.ConversionResponse;
import java.util.Map;

public class UnitConversionService {

    private static final Map<String, Double> LENGTH_TO_METERS = Map.of(
            "mm", 0.001,
            "cm", 0.01,
            "m", 1.0,
            "km", 1000.0,
            "in", 0.0254,
            "ft", 0.3048,
            "yd", 0.9144,
            "mi", 1609.344
    );

    private static final Map<String, Double> MASS_TO_KG = Map.of(
            "mg", 0.000001,
            "g", 0.001,
            "kg", 1.0,
            "lb", 0.45359237,
            "oz", 0.028349523125,
            "t", 1000.0
    );

    public ConversionResponse convertLength(double value, String fromUnit, String toUnit) {
        String from = normalize(fromUnit);
        String to = normalize(toUnit);

        if (!LENGTH_TO_METERS.containsKey(from) || !LENGTH_TO_METERS.containsKey(to)) {
            return new ConversionResponse(false,
                    "Unidades de longitud no validas.",
                    "length",
                    value,
                    fromUnit,
                    toUnit,
                    0);
        }

        double inMeters = value * LENGTH_TO_METERS.get(from);
        double result = inMeters / LENGTH_TO_METERS.get(to);

        return new ConversionResponse(true,
                "Conversion realizada correctamente.",
                "length",
                value,
                from,
                to,
                result);
    }

    public ConversionResponse convertMass(double value, String fromUnit, String toUnit) {
        String from = normalize(fromUnit);
        String to = normalize(toUnit);

        if (!MASS_TO_KG.containsKey(from) || !MASS_TO_KG.containsKey(to)) {
            return new ConversionResponse(false,
                    "Unidades de masa no validas.",
                    "mass",
                    value,
                    fromUnit,
                    toUnit,
                    0);
        }

        double inKg = value * MASS_TO_KG.get(from);
        double result = inKg / MASS_TO_KG.get(to);

        return new ConversionResponse(true,
                "Conversion realizada correctamente.",
                "mass",
                value,
                from,
                to,
                result);
    }

    public ConversionResponse convertTemperature(double value, String fromUnit, String toUnit) {
        String from = normalize(fromUnit);
        String to = normalize(toUnit);

        if (!isTemperatureUnitSupported(from) || !isTemperatureUnitSupported(to)) {
            return new ConversionResponse(false,
                    "Unidades de temperatura no validas.",
                    "temperature",
                    value,
                    fromUnit,
                    toUnit,
                    0);
        }

        double celsiusValue = toCelsius(value, from);
        double result = fromCelsius(celsiusValue, to);

        return new ConversionResponse(true,
                "Conversion realizada correctamente.",
                "temperature",
                value,
                from,
                to,
                result);
    }

    private boolean isTemperatureUnitSupported(String unit) {
        return normalizeTemperatureUnit(unit) != null;
    }

    private double toCelsius(double value, String fromUnit) {
        String normalizedUnit = normalizeTemperatureUnit(fromUnit);

        return switch (normalizedUnit) {
            case "c" -> value;
            case "f" -> (value - 32.0) * 5.0 / 9.0;
            case "k" -> value - 273.15;
            case "r" -> (value - 491.67) * 5.0 / 9.0;
            case "re" -> value * 5.0 / 4.0;
            default -> value;
        };
    }

    private double fromCelsius(double value, String toUnit) {
        String normalizedUnit = normalizeTemperatureUnit(toUnit);

        return switch (normalizedUnit) {
            case "c" -> value;
            case "f" -> (value * 9.0 / 5.0) + 32.0;
            case "k" -> value + 273.15;
            case "r" -> (value + 273.15) * 9.0 / 5.0;
            case "re" -> value * 4.0 / 5.0;
            default -> value;
        };
    }

    private String normalizeTemperatureUnit(String unit) {
        String normalized = normalize(unit);

        return switch (normalized) {
            case "c", "celsius", "centigrados", "centigrado", "centigrade" -> "c";
            case "f", "fahrenheit", "farenheit" -> "f";
            case "k", "kelvin" -> "k";
            case "r", "rankine" -> "r";
            case "re", "reaumur", "reamur" -> "re";
            default -> null;
        };
    }

    private String normalize(String unit) {
        return unit == null ? "" : unit.trim().toLowerCase();
    }
}
