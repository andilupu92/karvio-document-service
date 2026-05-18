package auto.trace.dto.response;

import auto.trace.dto.response.carClient.CarResponse;

import java.math.BigDecimal;

public record CarWithAmountResponse(CarResponse car, BigDecimal totalAmount) {}