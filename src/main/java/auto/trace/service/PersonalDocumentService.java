package auto.trace.service;

import auto.trace.dto.request.DocumentPersonalRequest;
import auto.trace.dto.request.ExpenseRequest;
import auto.trace.dto.response.PersonalDocumentResponse;
import auto.trace.entity.Document;
import auto.trace.entity.DocumentType;
import auto.trace.entity.PersonalDocument;
import auto.trace.exception.ResourceNotFoundException;
import auto.trace.mapper.PersonalDocumentMapper;
import auto.trace.repository.DocumentTypeRepository;
import auto.trace.repository.PersonalDocumentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalDocumentService {

    private final PersonalDocumentRepository personalDocumentRepository;
    private final PersonalDocumentMapper personalDocumentMapper;
    private final DocumentTypeRepository documentTypeRepository;

    public List<PersonalDocumentResponse> getPersonalDocuments(Long userId) {
        return personalDocumentMapper.toResponseList(personalDocumentRepository.findByUserIdOrderByExpiryDateAsc(userId));
    }

    public PersonalDocumentResponse save(Long userId, DocumentPersonalRequest documentPersonalRequest, Long documentId) {
        PersonalDocument personalDocument;

        if (documentId != null) {
            personalDocument = personalDocumentRepository.findById(documentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + documentId));
            personalDocumentMapper.updateEntityFromRequest(documentPersonalRequest, personalDocument);
        } else {
            personalDocument = personalDocumentMapper.toEntity(documentPersonalRequest);
        }

        DocumentType documentType = documentTypeRepository
                .findById(documentPersonalRequest.documentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document Type not found: " + documentPersonalRequest.documentTypeId()));
        personalDocument.setDocumentType(documentType);
        personalDocument.setUserId(userId);

        return personalDocumentMapper.toResponse(personalDocumentRepository.save(personalDocument));
    }

    public void delete(Long documentId) {
        if (!personalDocumentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }
        personalDocumentRepository.deleteById(documentId);
    }
}
