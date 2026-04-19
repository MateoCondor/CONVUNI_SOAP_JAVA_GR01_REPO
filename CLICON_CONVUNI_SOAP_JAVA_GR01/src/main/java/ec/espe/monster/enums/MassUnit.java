package ec.espe.monster.enums;

public enum MassUnit {
    Milligrams("mm"),
    Grams("g"),
    Kilograms("kg"),
    Pounds("lb"),
    Ounces("oz"),
    Tons("t");

    public String value;

    MassUnit(String value) {
        this.value = value;
    }
}
