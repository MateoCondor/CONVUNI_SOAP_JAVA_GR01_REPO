package ec.espe.monster.enums;

public enum UnitConversionType {
    Length("Longitud"),
    Temperature("Temperatura"),
    Mass("Masa");

    public String value;

    UnitConversionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
