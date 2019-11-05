package uk.ac.ebi.impc_prod_tracker.data.biology.crispr_attempt.mutagenesis_strategy.mutagenesis_strategy_type;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
public class MutagenesisStrategyType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "mutagenesisStrategyTypeSeq", sequenceName = "MUTAGENESIS_STRATEGY_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mutagenesisStrategyTypeSeq")
    private Long id;

    private String name;
}
