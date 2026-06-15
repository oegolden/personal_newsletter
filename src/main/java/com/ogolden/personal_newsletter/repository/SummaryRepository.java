package com.ogolden.personal_newsletter.repository;

import com.ogolden.personal_newsletter.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
}