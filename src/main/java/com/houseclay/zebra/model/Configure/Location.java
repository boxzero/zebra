package com.houseclay.zebra.model.Configure;


import com.houseclay.zebra.model.common.BaseTimeStamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="location")
public class Location {

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
        private UUID locationId;
        private String locationName;
        private String pinCode;
        private String city;
        @Embedded
        private BaseTimeStamp baseTimeStamp;

}
