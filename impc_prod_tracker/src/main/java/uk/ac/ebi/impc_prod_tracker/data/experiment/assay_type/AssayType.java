package uk.ac.ebi.impc_prod_tracker.data.experiment.assay_type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Getter
@Setter
@Entity
public class AssayType
{
    @Id
    @SequenceGenerator(name = "assayTypeSeq", sequenceName = "ASSAY_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assayTypeSeq")
    private Long id;

    private String name;
}
