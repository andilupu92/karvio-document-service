package karvio.controller;

import karvio.dto.request.DocumentRequest;
import karvio.dto.response.DocumentListWrapper;
import karvio.dto.response.DocumentResponse;
import karvio.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{carId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsFromCar(@PathVariable Long carId) {
        return new ResponseEntity<>(documentService.getDocumentsFromCar(carId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<DocumentListWrapper> getDocumentsFromUser(@RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(documentService.getDocumentsFromUser(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DocumentResponse> add(@Valid @RequestBody DocumentRequest documentRequest,
                                                @RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(documentService.save(userId, documentRequest, null), HttpStatus.CREATED);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> update(@Valid @RequestBody DocumentRequest documentRequest,
                                                   @RequestHeader("X-User-Id") Long userId,
                                                   @PathVariable Long documentId) {
        return new ResponseEntity<>(documentService.save(userId, documentRequest, documentId), HttpStatus.OK);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> delete(@PathVariable Long documentId) {
        documentService.delete(documentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/byCar/{carId}")
    public ResponseEntity<Void> deleteAllDocumentsByCar(@PathVariable Long carId) {
        documentService.deleteAllDocumentsByCar(carId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/byUser/{userId}")
    public ResponseEntity<Void> deleteAllDocumentsAndExpensesByUser(@PathVariable Long userId) {
        documentService.deleteAllDocumentsAndExpensesByUser(userId);
        return ResponseEntity.noContent().build();
    }
}
