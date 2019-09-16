package uk.ac.ebi.impc_prod_tracker.web.controller.project;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.conf.error_management.OperationFailedException;
import uk.ac.ebi.impc_prod_tracker.data.biology.project.Project;
import uk.ac.ebi.impc_prod_tracker.service.project.ProjectService;

@Component
public class ProjectUtilities
{
    private static final String PROJECT_NOT_FOUND_ERROR = "Project %s does not exist.";
    private static ProjectService projectService;

    public ProjectUtilities(ProjectService projectService)
    {
        this.projectService = projectService;
    }

    public static Project getNotNullProjectByTpn(String tpn)
    {
        Project project = projectService.getProjectByTpn(tpn);
        if (project == null)
        {
            throw new OperationFailedException(
                String.format(PROJECT_NOT_FOUND_ERROR, tpn), HttpStatus.NOT_FOUND);
        }
        return project;
    }
}
