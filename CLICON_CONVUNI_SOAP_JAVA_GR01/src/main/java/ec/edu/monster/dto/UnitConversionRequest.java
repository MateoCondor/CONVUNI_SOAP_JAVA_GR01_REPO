package ec.edu.monster.dto;

public record UnitConversionRequest(
        String fromUnit,
        String toUnit,
        double value) {
}
