package net.fvogel.repo;

import net.fvogel.model.meta.MetaInitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaInitStatusRepository extends JpaRepository<MetaInitStatus, Long> {
}
