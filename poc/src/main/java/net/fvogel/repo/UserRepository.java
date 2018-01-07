package net.fvogel.repo;

import net.fvogel.model.User;
import net.fvogel.model.meta.MetaInitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String userName);

}
