package karvio.repository;

import karvio.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCarIdOrderByExpiryDateAsc(Long carId);

    List<Document> findByUserIdOrderByExpiryDateAsc(Long userId);

    boolean existsByCarId(Long carId);

    void deleteByCarId(Long carId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Document> findAllByExpiryDate(LocalDate expiryDate);

    @Query("SELECT d FROM Document d JOIN FETCH d.documentType WHERE d.expiryDate = :date")
    List<Document> findByExpiryDateWithType(@Param("date") LocalDate date);
}
