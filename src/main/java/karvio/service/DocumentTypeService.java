package karvio.service;

import karvio.dto.response.DocumentTypeResponse;
import karvio.mapper.DocumentTypeMapper;
import karvio.repository.DocumentTypeRepository;
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
