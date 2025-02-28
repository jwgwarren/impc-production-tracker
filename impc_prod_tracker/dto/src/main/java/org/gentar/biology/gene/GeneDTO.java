package org.gentar.biology.gene;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GeneDTO
{
    private Long id;
    @JsonProperty("accessionId")
    private String accId;
    private String name;
    private String symbol;
    private String speciesName;
    private String externalLink;
}
