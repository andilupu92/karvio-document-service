package karvio.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DocumentPersonalRequest(
                                    @NotNull(message = "Document Type is required")
                                    Long documentTypeId,

                                    @NotNull(message = "ExpiryDate is required")
                                    LocalDate expiryDate
) { }
