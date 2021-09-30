package com.houseclay.zebra.model.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class BaseTimeStamp {

    @Column(nullable = false)
    private String created_by;

    @Column(nullable = false)
    private Date created_on;


    private String changed_by;


    private Date changed_on;

}
