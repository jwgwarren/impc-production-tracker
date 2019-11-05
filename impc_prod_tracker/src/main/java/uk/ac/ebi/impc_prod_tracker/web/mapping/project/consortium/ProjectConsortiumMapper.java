package uk.ac.ebi.impc_prod_tracker.web.mapping.project.consortium;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.data.biology.project_consortium.ProjectConsortium;
import uk.ac.ebi.impc_prod_tracker.data.organization.institute.Institute;
import uk.ac.ebi.impc_prod_tracker.service.organization.InstituteService;
import uk.ac.ebi.impc_prod_tracker.service.organization.consortium.ConsortiumService;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectConsortiumDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.project.ProjectConsortiumInstituteDTO;
import uk.ac.ebi.impc_prod_tracker.web.mapping.EntityMapper;
import uk.ac.ebi.impc_prod_tracker.web.mapping.project.consortium.institute.ProjectConsortiumInstituteMapper;

import java.util.*;

@Component
public class ProjectConsortiumMapper {
    private EntityMapper entityMapper;
    private ConsortiumService consortiumService;
    private ProjectConsortiumInstituteMapper projectConsortiumInstituteMapper;

    public ProjectConsortiumMapper(ConsortiumService consortiumService, EntityMapper entityMapper, ProjectConsortiumInstituteMapper projectConsortiumInstituteMapper)
    {
        this.entityMapper = entityMapper;
        this.consortiumService = consortiumService;
        this.projectConsortiumInstituteMapper = projectConsortiumInstituteMapper;
    }

    public ProjectConsortium toEntity(ProjectConsortiumDTO projectConsortiumDTO)
    {
        ProjectConsortium projectConsortium = new ProjectConsortium();
        String consortiumName = projectConsortiumDTO.getConsortiumName();
        projectConsortium.setConsortium(consortiumService.getConsortiumByNameOrThrowException(consortiumName));

        return projectConsortium;
    }

    public Set<ProjectConsortium> toEntities(Collection<ProjectConsortiumDTO> projectConsortiumDTOS)
    {
        Set<ProjectConsortium> projectConsortia = new HashSet<>();
        if (projectConsortiumDTOS != null)
        {
            projectConsortiumDTOS.forEach(x -> projectConsortia.add(toEntity(x)));
        }
        return projectConsortia;
    }

    public ProjectConsortiumDTO toDto(ProjectConsortium projectConsortium)
    {
        ProjectConsortiumDTO projectConsortiumDTO = entityMapper.toTarget(projectConsortium, ProjectConsortiumDTO.class);
        addProjectConsortiumInstitute(projectConsortium, projectConsortiumDTO);
        return projectConsortiumDTO;
    }

    public List<ProjectConsortiumDTO> toDtos(Collection<ProjectConsortium> projectConsortium)
    {
        List<ProjectConsortiumDTO> consortiumDTOS = new ArrayList<>();
        if (projectConsortium != null)
        {
            projectConsortium.forEach(x -> consortiumDTOS.add(toDto(x)));
        }
        return consortiumDTOS;
    }

    private void addProjectConsortiumInstitute(ProjectConsortium projectConsortium, ProjectConsortiumDTO projectConsortiumDTO)
    {
        List<ProjectConsortiumInstituteDTO> projectConsortiumInstituteDTOS =
                projectConsortiumInstituteMapper.toDtos(projectConsortium.getInstitutes());
        projectConsortiumDTO.setProjectConsortiumInstituteDTOS(projectConsortiumInstituteDTOS);
    }

}
