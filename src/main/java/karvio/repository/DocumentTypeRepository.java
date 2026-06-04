package karvio.repository;

import karvio.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    List<DocumentType> findByCategory(String category);
}
