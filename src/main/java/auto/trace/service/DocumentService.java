package auto.trace.service;

import auto.trace.dto.request.DocumentRequest;
import auto.trace.dto.response.DocumentResponse;
import auto.trace.entity.Document;
import auto.trace.entity.DocumentCategory;
import auto.trace.exception.ResourceNotFoundException;
import auto.trace.mapper.DocumentMapper;
import auto.trace.repository.DocumentCategoryRepository;
import auto.trace.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentCategoryRepository documentCategoryRepository;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentCategoryRepository documentCategoryRepository, DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentCategoryRepository = documentCategoryRepository;
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    public List<DocumentResponse> getDocumentsFromCar(Long carId) {
        return documentMapper.toResponseList(documentRepository.findByCarId(carId));
    }

    @Transactional
    public DocumentResponse save(Long carId, DocumentRequest documentRequest, Long documentId) {
        Document document;

        if (documentId != null) {
            document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + documentId));
            documentMapper.updateEntityFromRequest(documentRequest, document);
        } else {
            document = documentMapper.toEntity(documentRequest);
        }

        DocumentCategory category = documentCategoryRepository
                .findById(documentRequest.documentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + documentRequest.documentCategoryId()));
        document.setDocumentCategory(category);

        document.setCarId(carId);
        return documentMapper.toResponse(documentRepository.save(document));
    }

    public void delete(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }
        documentRepository.deleteById(documentId);
    }
}
