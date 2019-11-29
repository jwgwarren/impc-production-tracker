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
package org.gentar.biology.gene_list;

import org.gentar.exceptions.UserOperationFailedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.gentar.biology.gene_list.record.GeneByGeneListRecord;
import org.gentar.biology.gene_list.record.GeneListRecord;
import org.gentar.biology.gene_list.record.GeneListRecordRepository;
import org.gentar.biology.gene_list.record.SortGeneByGeneListRecordByIndex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class GeneListRecordService
{
    private GeneListRecordRepository geneListRecordRepository;

    public GeneListRecordService(GeneListRecordRepository geneListRecordRepository)
    {
        this.geneListRecordRepository = geneListRecordRepository;
    }

    public GeneListRecord getGeneListRecordById(Long id)
    {
        return geneListRecordRepository.findById(id).orElse(null);
    }

    public Page<GeneListRecord> getAllByConsortium(Pageable pageable, String consortiumName)
    {
        return geneListRecordRepository.findAllByGeneListConsortiumName(pageable, consortiumName);
    }

    public String genesByRecordToString(Collection<GeneByGeneListRecord> genes)
    {
        StringBuilder result = new StringBuilder();
        if (genes != null)
        {
            genes.forEach(x -> {
                result.append(x.getAccId()).append("-");
            } );
        }
        return result.toString();
    }

    private String getAccIdHashByGeneListRecord(GeneListRecord geneListRecord)
    {
        var genes = new ArrayList<>(geneListRecord.getGenesByRecord());
        genes.sort(new SortGeneByGeneListRecordByIndex());
        return genesByRecordToString(genes);
    }

    /**
     *
     * @param geneListRecord The new record in the a gene list.
     * @param geneRecordHashes A list of strings with the genes present in the list
     *                                  so the search for duplicates can be done quicker.
     * @param geneSymbols The label to show if an exception occurs.
     */
    public void validateNewRecord(
        GeneListRecord geneListRecord, Map<String, Long> geneRecordHashes, String geneSymbols)
    {
        List<GeneByGeneListRecord> genes = new ArrayList<>(geneListRecord.getGenesByRecord());
        genes.sort(new SortGeneByGeneListRecordByIndex());
        String hashNewRecord = getAccIdHashByGeneListRecord(geneListRecord);
        Long id = geneRecordHashes.get(hashNewRecord);
        if (id != null && !id.equals(geneListRecord.getId()))
        {
            throw new UserOperationFailedException(
                "Gene(s) [" + geneSymbols + "] already in this list.");
        }
    }
}
