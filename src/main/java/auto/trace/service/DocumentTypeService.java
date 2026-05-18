package auto.trace.service;

import auto.trace.dto.response.DocumentTypeResponse;
import auto.trace.mapper.DocumentTypeMapper;
import auto.trace.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    public List<DocumentTypeResponse> getDocumentCategories(String category) {
        return documentTypeMapper.toResponseList(documentTypeRepository.findByCategory(category));
    }
}
