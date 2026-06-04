package karvio.dto.response;

import java.math.BigDecimal;

public record CategorySummaryResponse(String categoryName,
                                      BigDecimal totalAmount

) { }
