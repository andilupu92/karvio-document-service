package karvio.dto.response;

import java.util.List;

public record DocumentListWrapper(
        long urgentCount,    // <= 3 days
        long soonCount,      // 3 - 10 days
        long validCount,     // > 10 days
        List<DocumentResponse> documents
) {}