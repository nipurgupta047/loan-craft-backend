package com.loan_craft.upload.modals;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "salarySlipDocument")
public class SalarySlipDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private long applicationId;
    private String name;
    private Integer monthlySalary;
    private String url;

    public SalarySlipDocument(long applicationId, String name, Integer monthlySalary, String url) {
        this.applicationId = applicationId;
        this.name = name;
        this.monthlySalary = monthlySalary;
        this.url = url;
    }
}
