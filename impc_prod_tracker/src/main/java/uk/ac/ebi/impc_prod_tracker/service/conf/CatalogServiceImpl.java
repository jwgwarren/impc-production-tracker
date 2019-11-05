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
package uk.ac.ebi.impc_prod_tracker.service.conf;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.data.biology.allele_type.AlleleTypeRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.assignment_status.AssignmentStatusRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.mutagenesis_donor.preparation_type.PreparationTypeRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.phenotyping_attempt.material_deposited_type.MaterialDepositedTypeRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.plan.type.PlanTypeRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.privacy.PrivacyRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.species.SpeciesRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.status.StatusRepository;
import uk.ac.ebi.impc_prod_tracker.data.biology.strain.StrainRepository;
import uk.ac.ebi.impc_prod_tracker.data.organization.institute.InstituteRepository;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_group.WorkGroupRepository;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_unit.WorkUnitRepository;
import uk.ac.ebi.impc_prod_tracker.service.biology.project.search.SearchType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CatalogServiceImpl implements CatalogService
{
    private WorkUnitRepository workUnitRepository;
    private WorkGroupRepository workGroupRepository;
    private PlanTypeRepository planTypeRepository;
    private PrivacyRepository privacyRepository;
    private StatusRepository statusRepository;
    private AssignmentStatusRepository assignmentStatusRepository;
    private AlleleTypeRepository alleleTypeRepository;
    private InstituteRepository instituteRepository;
    private StrainRepository strainRepository;
    private PreparationTypeRepository preparationTypeRepository;
    private MaterialDepositedTypeRepository materialDepositedTypeRepository;
    private SpeciesRepository speciesRepository;

    private Map<String, List<String>> conf = new HashMap<>();

    public CatalogServiceImpl(
        WorkUnitRepository workUnitRepository,
        WorkGroupRepository workGroupRepository,
        PlanTypeRepository planTypeRepository,
        PrivacyRepository privacyRepository,
        StatusRepository statusRepository,
        AssignmentStatusRepository assignmentStatusRepository,
        AlleleTypeRepository alleleTypeRepository,
        InstituteRepository instituteRepository,
        StrainRepository strainRepository,
        PreparationTypeRepository preparationTypeRepository,
        MaterialDepositedTypeRepository materialDepositedTypeRepository,
        SpeciesRepository speciesRepository
    )
    {
        this.workUnitRepository = workUnitRepository;
        this.workGroupRepository = workGroupRepository;
        this.planTypeRepository = planTypeRepository;
        this.privacyRepository = privacyRepository;
        this.statusRepository = statusRepository;
        this.assignmentStatusRepository = assignmentStatusRepository;
        this.alleleTypeRepository = alleleTypeRepository;
        this.instituteRepository = instituteRepository;
        this.strainRepository = strainRepository;
        this.preparationTypeRepository = preparationTypeRepository;
        this.materialDepositedTypeRepository = materialDepositedTypeRepository;
        this.speciesRepository = speciesRepository;
    }

    @Override
    public Map<String, List<String>> getCatalog()
    {
        if (conf.isEmpty())
        {
            addWorkUnits();
            addWorkGroups();
            addPlanTypes();
            addPrivacies();
            addStatuses();
            addAssignmentStatuses();
            addAlleleTypes();
            addInstitutes();
            addStrains();
            addMaterialTypes();
            addPreparationTypes();
            addSearchTypes();
            addSpecies();
        }

        return conf;
    }

    private void addWorkUnits()
    {
        List<String> workUnits = new ArrayList<>();
        workUnitRepository.findAll().forEach(p -> workUnits.add(p.getName()));
        conf.put("workUnits", workUnits);
    }

    private void addWorkGroups()
    {
        List<String> workGroups = new ArrayList<>();
        workGroupRepository.findAll().forEach(p -> workGroups.add(p.getName()));
        conf.put("workGroups", workGroups);
    }

    private void addPlanTypes()
    {
        List<String> planTypes = new ArrayList<>();
        planTypeRepository.findAll().forEach(p -> planTypes.add(p.getName()));
        conf.put("planTypes", planTypes);
    }

    private void addPrivacies()
    {
        List<String> privacies = new ArrayList<>();
        privacyRepository.findAll().forEach(p -> privacies.add(p.getName()));
        conf.put("privacies", privacies);
    }

    private void addStatuses()
    {
        List<String> statuses = new ArrayList<>();
        statusRepository.findAll().forEach(p -> statuses.add(p.getName()));
        conf.put("statuses", statuses);
    }

    private void addAssignmentStatuses()
    {
        List<String> assignmentStatuses = new ArrayList<>();
        assignmentStatusRepository.findAll().forEach(p -> assignmentStatuses.add(p.getName()));
        conf.put("assignmentStatuses", assignmentStatuses);

    }

    private void addAlleleTypes()
    {
        List<String> alleleTypes = new ArrayList<>();
        alleleTypeRepository.findAll().forEach(p -> alleleTypes.add(p.getName()));
        conf.put("alleleTypes", alleleTypes);
    }

    private void addInstitutes()
    {
        List<String> institutes = new ArrayList<>();
        instituteRepository.findAll().forEach(p -> institutes.add(p.getName()));
        conf.put("institutes", institutes);
    }

    private void addStrains()
    {
        List<String> trackedStrains = new ArrayList<>();
        strainRepository.findAll().forEach(p -> trackedStrains.add(p.getName()));
        conf.put("trackedStrains", trackedStrains);
    }

    private void addMaterialTypes()
    {
        List<String> materialTypes = new ArrayList<>();
        materialDepositedTypeRepository.findAll().forEach(p -> materialTypes.add(p.getName()));
        conf.put("materialTypes", materialTypes);
    }

    private void addPreparationTypes()
    {
        List<String> preparationTypes = new ArrayList<>();
        preparationTypeRepository.findAll().forEach(p -> preparationTypes.add(p.getName()));
        conf.put("preparationTypes", preparationTypes);
    }

    private void addSearchTypes()
    {
        List<String> searchTypes = SearchType.getValidValuesNames();
        conf.put("searchTypes", searchTypes);
    }

    private void addSpecies()
    {
        List<String> species = new ArrayList<>();
        speciesRepository.findAll().forEach(p -> species.add(p.getName()));
        conf.put("species", species);
    }
}
