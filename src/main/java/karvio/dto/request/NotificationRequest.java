package karvio.dto.request;

public record NotificationRequest(Long userId,
                                  String title,
                                  String body
) { }
