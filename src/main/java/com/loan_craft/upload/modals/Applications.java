package com.loan_craft.upload.modals;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

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

    public Applications(String firstName, String lastName, String email, String permanentAddress, String communicationAddress, String pancardNo, String phoneNo, String occupation, long monthlySalary, long loanAmount, float loanInterest, int loanTenureMonths) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.permanentAddress = permanentAddress;
        this.communicationAddress = communicationAddress;
        this.pancardNo = pancardNo;
        this.phoneNo = phoneNo;
        this.occupation = occupation;
        this.monthlySalary = monthlySalary;
        this.loanAmount = loanAmount;
        this.loanInterest = loanInterest;
        this.loanTenureMonths = loanTenureMonths;
    }

}
