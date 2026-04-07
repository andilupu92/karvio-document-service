package auto.trace.dto.response;

import java.time.LocalDate;

public record DocumentResponse (Long id,
                                Long documentCategoryId,
                                String documentCategoryName,
                                String documentCategoryIconName,
                                String documentCategoryIconLibrary,
                                LocalDate expiryDate,
                                long daysRemaining
){ }
