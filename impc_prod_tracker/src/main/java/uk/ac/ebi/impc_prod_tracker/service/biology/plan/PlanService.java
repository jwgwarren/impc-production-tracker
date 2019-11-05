/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.impc_prod_tracker.service.biology.plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uk.ac.ebi.impc_prod_tracker.data.common.history.History;
import uk.ac.ebi.impc_prod_tracker.web.dto.plan.PlanDTO;
import uk.ac.ebi.impc_prod_tracker.data.biology.plan.Plan;

import java.util.List;

public interface PlanService
{
    Page<Plan> getPlans(Pageable pageable, List<String> tpns, List<String> workUnitNames);

    Plan getPlanByPinWithoutCheckPermissions(String pin);

    /**
     * Get the production plan that is related with a phenotype plan. The relation is given by the
     * parent colony of the phenotype plan which should be the colony of one of the outcomes of the
     * production plan.
     * @param phenotypePlan The phenotype plan.
     * @return The related production plan.
     */
    Plan getProductionPlanRefByPhenotypePlan(Plan phenotypePlan);

    Page<Plan> getPlansBySpec(Specification<Plan> specification, Pageable pageable);

    Plan getNotNullPlanByPin(String pin);

    /**
     * Updates a plan.
     * @param pin Identifier of the plan.
     * @param planDTO Values to update.
     */
    History updatePlan(String pin, PlanDTO planDTO);

    /**
     * Gets the history for a plan
     * @param plan The plan.
     * @return List of {@link History} with the trace of the changes for a plan.
     */
    List<History> getPlanHistory(Plan plan);

}
