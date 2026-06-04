package karvio.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DocumentRequest(@NotNull(message = "Car id is required")
                              Long carId,

                              @NotNull(message = "Document Type is required")
                              Long documentTypeId,

                              @NotNull(message = "ExpiryDate is required")
                              LocalDate expiryDate,

                              BigDecimal amount,

                              LocalDate insertDate
) { }
