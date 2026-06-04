package karvio.repository;

import karvio.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
}
