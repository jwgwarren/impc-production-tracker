package org.gentar.biology.project.search.filter;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ProjectFilter
{
    private Map<FilterTypes, List<String>> filters;

    ProjectFilter()
    {
        filters = new HashMap<>();
    }

    public static final ProjectFilter getInstance()
    {
        return new ProjectFilter();
    }

    public Map<String, List<String>> getNotNullFilterNames()
    {
        return filters.entrySet().stream()
            .filter(x -> x.getValue() != null)
            .collect(Collectors.toMap(x -> x.getKey().getName(), x -> x.getValue()));
    }

    public List<String> getTpns()
    {
        return filters.getOrDefault(FilterTypes.TPN, null);
    }

    public List<String> getMarkerSymbols()
    {
        return filters.getOrDefault(FilterTypes.MARKER_SYMBOL, null);
    }

    public List<String> getGenes()
    {
        return filters.getOrDefault(FilterTypes.GENE, null);
    }

    public List<String> getIntentions()
    {
        return filters.getOrDefault(FilterTypes.INTENTION, null);
    }

    public List<String> getWorkUnitNames()
    {
        return filters.getOrDefault(FilterTypes.WORK_UNIT_NAME, null);
    }

    public List<String> getWorGroupNames()
    {
        return filters.getOrDefault(FilterTypes.WORK_GROUP_NAME, null);
    }

    public List<String> getConsortiaNames()
    {
        return filters.getOrDefault(FilterTypes.CONSORTIUM, null);
    }

    public List<String> getStatusesNames()
    {
        return filters.getOrDefault(FilterTypes.ASSIGNMENT_STATUS, null);
    }

    public List<String> getPrivaciesNames()
    {
        return filters.getOrDefault(FilterTypes.PRIVACY_NAME, null);
    }
}
