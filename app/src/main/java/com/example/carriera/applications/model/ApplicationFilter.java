package com.example.carriera.applications.model;

public enum ApplicationFilter {
    ALL("All"),
    SENT("Sent"),
    WAITING("Waiting"),
    INTERVIEW("Interview");

    public final String label;

    ApplicationFilter(String label) {
        this.label = label;
    }

    public boolean accepts(TrackedApplication application) {
        if (this == ALL) {
            return true;
        }
        if (this == SENT) {
            return application.status == ApplicationStatus.SENT;
        }
        if (this == WAITING) {
            return application.status == ApplicationStatus.WAITING_RESPONSE;
        }
        return application.status == ApplicationStatus.INTERVIEW_SCHEDULED;
    }
}
