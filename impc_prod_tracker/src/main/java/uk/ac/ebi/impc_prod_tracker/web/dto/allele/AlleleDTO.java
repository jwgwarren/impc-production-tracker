package uk.ac.ebi.impc_prod_tracker.web.dto.allele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.ac.ebi.impc_prod_tracker.web.dto.gene.ProjectGeneDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.location.ProjectLocationDTO;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AlleleDTO
{
    @JsonIgnore
    private Long id;
    private String name;
    private String alleleSymbol;
    private String mgiAlleleId;
    private String alleleTypeName;
    private String alleleSubtypeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("alleleGeneAttributes")
    private List<ProjectGeneDTO> projectGeneDTOS;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("locationAttributes")
    private List<ProjectLocationDTO> projectLocationDTOS;
}
