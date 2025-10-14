package com.loan_craft.upload.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationReq {

    private String firstName;
    private String lastName;
    private String email;
    private String permanentAddress;
    private String communicationAddress;
    private String pancardNo;
    @Size(min=10, max=10)
    private String phoneNo;
    private String occupation;
    private long monthlySalary;
    private long loanAmount;
    private float loanInterest;
    private int loanTenureMonths;

}
