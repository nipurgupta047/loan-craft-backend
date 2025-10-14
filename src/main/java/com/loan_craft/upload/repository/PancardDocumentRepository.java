package com.loan_craft.upload.repository;

import com.loan_craft.upload.modals.PancardDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PancardDocumentRepository extends JpaRepository<PancardDocument, Long> {
}
