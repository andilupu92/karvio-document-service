package auto.trace.repository;

import auto.trace.entity.PersonalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalDocumentRepository extends JpaRepository<PersonalDocument, Long> {
    List<PersonalDocument> findByUserIdOrderByExpiryDateAsc(Long userId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
