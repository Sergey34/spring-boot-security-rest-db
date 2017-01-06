package base.dto;

import base.domain.airfoil.Prefix;
import lombok.Data;

@Data
public class AirfoilDto {
    private String name;
    private String shortName;
    private String coordView;
    private String description;
    private Prefix prefix;
}
