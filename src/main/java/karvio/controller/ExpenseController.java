package karvio.controller;

import karvio.client.CarServiceClient;
import karvio.dto.request.ExpenseRequest;
import karvio.dto.response.ExpenseCarsSummaryResponse;
import karvio.dto.response.ExpenseHistoryResponse;
import karvio.dto.response.ExpenseResponse;
import karvio.dto.response.ExpenseToCarResponse;
import karvio.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CarServiceClient carServiceClient;

    @GetMapping()
    public ResponseEntity<List<ExpenseCarsSummaryResponse>> getExpensesFromUser(@RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(expenseService.getExpensesFromUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{carId}/{months}")
    public ResponseEntity<List<ExpenseHistoryResponse>> getExpensesFromCar(@PathVariable Long carId,
                                                                     @PathVariable Long months) {
        return new ResponseEntity<>(expenseService.getExpensesFromCar(carId, months), HttpStatus.OK);
    }

    @PostMapping("/monthly-average/{carId}")
    public ResponseEntity<BigDecimal> getMonthlyAverage(@PathVariable Long carId,
                                                        @Valid @RequestBody ExpenseRequest expenseRequest,
                                                        @RequestHeader("X-User-Id") Long userId) {
        expenseService.save(userId, expenseRequest);
        return new ResponseEntity<>(expenseService.getMonthlyAverage(carId), HttpStatus.OK);
    }

    @PostMapping("/byCars")
    public ResponseEntity<List<ExpenseToCarResponse>> getExpensesForCars(@RequestBody List<Long> carIds) {
        return new ResponseEntity<>(expenseService.getExpensesForCars(carIds), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ExpenseResponse> add(@Valid @RequestBody ExpenseRequest expenseRequest,
                                                  @RequestHeader("X-User-Id") Long userId) {
        Long carId = expenseRequest.carId();
        ExpenseResponse saved = expenseService.save(userId, expenseRequest);
        BigDecimal monthlyAverage = expenseService.getMonthlyAverage(carId);
        carServiceClient.updateFinancialHealth(monthlyAverage, carId);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/byExpense/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long expenseId) {
        expenseService.delete(expenseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/byCar/{carId}")
    public ResponseEntity<Void> deleteAllExpensesByCar(@PathVariable Long carId) {
        expenseService.deleteAllExpensesByCar(carId);
        return ResponseEntity.noContent().build();
    }
}
