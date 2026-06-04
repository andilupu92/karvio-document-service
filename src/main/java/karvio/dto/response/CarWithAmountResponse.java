package karvio.dto.response;

import karvio.dto.response.carClient.CarResponse;

import java.math.BigDecimal;

public record CarWithAmountResponse(CarResponse car, BigDecimal totalAmount) {}