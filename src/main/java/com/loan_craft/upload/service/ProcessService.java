package com.loan_craft.upload.service;

import com.loan_craft.upload.dto.response.ApplicationProcessReq;
import com.loan_craft.upload.exception.CustomException;
import com.loan_craft.upload.modals.Results;
import com.loan_craft.upload.modals.Validations;
import com.loan_craft.upload.repository.ResultRepository;
import com.loan_craft.upload.repository.ValidationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {

    @Autowired
    private ValidationsRepository validationsRepo;

    @Autowired
    private ResultRepository resultRepo;

    public Boolean validateApplication(ApplicationProcessReq applicationProcessReq, String panCard, Integer monthlySal) {
        boolean pancardValid = applicationProcessReq.getPancardNo().equalsIgnoreCase(panCard);
        boolean salarySlipValid = applicationProcessReq.getMonthlySalary() == monthlySal;
        Validations pancardValidation = new Validations(
                applicationProcessReq.getApplicationId(),
                "PANCARD",
                pancardValid
        );

        Validations salarySlipValidation = new Validations(
                applicationProcessReq.getApplicationId(),
                "SALARY SLIP",
                salarySlipValid
        );

        int loanAmountScore = 0; // out of 80
        long monthlySalary = applicationProcessReq.getMonthlySalary();
        long loanAmount = applicationProcessReq.getLoanAmount();
        float loanRate = applicationProcessReq.getLoanInterest();
        int tenureMonths = applicationProcessReq.getLoanTenureMonths();
        float emi = ((loanAmount*loanRate*tenureMonths)/(12*100) + loanAmount)/tenureMonths;

        if(2*emi < monthlySalary && monthlySalary<80000){
            loanAmountScore = 40;
        }
        else if(2*emi < monthlySalary && monthlySalary<100000){
            loanAmountScore = 50;
        }
        else if(2*emi < monthlySalary && monthlySalary<120000){
            loanAmountScore = 60;
        }
        else if(2*emi < monthlySalary && monthlySalary<160000){
            loanAmountScore = 70;
        }
        else if(2*emi < monthlySalary){
            loanAmountScore = 75;
        }

        if(3*emi < monthlySalary && monthlySalary<80000){
            loanAmountScore = 65;
        }
        else if(3*emi < monthlySalary && monthlySalary<100000){
            loanAmountScore = 70;
        }
        else if(3*emi < monthlySalary){
            loanAmountScore = 75;
        }

        int totalScore = (loanAmountScore*60)/100; //round off to 60
        if(pancardValid)
            totalScore+=20;
        if(salarySlipValid)
            totalScore+=20;

        String comment = "";
        String status = "";
        int statusCode = 0;
        if(totalScore > 80){
            comment = "Your application has a great score against the application, as next step you will receive a call on the registered mobile no from the bank, in 3 working days.";
            status = "System Approved";
            statusCode = 1;
        }
        else if(totalScore > 60){
            comment = "Your application has an average score against the application, it will be reviewed manually, and if required you will receive a call on the registered mobile no from the bank, in 3 working days.";
            status = "Manual validation";
            statusCode = 2;
        }
        else{
            comment = "Your application has low score against the application, therefore the loan cannot be approved.";
            status = "Rejected";
            statusCode = 3;
        }

        Results applicationResult = new Results(applicationProcessReq.getApplicationId(),totalScore, status, comment, statusCode);

        try{
            validationsRepo.saveAll(List.of(pancardValidation, salarySlipValidation));
            resultRepo.save(applicationResult);
            return true;
        }
        catch (Exception e){
            throw new CustomException("Application failed to save. Please try again!");
        }

    }
}
