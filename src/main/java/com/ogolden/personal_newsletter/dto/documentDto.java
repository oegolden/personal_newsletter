package com.ogolden.personal_newsletter.dto;
import java.time.LocalDateTime;

public record documentDto(long id, String title, String author, String body, LocalDateTime dt) {
}