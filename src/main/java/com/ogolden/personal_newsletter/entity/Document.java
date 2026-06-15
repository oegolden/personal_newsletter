package com.ogolden.personal_newsletter.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Document object used to represent a range of documents from newsletters to papers
 * Has id, author, datewritten, datescraped, link, source, tags, summary, and related documents
 * Indexed on the document ID as that will be the main source of lookups
 */


@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(nullable = false)
    private String title;

    @Column(name = "date_written")
    private LocalDateTime dateWritten;

    @Column(name = "date_scraped")
    private LocalDateTime dateScraped;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private List<String> tags = new ArrayList<>();

    @OneToOne
    private Summary summary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "document_relations",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "related_document_id")
    )
    private Set<Document> relatedDocuments = new HashSet<Document>();
}
