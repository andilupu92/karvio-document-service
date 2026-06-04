package karvio.service;

import karvio.dto.request.DocumentPersonalRequest;
import karvio.dto.response.PersonalDocumentResponse;
import karvio.entity.DocumentType;
import karvio.entity.PersonalDocument;
import karvio.exception.ResourceNotFoundException;
import karvio.mapper.PersonalDocumentMapper;
import karvio.repository.DocumentTypeRepository;
import karvio.repository.PersonalDocumentRepository;
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
