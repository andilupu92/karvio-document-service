package karvio.scheduler;

import karvio.client.NotificationServiceClient;
import karvio.dto.request.NotificationRequest;
import karvio.entity.Document;
import karvio.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DocumentExpiryScheduler {

    private final DocumentRepository documentRepository;
    private final NotificationServiceClient notificationClient;

    // Every day at 08:00 AM
    @Scheduled(cron = "0 00 07 * * ?")
    @Transactional
    public void checkDocumentExpirations() {
        LocalDate today = LocalDate.now();

        processExpirations(today.plusDays(10), " expiră în 10 zile", "Mai ai 10 zile până când documentul pentru mașina ta va expira. Nu uita să îl reînnoiești!");

        processExpirations(today.plusDays(3), " expiră în 3 zile", "Documentul tău auto expiră peste doar 3 zile. Reînnoiește-l cât mai repede!");

        processExpirations(today, " expiră ASTĂZI", "Atenție! Documentul tău auto a expirat astăzi. Evită amenzile și reînnoiește-l acum!");
    }

    private void processExpirations(LocalDate targetDate, String title, String bodyPrefix) {
        List<Document> documents = documentRepository.findByExpiryDateWithType(targetDate);

        for (Document doc : documents) {
            String customizedTitle = doc.getDocumentType().getName() + title;

            NotificationRequest request = new NotificationRequest(
                    doc.getUserId(),
                    customizedTitle,
                    bodyPrefix
            );

            try {
                notificationClient.sendNotification(request,
                        "SYSTEM_SCHEDULER",
                        "ROLE_SYSTEM",
                        "Scheduler_Service");
            } catch (Exception e) {
                System.err.println("Failed to send Feign notification for user: " + doc.getUserId() + e.getMessage());
            }
        }
    }
}
