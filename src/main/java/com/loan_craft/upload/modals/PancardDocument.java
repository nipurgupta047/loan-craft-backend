package com.loan_craft.upload.modals;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pancardDocument")
public class PancardDocument {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private long applicationId;
    private String pancardNumber;
    private String name;
    private String dateOfBirth;
    private String url;

    public PancardDocument(long applicationId, String pancardNumber, String name, String dateOfBirth, String url) {
        this.applicationId = applicationId;
        this.pancardNumber = pancardNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.url = url;
    }

}
