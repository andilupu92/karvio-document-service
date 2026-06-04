package karvio.controller;

import karvio.dto.response.ExpenseTypeResponse;
import karvio.service.ExpenseTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenseTypes")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }


    @GetMapping
    public ResponseEntity<List<ExpenseTypeResponse>> getDocumentCategories() {
        return new ResponseEntity<>(expenseTypeService.getExpenseTypes(), HttpStatus.OK);
    }
}
