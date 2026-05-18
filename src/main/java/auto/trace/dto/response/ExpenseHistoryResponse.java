package auto.trace.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ExpenseHistoryResponse(String monthName,
                                     BigDecimal totalAmount,
                                     List<ExpenseResponse> expenseResponseList
) { }