package com.houseclay.zebra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CallHistoryDTO {


    private String called_by;
    private LocalDateTime timeStamp;
    private String durationOfTheCall;
    private String leadInitialStatus;
    private String leadFinalStatus;
    private String callNotes;

}
