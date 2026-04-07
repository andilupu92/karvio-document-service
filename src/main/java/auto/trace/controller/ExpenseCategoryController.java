package auto.trace.controller;

import auto.trace.dto.response.ExpenseCategoryResponse;
import auto.trace.service.ExpenseCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenseCategories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponse>> getDocumentCategories() {
        return new ResponseEntity<>(expenseCategoryService.getExpenseCategories(), HttpStatus.OK);
    }
}
