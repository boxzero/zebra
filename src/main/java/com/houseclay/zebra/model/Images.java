package com.houseclay.zebra.model;

import javax.persistence.*;

/**
 * Images Size should be designed to fit in houseclay portal
 */
@Entity
@Table(name = "images")
public class Images {

    @Id
    private long image_id;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String original_image;


}
