package com.loan_craft.upload.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan_craft.upload.dto.request.LoanApplicationReq;
import com.loan_craft.upload.dto.request.TrackReqBody;
import com.loan_craft.upload.dto.response.ApiGenericResponse;
import com.loan_craft.upload.dto.response.TrackResBody;
import com.loan_craft.upload.service.LoanApplicationService;
import com.loan_craft.upload.service.TrackApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    LoanApplicationService loanApplicationService;

    @Autowired
    TrackApplicationService trackApplicationService;

    @PostMapping(value = "/loan/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiGenericResponse<Object>> applicationApply(
            @RequestPart("userDetails") String loanApplicationReqStr,
            @RequestPart("pancard" )MultipartFile pancard,
            @RequestPart("salarySlip") MultipartFile salarySlip) throws Exception {
        byte[] pancardBytes = pancard.getBytes();
        byte[] salarySlipBytes = salarySlip.getBytes();
        ObjectMapper mapper = new ObjectMapper();
        LoanApplicationReq loanApplicationReq = mapper.readValue(loanApplicationReqStr, LoanApplicationReq.class);

        long applicationId = loanApplicationService.applicationApply(loanApplicationReq,
                pancard, salarySlip, pancardBytes, salarySlipBytes);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiGenericResponse<>(true, "Application submitted", null, applicationId));
    }

    @PostMapping(value = "/loan/track")
    public ResponseEntity<ApiGenericResponse<Object>> trackApplication(@RequestBody TrackReqBody reqBody){

        TrackResBody res = trackApplicationService.trackApplication(reqBody.getAppId(), reqBody.getPhoneNo());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiGenericResponse<>(true, "Application submitted", null, res));
    }

}
