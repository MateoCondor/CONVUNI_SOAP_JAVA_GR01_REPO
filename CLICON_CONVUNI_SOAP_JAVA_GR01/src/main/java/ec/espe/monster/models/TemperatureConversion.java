package ec.espe.monster.models;

import ec.espe.monster.enums.TemperatureUnit;
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
