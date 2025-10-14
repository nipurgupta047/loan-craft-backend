package com.loan_craft.upload.modals;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "results")
public class Results {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private long applicationId;
    private int score;
    private String Status;
    private String comment;
    private int statusCode;

    public Results(long applicationId, int score, String status, String comment, int statusCode) {
        this.applicationId = applicationId;
        this.score = score;
        Status = status;
        this.comment = comment;
        this.statusCode = statusCode;
    }
}
