package auto.trace.repository;

import auto.trace.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCarIdInAndDateBetween(List<Long> carIds, LocalDate start, LocalDate end);

    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<Expense> findByCarIdAndDateBetween(Long carId, LocalDate start, LocalDate end);

    void deleteByCarId(Long carId);

    boolean existsByCarId(Long carId);

    List<Expense> findByCarId(Long carId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
