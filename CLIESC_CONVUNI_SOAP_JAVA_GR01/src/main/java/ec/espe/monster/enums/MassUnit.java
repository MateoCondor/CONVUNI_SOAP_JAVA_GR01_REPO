package ec.espe.monster.enums;

public enum MassUnit {
    Milligrams("mm", "Miligramos"),
    Grams("g", "Gramos"),
    Kilograms("kg", "Kilogramos"),
    Pounds("lb", "Libras"),
    Ounces("oz", "Onzas"),
    Tons("t", "Toneladas");

    public String value;
    public String label;

    MassUnit(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
