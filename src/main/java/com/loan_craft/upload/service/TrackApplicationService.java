package com.loan_craft.upload.service;

import com.loan_craft.upload.dto.response.TrackResBody;
import com.loan_craft.upload.exception.CustomException;
import com.loan_craft.upload.modals.Applications;
import com.loan_craft.upload.modals.Results;
import com.loan_craft.upload.modals.Validations;
import com.loan_craft.upload.repository.ApplicationsRepository;
import com.loan_craft.upload.repository.ResultRepository;
import com.loan_craft.upload.repository.ValidationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackApplicationService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private ValidationsRepository validationsRepository;

    @Autowired
    private ResultRepository resultRepository;

    public TrackResBody trackApplication(long appId, String phoneNo){
        Applications matchedApplication = applicationsRepository.findByAppIdAndPhoneNo(appId, phoneNo);
        if(matchedApplication == null){
            throw new CustomException("No application found");
        }
        List<Validations> validationsList = validationsRepository.findByApplicationId(appId);
        Results result = resultRepository.findByApplicationId(appId);

        return new TrackResBody(validationsList, result);
    }

}
