package az.et.unitech.account.dao.repository;

import az.et.unitech.account.dao.entity.AccountEntity;
import az.et.unitech.account.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findAllByUsernameAndStatus(String username, AccountStatus status);

    Optional<AccountEntity> findByUsernameAndIbanAndStatus(String username, String iban, AccountStatus status);

    Optional<AccountEntity> findByIban(String iban);

}
