/******************************************************************************
 Copyright 2019 EMBL - European Bioinformatics Institute

 Licensed under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific
 language governing permissions and limitations under the
 License.
 */
package uk.ac.ebi.impc_prod_tracker.service.biology.target_gene_list;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.data.biology.project.Project;
import uk.ac.ebi.impc_prod_tracker.data.biology.target_gene_list.target_group.TargetGroup;
import uk.ac.ebi.impc_prod_tracker.service.biology.project.search.ProjectSearcherService;
import uk.ac.ebi.impc_prod_tracker.service.biology.project.search.Search;
import uk.ac.ebi.impc_prod_tracker.service.biology.project.search.SearchReport;
import uk.ac.ebi.impc_prod_tracker.service.biology.project.search.SearchType;
import uk.ac.ebi.impc_prod_tracker.web.controller.project.helper.ProjectFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProjectsByGroupOfGenesFinder
{
    private ProjectSearcherService projectSearcherService;

    public ProjectsByGroupOfGenesFinder(ProjectSearcherService projectSearcherService)
    {
        this.projectSearcherService = projectSearcherService;
    }

    public List<Project> findProjectsByGenes(Set<TargetGroup> targetGroups)
    {
        Set<Project> projects = new HashSet<>();
        List<String> ids = new ArrayList<>();
        if (targetGroups != null)
        {
            targetGroups.forEach(x -> ids.add(x.getAccId()));
        }
        Search search = new Search(SearchType.BY_GENE.getName(), ids, ProjectFilter.getInstance());
        SearchReport searchReport = projectSearcherService.executeSearch(search);
        if (searchReport.getResults() != null)
        {
            searchReport.getResults().forEach(x -> projects.add(x.getProject()));
        }

        return filterExactMatches(projects);
    }

    private List<Project> filterExactMatches(Set<Project> projects)
    {
        return new ArrayList<>(projects);
    }
}
