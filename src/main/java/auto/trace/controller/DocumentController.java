package auto.trace.controller;

import auto.trace.dto.request.DocumentRequest;
import auto.trace.dto.response.DocumentResponse;
import auto.trace.service.DocumentService;
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

    @PostMapping("/{carId}")
    public ResponseEntity<DocumentResponse> add(@Valid @RequestBody DocumentRequest documentRequest,
                                                @PathVariable Long carId) {
        return new ResponseEntity<>(documentService.save(carId, documentRequest, null), HttpStatus.CREATED);
    }

    @PutMapping("/{carId}/{documentId}")
    public ResponseEntity<DocumentResponse> update(@Valid @RequestBody DocumentRequest documentRequest,
                                                   @PathVariable Long carId,
                                                   @PathVariable Long documentId) {
        return new ResponseEntity<>(documentService.save(carId,documentRequest,documentId), HttpStatus.OK);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> delete(@PathVariable Long documentId) {
        documentService.delete(documentId);
        return ResponseEntity.noContent().build();
    }
}
