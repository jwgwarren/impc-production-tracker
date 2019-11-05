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
package uk.ac.ebi.impc_prod_tracker.data.biology.strain;

import lombok.*;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import uk.ac.ebi.impc_prod_tracker.data.biology.strain.strain_type.StrainType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class Strain extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "strainSeq", sequenceName = "STRAIN_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strainSeq")
    private Long id;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String name;

    private String mgiStrainAccId;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "strain_type_relationship",
            joinColumns = @JoinColumn(name = "strain_id"),
            inverseJoinColumns = @JoinColumn(name = "strain_type_id"))
    private Set<StrainType> strainTypes;
}
