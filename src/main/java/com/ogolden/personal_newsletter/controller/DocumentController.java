package com.ogolden.personal_newsletter.controller;

import com.ogolden.personal_newsletter.entity.Document;
import com.ogolden.personal_newsletter.entity.Summary;
import com.ogolden.personal_newsletter.repository.DocumentRepository;
import com.ogolden.personal_newsletter.repository.SummaryRepository;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

/**
 * Controller in charge of dealing with Document related endpoints
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/info")

    public String docMetadata(){
        return "Hello World";
    }

    @GetMapping("/")
    public List<Document> getAllDocuments(@RequestParam(required = false) List<String> tags){
        if(!tags.isEmpty()){
            return documentRepository.findByTagsIn(tags);
        } else{
            return documentRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable long id){
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    @PostMapping("/")
    public ResponseEntity<Document> createDocument(@RequestBody Document document){
        Document document1 = documentRepository.save(document);
        return new ResponseEntity<>(document1, HttpStatus.CREATED);
    }
}