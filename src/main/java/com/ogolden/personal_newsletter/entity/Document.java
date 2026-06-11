package com.ogolden.personal_newsletter.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class documentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String author;

    @Column(nullable = false)
    private String title;

    @Column(name = "date_written")
    private LocalDateTime dateWritten;

    @Column(name = "date_scraped")
    private LocalDateTime DateScraped;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String source;

    @OneToOne()
    @Column(name = "summary")
    private long summaryId;
}
