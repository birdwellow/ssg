package net.fvogel.repo;

import net.fvogel.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserName(String userName);

    Account findByEmail(String email);

}
