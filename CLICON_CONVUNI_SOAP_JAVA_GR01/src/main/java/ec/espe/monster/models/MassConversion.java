package ec.espe.monster.models;

import ec.espe.monster.enums.MassUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class MassConversion {
    private MassUnit from;
    private MassUnit to;
    private double value;
}
