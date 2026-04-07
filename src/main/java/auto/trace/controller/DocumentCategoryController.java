package auto.trace.controller;

import auto.trace.dto.response.DocumentCategoryResponse;
import auto.trace.service.DocumentCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documentCategories")
public class DocumentCategoryController {

    private final DocumentCategoryService documentCategoryService;

    public DocumentCategoryController(DocumentCategoryService documentCategoryService) {
        this.documentCategoryService = documentCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentCategoryResponse>>getDocumentCategories() {
        return new ResponseEntity<>(documentCategoryService.getDocumentCategories(), HttpStatus.OK);
    }
}
