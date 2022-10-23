package az.et.unitech.account.dao.repository;

import az.et.unitech.account.dao.entity.TransferHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferHistoryRepository extends JpaRepository<TransferHistoryEntity, Long> {

}
