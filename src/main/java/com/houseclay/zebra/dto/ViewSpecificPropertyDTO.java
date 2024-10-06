package com.houseclay.zebra.dto;

import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.common.PropertySpecs;
import com.houseclay.zebra.model.rental.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ViewSpecificPropertyDTO {

    private UUID property_id;
    private String name;
    private String title;
    private boolean isManaged; //false by default
    private PropertySpecs propertySpecs;
    private boolean active_status; // false by default
    private String inactive_reason; // null
    private Date posted_on;
    private Date available_from;
    private double property_rent;
    private double property_maintenance;
    private List<String> preferred_tenant_type;
    private String propertyFor;
    private Double  expected_Deposit;
    private boolean rent_negotiable;
    private String who_will_show_the_property;
    private String showProperty_contact;
    private String availability;
    private String startTime;
    private String endTime;
    private BaseTimeStamp baseTimeStamp;


}
