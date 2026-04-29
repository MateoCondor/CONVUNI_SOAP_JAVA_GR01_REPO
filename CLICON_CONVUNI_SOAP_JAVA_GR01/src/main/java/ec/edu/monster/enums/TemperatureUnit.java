package ec.edu.monster.enums;

public enum TemperatureUnit {
    Celsius("c"),
    Farenheit("f"),
    Kelvin("k"),
    Rankine("r"),
    Reaumur("re");

    public String value;

    TemperatureUnit(String value) {
        this.value = value;
    }
}
