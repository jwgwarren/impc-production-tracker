package org.gentar.biology.location;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.gentar.biology.strain.StrainDTO;

@Data
@RequiredArgsConstructor
public class LocationDTO
{
    private String chr;
    private Long start;
    private Long stop;
    private String strand;
    private String genomeBuild;
    private StrainDTO strain;
    private String speciesName;
    private Integer speciesTaxonId;
}
