package karvio.controller;

import karvio.dto.response.DocumentTypeResponse;
import karvio.service.DocumentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documentTypes")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }


    @GetMapping("/{category}")
    public ResponseEntity<List<DocumentTypeResponse>>getDocumentCategories(@PathVariable String category) {
        return new ResponseEntity<>(documentTypeService.getDocumentCategories(category), HttpStatus.OK);
    }
}
