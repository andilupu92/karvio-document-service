package auto.trace.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ExpenseCarsSummaryResponse(String monthName,
                                         BigDecimal totalAmount,
                                         List<CategorySummaryResponse> categorySummaryResponseList,
                                         List<CarWithAmountResponse> carResponsesList
) { }
