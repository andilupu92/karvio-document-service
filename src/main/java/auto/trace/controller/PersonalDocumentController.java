package auto.trace.controller;

import auto.trace.dto.request.DocumentPersonalRequest;
import auto.trace.dto.request.DocumentRequest;
import auto.trace.dto.response.DocumentListWrapper;
import auto.trace.dto.response.DocumentResponse;
import auto.trace.dto.response.PersonalDocumentResponse;
import auto.trace.service.PersonalDocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents/personal")
public class PersonalDocumentController {

    private final PersonalDocumentService personalDocumentService;

    public PersonalDocumentController(PersonalDocumentService personalDocumentService) {
        this.personalDocumentService = personalDocumentService;
    }

    @GetMapping()
    public ResponseEntity<List<PersonalDocumentResponse>> getPersonalDocuments(@RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(personalDocumentService.getPersonalDocuments(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PersonalDocumentResponse> add(@Valid @RequestBody DocumentPersonalRequest documentPersonalRequest,
                                                @RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(personalDocumentService.save(userId, documentPersonalRequest, null), HttpStatus.CREATED);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<PersonalDocumentResponse> update(@Valid @RequestBody DocumentPersonalRequest documentPersonalRequest,
                                                           @RequestHeader("X-User-Id") Long userId,
                                                           @PathVariable Long documentId) {
        return new ResponseEntity<>(personalDocumentService.save(userId, documentPersonalRequest, documentId), HttpStatus.OK);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> delete(@PathVariable Long documentId) {
        personalDocumentService.delete(documentId);
        return ResponseEntity.noContent().build();
    }
}
