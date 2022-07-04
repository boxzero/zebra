package com.houseclay.zebra.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class BaseTimeStamp {

    @Column(nullable = false)
    private String created_by;

    @Column(nullable = false)
    private Date created_on;


    private String changed_by;


    private Date changed_on;

}
