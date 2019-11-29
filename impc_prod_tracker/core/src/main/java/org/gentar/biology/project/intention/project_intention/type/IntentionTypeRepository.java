package org.gentar.biology.project.intention.project_intention.type;

import org.springframework.data.repository.CrudRepository;

public interface IntentionTypeRepository extends CrudRepository<IntentionType, Long>
{
    IntentionType findFirstByNameIgnoreCase(String name);
}
