package karvio.client;

import karvio.dto.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "karvio-notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notification/saveAndSendNotification")
    void sendNotification(@RequestBody NotificationRequest request);
}
