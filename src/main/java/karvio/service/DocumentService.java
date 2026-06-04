package karvio.service;

import karvio.client.CarServiceClient;
import karvio.dto.request.DocumentRequest;
import karvio.dto.request.ExpenseRequest;
import karvio.dto.response.DocumentListWrapper;
import karvio.dto.response.DocumentResponse;
import karvio.dto.response.carClient.CarResponse;
import karvio.entity.Document;
import karvio.entity.DocumentType;
import karvio.exception.ResourceNotFoundException;
import karvio.mapper.DocumentMapper;
import karvio.repository.DocumentTypeRepository;
import karvio.repository.DocumentRepository;
import karvio.repository.ExpenseRepository;
import karvio.repository.PersonalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final ExpenseService expenseService;
    private final CarServiceClient carServiceClient;
    private final ExpenseRepository expenseRepository;
    private final PersonalDocumentRepository personalDocumentRepository;

    public List<DocumentResponse> getDocumentsFromCar(Long carId) {
        return documentMapper.toResponseList(documentRepository.findByCarIdOrderByExpiryDateAsc(carId));
    }

    @Transactional
    public DocumentResponse save(Long userId, DocumentRequest documentRequest, Long documentId) {
        Document document;

        if (documentId != null) {
            document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + documentId));
            documentMapper.updateEntityFromRequest(documentRequest, document);
        } else {
            document = documentMapper.toEntity(documentRequest);
        }

        DocumentType documentType = documentTypeRepository
                .findById(documentRequest.documentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document Type not found: " + documentRequest.documentTypeId()));
        document.setDocumentType(documentType);
        document.setUserId(userId);

        if (documentRequest.amount() != null) {
            var expenseRequest = new ExpenseRequest(documentRequest.carId(),
                    documentType.getExpenseType().getId(), documentRequest.amount(), documentRequest.insertDate());
            expenseService.save(userId, expenseRequest);
        }
        return documentMapper.toResponse(documentRepository.save(document));
    }

    public void delete(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }
        documentRepository.deleteById(documentId);
    }

    public DocumentListWrapper getDocumentsFromUser(Long userId) {

        List<Document> documents = documentRepository.findByUserIdOrderByExpiryDateAsc(userId);

        List<Long> carsIds = documents.stream()
                .map(Document::getCarId)
                .distinct()
                .toList();

        List<CarResponse> cars = carServiceClient.getDetailsCars(carsIds);

        Map<Long, CarResponse> carMap = cars.stream()
                .collect(Collectors.toMap(
                        CarResponse::id,
                        car -> car,
                        (existing, replacement) -> existing
                ));

        LocalDate today = LocalDate.now();

        List<DocumentResponse> dtoList = documents.stream()
                .map(doc -> {

                    String carName = carMap.containsKey(doc.getCarId())
                            ? carMap.get(doc.getCarId()).name()
                            : "Car sold";

                    long daysRemaining = ChronoUnit.DAYS.between(today, doc.getExpiryDate());

                    return new DocumentResponse(
                            doc.getId(),
                            doc.getDocumentType().getId(),
                            doc.getDocumentType().getName(),
                            doc.getDocumentType().getIconName(),
                            doc.getExpiryDate(),
                            daysRemaining,
                            carName,
                            doc.getCarId()
                    );
                })
                .toList();

        long urgent = dtoList.stream().filter(d -> d.daysRemaining() <= 3).count();
        long soon = dtoList.stream().filter(d -> d.daysRemaining() > 3 && d.daysRemaining() <= 10).count();
        long valid = dtoList.stream().filter(d -> d.daysRemaining() > 10).count();

        return new DocumentListWrapper(urgent, soon, valid, dtoList);
    }

    @Transactional
    public void deleteAllDocumentsByCar(Long carId) {
        if (documentRepository.existsByCarId(carId)) {
            documentRepository.deleteByCarId(carId);
        }
    }

    @Transactional
    public void deleteAllDocumentsAndExpensesByUser(Long userId) {
        if (documentRepository.existsByUserId(userId)) {
            documentRepository.deleteByUserId(userId);
        }

        if (expenseRepository.existsByUserId(userId)) {
            expenseRepository.deleteByUserId(userId);
        }

        if (personalDocumentRepository.existsByUserId(userId)) {
            personalDocumentRepository.deleteByUserId(userId);
        }
    }
}
