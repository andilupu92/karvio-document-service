package auto.trace.dto.response.carClient;

import java.math.BigDecimal;

public record CarResponse(Long id,
                          String name,
                          String energyType,
                          Integer kilometers,
                          Integer year,
                          BigDecimal consumption,
                          Integer health
) { }