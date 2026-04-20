package ec.espe.monster.enums;

public enum LengthUnit {
    Millimeters("mm", "Milímetros"),
    Centimeters("cm", "Centímetros"),
    Meters("m", "Metros"),
    Kilometers("km", "Kilómetros"),
    Inches("in", "Pulgadas"),
    Feet("ft", "Pies"),
    Yards("yd", "Yardas"),
    Miles("mi", "Millas");

    public String value;
    public String label;

    LengthUnit(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
