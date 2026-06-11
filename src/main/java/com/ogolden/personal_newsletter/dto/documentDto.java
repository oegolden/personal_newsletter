package com.ogolden.personal_newsletter.dto;
import java.time.LocalDateTime;

public record document(long id, String title, String author, String body, LocalDateTime dt) {
}