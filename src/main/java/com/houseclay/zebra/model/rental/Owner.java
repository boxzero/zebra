package com.houseclay.zebra.model.rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "owner")
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    private UUID owner_id;

    private String owner_name;

    private String owner_email;

    private String owner_contact;

    private String owner_pan;

    private String owner_contact_addr;


    private String notes;

    // ----------------- ------------
    private String availability;
    private String startTime;
    private String endTime;


}
