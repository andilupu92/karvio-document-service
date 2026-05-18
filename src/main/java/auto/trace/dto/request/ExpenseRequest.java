package auto.trace.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest (@NotNull(message = "Car id is required")
                              Long carId,

                              @NotNull(message = "ExpenseType is required")
                              Long expenseTypeId,

                              @NotNull(message = "Amount is required")
                              @Digits(integer = 10, fraction = 2)
                              @Positive
                              BigDecimal amount,

                              @NotNull(message = "Date is required")
                              LocalDate date
) {
}
