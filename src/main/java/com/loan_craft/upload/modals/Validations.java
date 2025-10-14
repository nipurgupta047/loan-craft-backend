package com.loan_craft.upload.modals;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "validations")
public class Validations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private long applicationId;
    private String validationField;
    private Boolean validationSuccess;

    public Validations(long applicationId, String validationField, Boolean validationSuccess) {
        this.applicationId = applicationId;
        this.validationField = validationField;
        this.validationSuccess = validationSuccess;
    }
}
