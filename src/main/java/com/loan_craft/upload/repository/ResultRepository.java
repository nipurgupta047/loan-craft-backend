package com.loan_craft.upload.repository;

import com.loan_craft.upload.modals.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Results, Long> {
    @Query("SELECT r FROM Results r WHERE r.applicationId = :appId")
    Results findByApplicationId(@Param("appId") Long appId);
}
