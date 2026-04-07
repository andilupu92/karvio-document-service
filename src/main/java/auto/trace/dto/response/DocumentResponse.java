package auto.trace.dto.response;

import java.time.LocalDate;

public record DocumentResponse (Long id,
                                Long documentCategoryId,
                                String documentCategoryName,
                                LocalDate expiryDate
){ }
