package org.gentar.biology.colony.distribution.distribution_network;

import lombok.*;
import org.gentar.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
public class DistributionNetwork extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "distributionNetworkSeq", sequenceName = "DISTRIBUTION_NETWORK_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distributionNetworkSeq")
    private Long id;

    private String name;
}
