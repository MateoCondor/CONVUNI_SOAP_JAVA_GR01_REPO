package ec.espe.monster.enums;

public enum TemperatureUnit {
    Celsius("c", "Celsius"),
    Farenheit("f", "Farenheit"),
    Kelvin("k", "Kelvin"),
    Rankine("r", "Rankine"),
    Reaumur("re", "Reaumur");

    public String value;
    public String label;

    TemperatureUnit(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
