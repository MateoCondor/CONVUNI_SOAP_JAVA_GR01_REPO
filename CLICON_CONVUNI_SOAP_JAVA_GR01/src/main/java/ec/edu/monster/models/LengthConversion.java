package ec.edu.monster.models;

import ec.edu.monster.enums.LengthUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class LengthConversion {
    private LengthUnit from;
    private LengthUnit to;
    private double value;
}
