package uk.ac.ebi.impc_prod_tracker.service.biology.plan.engine;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.common.history.HistoryService;
import uk.ac.ebi.impc_prod_tracker.conf.security.abac.spring.ContextAwarePolicyEnforcement;

import uk.ac.ebi.impc_prod_tracker.data.biology.plan.Plan;
import uk.ac.ebi.impc_prod_tracker.data.biology.plan.PlanRepository;

import uk.ac.ebi.impc_prod_tracker.data.common.history.History;

@Component
public class PlanUpdaterImpl implements PlanUpdater
{
    private HistoryService<Plan> historyService;
    private ContextAwarePolicyEnforcement policyEnforcement;
    private PlanRepository planRepository;
    private PlanValidator planValidator;

    public PlanUpdaterImpl(
        HistoryService<Plan> historyService,
        ContextAwarePolicyEnforcement policyEnforcement,
        PlanRepository planRepository,
        PlanValidator planValidator)
    {
        this.historyService = historyService;
        this.policyEnforcement = policyEnforcement;
        this.planRepository = planRepository;
        this.planValidator = planValidator;
    }

    @Override
    public History updatePlan(Plan originalPlan, Plan newPlan)
    {
        historyService.setEntityData(Plan.class.getSimpleName(), originalPlan.getId());
        validatePermissionToUpdatePlan(newPlan);
        validateData(newPlan);
        History history = detectTrackOfChanges(originalPlan, newPlan);
        changeStatusIfNeeded(newPlan);
        saveChanges(newPlan);
        saveTrackOfChanges(history);
        return history;
    }

    /**
     * Check if the current logged user has permission to update the plan p.
     * @param plan Plan being updated.
     */
    private void validatePermissionToUpdatePlan(Plan plan)
    {
        policyEnforcement.checkPermission(plan, "UPDATE_PLAN");
    }

    /**
     * Check if the changes in the plan require a change on the status.
     * @param plan Plan being updated.
     */
    private void changeStatusIfNeeded(Plan plan)
    {
        System.out.println("Changing status");
    }

    /**
     * Validates that the changes are valid.
     */
    private void validateData(Plan newPlan)
    {
        planValidator.validate(newPlan);
    }

    /**
     * Save the changes into the database for the specific plan.
     * @param plan Plan being updated.
     */
    private void saveChanges(Plan plan)
    {
        System.out.println("Saving changes");
        planRepository.save(plan);
    }

    /**
     * Detects the track of the changes between originalPlan and newPlan.
     * @param originalPlan The plan before the update.
     * @param newPlan The updated plan.
     */
    private History detectTrackOfChanges(Plan originalPlan, Plan newPlan)
    {
        History historyEntry = historyService.detectTrackOfChanges(originalPlan, newPlan);
        return historyEntry;
    }

    /**
     * Stores the track of the changes.
     * @param history
     */
    private void saveTrackOfChanges(History history)
    {
        historyService.saveTrackOfChanges(history);
    }
}
