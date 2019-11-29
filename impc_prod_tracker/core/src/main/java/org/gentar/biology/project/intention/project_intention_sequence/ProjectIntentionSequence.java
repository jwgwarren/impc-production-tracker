package org.gentar.biology.project.intention.project_intention_sequence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gentar.BaseEntity;
import org.gentar.biology.project.intention.project_intention.ProjectIntention;
import org.gentar.biology.sequence.Sequence;
import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class ProjectIntentionSequence extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_intention_id")
    @MapsId
    private ProjectIntention projectIntention;

    @ManyToOne
    private Sequence sequence;

    private Integer index;
}
