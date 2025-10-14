package com.loan_craft.upload.repository;

import com.loan_craft.upload.modals.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Long> {
    @Query("SELECT a FROM Applications a WHERE a.id = :appId AND a.phoneNo = :phoneNo")
    Applications findByAppIdAndPhoneNo(@Param("appId") long appId, @Param("phoneNo") String phoneNo);
}
