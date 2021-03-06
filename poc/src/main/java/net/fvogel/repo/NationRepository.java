package net.fvogel.repo;

import net.fvogel.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {

    Nation findOneByName(String name);

}
