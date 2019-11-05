package uk.ac.ebi.impc_prod_tracker.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_group.WorkGroup;
import uk.ac.ebi.impc_prod_tracker.service.organization.WorkUnitService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class WorkGroupController {

    private WorkUnitService workUnitService;

    public WorkGroupController (WorkUnitService workUnitService){
        this.workUnitService = workUnitService;
    }

    @GetMapping(value = {"/workGroups"})
    public Set<WorkGroup> getWorkGroup(@RequestParam String workUnitName)
    {
        return workUnitService.getWorkGroupsByWorkUnitName(workUnitName);
    }

}
