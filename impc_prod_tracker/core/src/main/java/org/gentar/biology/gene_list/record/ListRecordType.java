package org.gentar.biology.gene_list.record;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.biology.gene_list.GeneList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class ListRecordType
{
    @Id
    @SequenceGenerator(name = "listRecordTypeSeq", sequenceName = "LIST_RECORD_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listRecordTypeSeq")
    private Long id;

    private String name;

    @ManyToOne
    private GeneList geneList;

    @ManyToMany(mappedBy = "listRecordTypes")
    private Set<ListRecord> listRecords;

}
