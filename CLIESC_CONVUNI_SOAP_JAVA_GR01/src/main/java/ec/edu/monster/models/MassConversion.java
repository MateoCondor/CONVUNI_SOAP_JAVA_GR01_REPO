package ec.edu.monster.models;

import ec.edu.monster.enums.MassUnit;
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
public class MassConversion {
    private MassUnit from;
    private MassUnit to;
    private double value;
}
