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
package org.gentar.biology.plan.protocol;

import lombok.*;
import org.gentar.BaseEntity;
import org.gentar.biology.plan.Plan;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Data
@Entity
public class Protocol extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "protocolSeq", sequenceName = "PROTOCOL_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "protocolSeq")
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "protocols")
    private Set<Plan> plans;

    @NotNull
    @ManyToOne(targetEntity= ProtocolType.class)
    private ProtocolType protocolType;
}
