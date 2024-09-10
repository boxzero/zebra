package com.houseclay.zebra.dto.AddPropertyDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class ScheduleDTO {
    private String availability;
    private String startTime;
    private String endTime;


}

