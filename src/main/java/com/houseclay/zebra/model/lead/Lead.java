package com.houseclay.zebra.model.lead;

import com.houseclay.zebra.model.common.BaseTimeStamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "tbl_leads")
public class Lead {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    private UUID lead_id;

    private String firstName;

    private String lastName;

    private String emailId;

    private String contactNumber;

    private String preferredAreas; // Enter a String with '-' and use split by while processing

    private String notes;

    private Date preferredOccupancyDate;

    private Boolean isLeadConverted;

    private Boolean isLeadTrashed; //Unconverted Leads , Leads to be Blocked

    private String trashedReason;

    private String leadSource; //Facebook, Online Sites , Reference , Zebra Web App

    @Embedded
    private BaseTimeStamp baseTimeStamp;


}
