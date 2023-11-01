package com.houseclay.zebra.model.lead.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public enum LeadSource {
    FACEBOOK("FACEBOOK"),
    INSTAGRAM("INSTAGRAM"),
    REFERENCE("REFERENCE"),
    WEBSITE("WEBSITE");

    public final String label;

    private LeadSource(String label) {
        this.label = label;
    }
}
