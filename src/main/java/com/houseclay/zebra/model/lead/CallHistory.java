package com.houseclay.zebra.model.lead;

import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.rental.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="call_history")
public class CallHistory {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    private UUID call_id;
    private String called_by;
    private LocalDateTime timeStamp;
    private String durationOfTheCall;
    //New, Call Back Later, Schedule Visit, Customer Agreed, Rejected/Denied, Invalid Lead, Onboarded, Others
    private String leadInitialStatus;
    private String leadFinalStatus;
    private String callNotes;


    @Embedded
    private BaseTimeStamp baseTimeStamp;
}
