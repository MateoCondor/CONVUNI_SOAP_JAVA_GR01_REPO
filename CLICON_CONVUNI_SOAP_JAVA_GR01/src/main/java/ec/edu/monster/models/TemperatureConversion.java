package ec.edu.monster.models;

import ec.edu.monster.enums.TemperatureUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TemperatureConversion {
    private TemperatureUnit from;
    private TemperatureUnit to;
    private double value;
}
