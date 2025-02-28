package org.gentar.biology.sequence.type;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
public class SequenceType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "sequenceTypeSeq", sequenceName = "SEQUENCE_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceTypeSeq")
    private Long id;

    private String name;

}
