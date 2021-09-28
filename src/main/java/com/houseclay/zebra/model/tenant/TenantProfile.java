package com.houseclay.zebra.model.tenant;

import com.houseclay.zebra.model.nationalid.NationalID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TenantProfile {

    private String firstName;
    private String lastName;
    private String emailId;
    private NationalID nationalID;
    private String contactNumber;
    private String permanentAddress;


}
