package com.loan_craft.upload.service;

import com.loan_craft.upload.dto.request.LoanApplicationReq;
import com.loan_craft.upload.dto.response.ApplicationProcessReq;
import com.loan_craft.upload.exception.CustomException;
import com.loan_craft.upload.modals.Applications;
import com.loan_craft.upload.modals.PancardDocument;
import com.loan_craft.upload.modals.SalarySlipDocument;
import com.loan_craft.upload.repository.ApplicationsRepository;
import com.loan_craft.upload.repository.SalarySlipDocumentRepository;
import com.loan_craft.upload.repository.PancardDocumentRepository;
import com.loan_craft.upload.utils.CloudinaryUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class LoanApplicationService {

    @Autowired
    private ApplicationsRepository applicationsRepo;

    @Autowired
    private SalarySlipExtractService salarySlipExtractService;

    @Autowired
    private SalarySlipDocumentRepository salarySlipDocumentRepo;

    @Autowired
    private PancardDocumentRepository pancardDocumentRepo;

    @Autowired
    private PanCardExtractService panCardExtractService;

    @Autowired
    private ProcessService processService;

    private static Integer formatSalary(String incomeStr){
        if (incomeStr == null || incomeStr.isBlank()) return null;
        String cleaned = incomeStr.replace(",", "");

        int dot = cleaned.indexOf('.');
        if (dot >= 0) {
            cleaned = cleaned.substring(0, dot);
        }
        return Integer.valueOf(cleaned);
    }

    public long applicationApply(LoanApplicationReq loanApplicationReq, MultipartFile pancard, MultipartFile salarySlip, byte[] pancardBytes, byte[] salarySlipBytes) throws Exception{
        System.out.println("entered apply service");
        Applications newApplication = new Applications(
                loanApplicationReq.getFirstName(),
                loanApplicationReq.getLastName(),
                loanApplicationReq.getEmail(),
                loanApplicationReq.getPermanentAddress(),
                loanApplicationReq.getCommunicationAddress(),
                loanApplicationReq.getPancardNo(),
                loanApplicationReq.getPhoneNo(),
                loanApplicationReq.getOccupation(),
                loanApplicationReq.getMonthlySalary(),
                loanApplicationReq.getLoanAmount(),
                loanApplicationReq.getLoanInterest(),
                loanApplicationReq.getLoanTenureMonths());
        System.out.println("calling app save");
        Applications savedApplication = applicationsRepo.save(newApplication);
        System.out.println("finished app save");
        Map<String, String> pancardResult = panCardExtractService.processPdf(pancard, pancardBytes);
        System.out.println("finished pan extract");
        CloudinaryUpload cloudinaryUpload = new CloudinaryUpload();
        System.out.println("couldinary pan uplaod");
        String pancardSecureUrl = cloudinaryUpload.uploadPancard(pancard);
        System.out.println("cloudinary pan upload finsihed");
        PancardDocument pancardUpload = new PancardDocument(savedApplication.getId(), pancardResult.get("panNumber"), pancardResult.get("name"), pancardResult.get("dateOfBirth"), pancardSecureUrl);
        System.out.println("save pan card doc");
        pancardDocumentRepo.save(pancardUpload);
        System.out.println("save pan card doc finished");
        Map<String,String> salarySlipResult = salarySlipExtractService.processPdf(salarySlip, salarySlipBytes);
        String salarySlipSecureUrl = cloudinaryUpload.uploadSalarySlip(salarySlip);
        SalarySlipDocument salarySlipUpload = new SalarySlipDocument(savedApplication.getId(), salarySlipResult.get("name"), formatSalary(salarySlipResult.get("salary")), salarySlipSecureUrl);
        salarySlipDocumentRepo.save(salarySlipUpload);

        ApplicationProcessReq applicationProcessReq = new ApplicationProcessReq(
                pancardUpload.getId(),
                salarySlipUpload.getId(),
                savedApplication.getId(),
                loanApplicationReq.getFirstName(),
                loanApplicationReq.getLastName(),
                loanApplicationReq.getPancardNo(),
                loanApplicationReq.getMonthlySalary(),
                loanApplicationReq.getLoanAmount(),
                pancardSecureUrl,
                salarySlipSecureUrl,
                loanApplicationReq.getLoanInterest(),
                loanApplicationReq.getLoanTenureMonths()
        );

        if(processService.validateApplication(applicationProcessReq, pancardUpload.getPancardNumber(), salarySlipUpload.getMonthlySalary()))
            return savedApplication.getId();
        else
            throw new CustomException("Application validation process failed. Please try again!");

    }
}
