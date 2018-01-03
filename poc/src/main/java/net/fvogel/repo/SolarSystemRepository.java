package net.fvogel.repo;

import net.fvogel.model.SolarSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolarSystemRepository extends JpaRepository<SolarSystem, Long> {
}
