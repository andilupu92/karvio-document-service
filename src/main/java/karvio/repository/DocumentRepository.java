package karvio.repository;

import karvio.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCarIdOrderByExpiryDateAsc(Long carId);

    List<Document> findByUserIdOrderByExpiryDateAsc(Long userId);

    boolean existsByCarId(Long carId);

    void deleteByCarId(Long carId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
