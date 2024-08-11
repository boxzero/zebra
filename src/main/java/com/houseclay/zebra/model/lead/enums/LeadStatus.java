package com.houseclay.zebra.model.lead.enums;

public enum LeadStatus {
    NEW("NEW"),
    CALL_BACK_LATER("CALL_BACK_LATER"),
    VISIT_SCHEDULED("VISIT_SCHEDULED"), // for property owners,sellers
    CUSTOMER_AGREED("CUSTOMER_AGREED"), //for buyers, tenants
    REJECTED("REJECTED"), // lead not converted, dump leads
    INVALID_LEAD("INVALID_LEAD"), // tenant, buyers converted
    ONBOARDED("ONBOARDED"), // owners, sellers property onbaorded
    OTHERS("OTHERS");
    public final String label;

    private LeadStatus(String label) {
        this.label = label;
    }

}
