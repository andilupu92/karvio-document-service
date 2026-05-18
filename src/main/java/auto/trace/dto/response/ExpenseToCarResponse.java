package auto.trace.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseToCarResponse(Long id,
                                   Long carId,
                                   LocalDateTime date,
                                   BigDecimal amount
) { }
