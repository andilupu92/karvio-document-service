package karvio.client;

import karvio.dto.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "karvio-notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notification/saveAndSendNotification")
    void sendNotification(@RequestBody NotificationRequest request,
                          @RequestHeader("X-User-Id") String userId,
                          @RequestHeader("X-User-Roles") String roles,
                          @RequestHeader("X-User-Name") String username);
}
