package com.ogolden.personal_newsletter;
import java.time.LocalDateTime;

public record document(long id, String title, String Author, String Body, String DateTime) {
}