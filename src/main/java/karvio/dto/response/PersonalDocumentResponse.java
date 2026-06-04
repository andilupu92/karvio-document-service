package karvio.dto.response;

import java.time.LocalDate;

public record PersonalDocumentResponse(Long id,
                                       Long documentTypeId,
                                       String documentTypeName,
                                       String documentTypeIconName,
                                       LocalDate expiryDate,
                                       long daysRemaining
) {}
