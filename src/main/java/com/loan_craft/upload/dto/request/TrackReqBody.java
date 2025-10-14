package com.loan_craft.upload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrackReqBody {
    private long appId;
    private String phoneNo;
}
