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
package org.gentar.biology.plan;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.gentar.biology.outcome.Outcome;
import org.gentar.security.abac.Resource;
import org.gentar.security.abac.ResourcePrivacy;
import org.gentar.BaseEntity;
import org.gentar.biology.plan.attempt.AttemptType;
import org.gentar.biology.plan.attempt.breeding.BreedingAttempt;
import org.gentar.biology.plan.attempt.crispr.CrisprAttempt;
import org.gentar.biology.plan.attempt.phenotyping.PhenotypingAttempt;
import org.gentar.biology.plan.flag.PlanFlag;
import org.gentar.biology.plan.protocol.Protocol;
import org.gentar.biology.project.Project;
import org.gentar.biology.status.Status;
import org.gentar.organization.consortium.Consortium;
import org.gentar.organization.funder.Funder;
import org.gentar.organization.work_unit.WorkUnit;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Plan extends BaseEntity  implements Resource<Plan>
{
    @Id
    @SequenceGenerator(name = "planSeq", sequenceName = "PLAN_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planSeq")
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    private String pin;

    @ToString.Exclude
    @NotNull
    @ManyToOne
    private Project project;

    @ManyToOne(targetEntity = Funder.class)
    private Funder funder;

    @ManyToOne(targetEntity = WorkUnit.class)
    private WorkUnit workUnit;

    @NotNull
    private Boolean isActive;

    @NotNull
    @ManyToOne(targetEntity= Status.class)
    private Status status;

    @ManyToOne(targetEntity= PlanType.class)
    private PlanType planType;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Boolean productsAvailableForGeneralPublic;

    @ManyToMany()
    @JoinTable(
            name = "plan_flag_relation",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_flag_id"))
    private Set<PlanFlag> planFlags;

    @ManyToMany
    @JoinTable(
            name = "plan_protocol",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "protocol_id"))
    private Set<Protocol> protocols;

    @OneToMany
    @JoinColumn(name = "plan_id")
    private Set<Outcome> outcomes;

    // Copy Constructor
    public Plan(Plan plan)
    {
        this.id = plan.id;
        this.pin = plan.pin;
        this.isActive = plan.isActive;
        this.project = plan.project;
        this.planType = plan.planType;
        this.workUnit = plan.workUnit;
        this.funder = plan.funder;
        this.comment = plan.comment;
        this.productsAvailableForGeneralPublic = plan.productsAvailableForGeneralPublic;
        this.planFlags = new HashSet<>(plan.planFlags);
        this.protocols = new HashSet<>(plan.protocols);
        this.status = plan.status;
        this.attemptType = plan.attemptType;
        this.crisprAttempt = plan.crisprAttempt;
        this.breedingAttempt = plan.breedingAttempt;
        this.phenotypingAttempt = plan.phenotypingAttempt;
    }

    @ManyToOne
    private AttemptType attemptType;

    @OneToOne(cascade=CascadeType.ALL, mappedBy = "plan")
    private CrisprAttempt crisprAttempt;

    @ToString.Exclude
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "plan")
    private PhenotypingAttempt phenotypingAttempt;

    @ToString.Exclude
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "plan")
    private BreedingAttempt breedingAttempt;

    @Override
    public String toString()
    {
        return "(id:" +id + ", pin:" + pin + ", type: "+ (this.planType == null ? "Not defined" : planType.getName());
    }

    @Override
    public ResourcePrivacy getResourcePrivacy()
    {
        return project.getResourcePrivacy();
    }

    @Override
    public Resource<Plan> getRestrictedObject()
    {
        return this;
    }

    @Override
    public List<WorkUnit> getRelatedWorkUnits()
    {
        return Arrays.asList(workUnit);
    }

    @Override
    public List<Consortium> getRelatedConsortia()
    {
        return Collections.emptyList();
    }
}
