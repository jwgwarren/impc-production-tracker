package uk.ac.ebi.impc_prod_tracker.data.biology.assignment_status;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.ac.ebi.impc_prod_tracker.data.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Data
@Entity
public class AssignmentStatus extends BaseEntity
{
    @Id
    @SequenceGenerator(name = "assignmentStatusSeq", sequenceName = "ASSIGNMENT_STATUS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentStatusSeq")
    private Long id;

    private String name;

    private String description;
}
