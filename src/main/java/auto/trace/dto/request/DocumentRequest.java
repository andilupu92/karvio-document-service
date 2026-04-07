package auto.trace.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DocumentRequest(@NotNull(message = "Category is required")
                              Long documentCategoryId,

                              @NotNull(message = "ExpiryDate is required")
                              LocalDate expiryDate
) { }
