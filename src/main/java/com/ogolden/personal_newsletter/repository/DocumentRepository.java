package com.ogolden.personal_newsletter.repository;

import com.ogolden.personal_newsletter.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    public List<Document> findByTagsIn(List<String> tags);
}