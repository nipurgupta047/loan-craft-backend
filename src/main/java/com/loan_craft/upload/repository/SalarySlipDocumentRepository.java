package com.loan_craft.upload.repository;

import com.loan_craft.upload.modals.SalarySlipDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalarySlipDocumentRepository extends JpaRepository<SalarySlipDocument, Long> {
}
