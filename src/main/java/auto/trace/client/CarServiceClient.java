package auto.trace.client;

import auto.trace.dto.response.carClient.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "auto-car-service")
public interface CarServiceClient {

    @PostMapping("/cars/byExpenses")
    List<CarResponse> getDetailsCars(@RequestBody List<Long> carIds);

    @PostMapping("/cars/financialHealth/{monthlyAverage}/{carId}")
    void updateFinancialHealth(@PathVariable BigDecimal monthlyAverage,
                               @PathVariable Long carId);
}