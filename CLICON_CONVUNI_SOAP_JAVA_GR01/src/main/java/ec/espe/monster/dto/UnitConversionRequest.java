package ec.espe.monster.dto;

public record UnitConversionRequest(
        String fromUnit,
        String toUnit,
        double value) {
}
