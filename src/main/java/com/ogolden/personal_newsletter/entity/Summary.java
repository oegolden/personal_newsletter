package com.ogolden.personal_newsletter.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Summary Object, tied to a document represents an AI generated summary of the doc
 * Primary key and indexed on ID
 */

@Entity
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String summary;
}
