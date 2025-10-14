package com.loan_craft.upload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProcessReq {

    private long pancardId;
    private long salarySlipId;
    private long applicationId;
    private String firstName;
    private String lastName;
    private String pancardNo;
    private long monthlySalary;
    private long loanAmount;
    private String pancardUrl;
    private String salarySlipUrl;
    private float loanInterest;
    private int loanTenureMonths;

}
