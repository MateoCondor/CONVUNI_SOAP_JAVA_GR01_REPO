package ec.edu.monster.enums;

public enum LengthUnit {
    Millimeters("mm"),
    Centimeters("cm"),
    Meters("m"),
    Kilometers("km"),
    Inches("in"),
    Feet("ft"),
    Yards("yd"),
    Miles("mi");

    public String value;

    LengthUnit(String value) {
        this.value = value;
    }
}
