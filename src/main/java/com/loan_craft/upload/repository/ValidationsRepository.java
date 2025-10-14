package com.loan_craft.upload.repository;

import com.loan_craft.upload.modals.Validations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationsRepository extends JpaRepository<Validations, Long> {
    @Query("SELECT v FROM Validations v WHERE v.applicationId = :appId")
    List<Validations> findByApplicationId(@Param("appId") long appId);
}
