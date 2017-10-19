package net.fvogel.persistence;

import net.fvogel.model.Definition;
import org.springframework.data.repository.CrudRepository;

public interface DefinitionRepository extends CrudRepository<Definition, Long> {

    Definition findOneByName(String name);

}
