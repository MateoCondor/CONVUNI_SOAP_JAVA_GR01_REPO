package ec.espe.monster.models;

import ec.espe.monster.enums.LengthUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LengthConversion {
    private LengthUnit from;
    private LengthUnit to;
    private double value;
}
