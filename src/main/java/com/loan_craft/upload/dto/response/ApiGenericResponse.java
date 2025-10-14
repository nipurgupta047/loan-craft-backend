package com.loan_craft.upload.dto.response;

import lombok.Data;

@Data
public class ApiGenericResponse<T> {

    private boolean success;
    private String successMessage;
    private String errorMessage;
    private T result;

    public ApiGenericResponse(){
    }

    public ApiGenericResponse(boolean success, String successMessage, String errorMessage, T result) {
        this.success = success;
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
        this.result = result;
    }

}
