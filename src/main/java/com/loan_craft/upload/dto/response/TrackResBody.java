package com.loan_craft.upload.dto.response;

import com.loan_craft.upload.modals.Results;
import com.loan_craft.upload.modals.Validations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackResBody {
    private List<Validations> validation;
    private Results result;
}
