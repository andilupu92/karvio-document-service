package auto.trace.repository;

import auto.trace.entity.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentCategoryRepository extends JpaRepository<DocumentCategory, Long> {
}
