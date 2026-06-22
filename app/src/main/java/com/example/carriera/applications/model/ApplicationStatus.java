package com.example.carriera.applications.model;

public enum ApplicationStatus {
    PLANNED("Planned"),
    SENT("Sent"),
    WAITING_RESPONSE("Waiting response"),
    INTERVIEW_SCHEDULED("Interview scheduled"),
    REJECTED("Rejected"),
    ACCEPTED("Accepted");

    public final String label;

    ApplicationStatus(String label) {
        this.label = label;
    }
}
