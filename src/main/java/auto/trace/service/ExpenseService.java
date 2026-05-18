package auto.trace.service;

import auto.trace.client.CarServiceClient;
import auto.trace.dto.request.ExpenseRequest;
import auto.trace.dto.response.*;
import auto.trace.dto.response.carClient.CarResponse;
import auto.trace.entity.Expense;
import auto.trace.entity.ExpenseType;
import auto.trace.exception.ResourceNotFoundException;
import auto.trace.mapper.ExpenseMapper;
import auto.trace.repository.ExpenseRepository;
import auto.trace.repository.ExpenseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CarServiceClient carServiceClient;

    public List<ExpenseHistoryResponse> getExpensesFromCar(Long carId, Long months) {

        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(months).withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> expenses = expenseRepository.findByCarIdAndDateBetween(carId, start, end);

        return expenses.stream()
                .collect(Collectors.groupingBy(e -> YearMonth.from(e.getDate())))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    YearMonth ym = entry.getKey();
                    List<Expense> monthExpenses = entry.getValue();

                    // Month name
                    String monthName = ym.getMonth()
                            .getDisplayName(TextStyle.FULL, Locale.of("ro"));

                    // Amount
                    BigDecimal totalAmount = monthExpenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    List<ExpenseResponse> newExpenses = monthExpenses.stream()
                            .map(e -> new ExpenseResponse(e.getId(),
                                    e.getExpenseType().getId(),
                                    e.getExpenseType().getName(),
                                    e.getExpenseType().getIconName(),
                                    e.getDate().atStartOfDay(),
                                    e.getAmount()))
                            .toList();

                    return new ExpenseHistoryResponse(monthName, totalAmount, newExpenses);
                })
                .toList();

    }

    public List<ExpenseToCarResponse> getExpensesForCars(List<Long> carIds) {

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> expenses = expenseRepository.findByCarIdInAndDateBetween(carIds, start, end);
        return expenseMapper.toCarResponseList(expenses);
    }

    @Transactional
    public ExpenseResponse save(Long userId, ExpenseRequest expenseRequest) {
        Expense expense;
        expense = expenseMapper.toEntity(expenseRequest);

        ExpenseType expenseType = expenseTypeRepository
                .findById(expenseRequest.expenseTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseType not found: " + expenseRequest.expenseTypeId()));
        expense.setExpenseType(expenseType);

        expense.setUserId(userId);
        return expenseMapper.toResponse(expenseRepository.save(expense));
    }

    public void delete(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new ResourceNotFoundException("Expense not found with id: " + expenseId);
        }
        expenseRepository.deleteById(expenseId);
    }

    public List<ExpenseCarsSummaryResponse> getExpensesFromUser(Long userId) {

        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(5).withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, start, end);

        List<Long> carsIds = expenses.stream()
                .map(Expense::getCarId)
                .distinct()
                .toList();

        List<CarResponse> cars = carServiceClient.getDetailsCars(carsIds);

        Map<Long, CarResponse> carMap = cars.stream()
                .collect(Collectors.toMap(
                        CarResponse::id,
                        car -> car,
                        (existing, replacement) -> existing
                ));

        return expenses.stream()
                .collect(Collectors.groupingBy(e -> YearMonth.from(e.getDate())))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    YearMonth ym = entry.getKey();
                    List<Expense> monthExpenses = entry.getValue();

                    Map<Long, BigDecimal> sumByCarId = monthExpenses.stream()
                            .collect(Collectors.groupingBy(
                                    Expense::getCarId,
                                    Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                            ));

                    // Month name
                    String monthName = ym.getMonth()
                            .getDisplayName(TextStyle.FULL, Locale.of("ro"));

                    // Amount
                    BigDecimal totalAmount = monthExpenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    List<CarWithAmountResponse> carsInMonth = sumByCarId.entrySet().stream()
                            .map(carEntry -> new CarWithAmountResponse(
                                    carMap.get(carEntry.getKey()),
                                    carEntry.getValue()
                            ))
                            .filter(c -> c.car() != null)
                            .toList();

                    // top 3 categories
                    List<CategorySummaryResponse> categories = monthExpenses.stream()
                            .collect(Collectors.groupingBy(
                                    e -> e.getExpenseType().getName(),
                                    Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                            ))
                            .entrySet().stream()
                            .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                            .limit(3)
                            .map(e -> new CategorySummaryResponse(e.getKey(), e.getValue()))
                            .toList();

                    return new ExpenseCarsSummaryResponse(monthName, totalAmount, categories, carsInMonth);
                })
                .toList();
    }

    @Transactional
    public void deleteAllExpensesByCar(Long carId) {
        if (expenseRepository.existsByCarId(carId)) {
            expenseRepository.deleteByCarId(carId);
        }
    }

    public BigDecimal getMonthlyAverage(Long carId) {
        List<Expense> expenses = expenseRepository.findByCarId(carId);

        if (expenses.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Map<YearMonth, List<Expense>> expensesByMonth = expenses.stream()
                .collect(Collectors.groupingBy(e -> YearMonth.from(e.getDate())));

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total
                .divide(BigDecimal.valueOf(expensesByMonth.size()), 2, RoundingMode.HALF_UP);
    }
}
