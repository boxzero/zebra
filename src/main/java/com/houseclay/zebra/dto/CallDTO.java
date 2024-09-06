package com.houseclay.zebra.dto;

import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CallDTO {

    private String durationOfTheCall;
    private String leadInitialStatus;
    private String leadFinalStatus;
    private String callNotes;

//    private UUID leadOwnerId;
//    private UUID leadBuyerId;
//    private UUID leadSellerId;
//    private UUID leadTenantId;


}
