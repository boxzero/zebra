package com.houseclay.zebra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "amenities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Amenities {

    private long amenities_id;
    private boolean lift;
    private boolean club_house;
    private boolean intercom;
    private boolean swimming_pool;
    private boolean children_play_area;
    private boolean fire_safety;
    private boolean security;
    private boolean park;
    private boolean rain_water_harvesting;
    private boolean sewage_treatment_plant;
    private boolean housekeeping;
    private boolean power_backup;



}
