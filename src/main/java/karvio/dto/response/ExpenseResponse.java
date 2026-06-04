package karvio.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponse (Long id,
                               Long expenseTypeId,
                               String expenseTypeName,
                               String expenseTypeIconName,
                               LocalDateTime date,
                               BigDecimal amount
) { }
