package auto.trace.service;

import auto.trace.dto.response.DocumentCategoryResponse;
import auto.trace.mapper.DocumentCategoryMapper;
import auto.trace.repository.DocumentCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentCategoryService {

    private final DocumentCategoryRepository documentCategoryRepository;
    private final DocumentCategoryMapper documentCategoryMapper;

    public DocumentCategoryService(DocumentCategoryRepository documentCategoryRepository, DocumentCategoryMapper documentCategoryMapper) {
        this.documentCategoryRepository = documentCategoryRepository;
        this.documentCategoryMapper = documentCategoryMapper;
    }

    public List<DocumentCategoryResponse> getDocumentCategories() {
        return documentCategoryMapper.toResponseList(documentCategoryRepository.findAll());
    }
}
