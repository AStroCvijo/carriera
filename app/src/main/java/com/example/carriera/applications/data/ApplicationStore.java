package com.example.carriera.applications.data;

import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.ApplicationStatus;
import com.example.carriera.applications.model.TrackedApplication;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStore {
    private final List<TrackedApplication> applications = new ArrayList<>();

    public static ApplicationStore createDemoStore() {
        ApplicationStore store = new ApplicationStore();
        store.applications.add(new TrackedApplication(
                "java-levi9",
                "Junior Java Developer",
                "Levi9",
                ApplicationStatus.WAITING_RESPONSE,
                "18 May 2026",
                "25 May",
                "Sent CV and cover letter.\nWaiting for HR response.",
                "HR contacted by email.\nNo response yet."
        ));
        store.applications.add(new TrackedApplication(
                "software-microsoft",
                "Software Engineering",
                "Microsoft",
                ApplicationStatus.INTERVIEW_SCHEDULED,
                "19 May 2026",
                "28 May",
                "Technical interview scheduled.",
                "Recruiter sent interview link."
        ));
        store.applications.add(new TrackedApplication(
                "backend-htec",
                "Backend Intern",
                "HTEC",
                ApplicationStatus.SENT,
                "21 May 2026",
                "30 May",
                "Application sent through company portal.",
                "No communication yet."
        ));
        return store;
    }

    public List<TrackedApplication> list(ApplicationFilter filter) {
        List<TrackedApplication> result = new ArrayList<>();
        for (TrackedApplication application : applications) {
            if (filter.accepts(application)) {
                result.add(application);
            }
        }
        return result;
    }

    public List<TrackedApplication> search(ApplicationFilter filter, String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();
        List<TrackedApplication> result = new ArrayList<>();
        for (TrackedApplication application : applications) {
            if (!filter.accepts(application)) {
                continue;
            }
            if (normalizedQuery.isEmpty() || searchableText(application).contains(normalizedQuery)) {
                result.add(application);
            }
        }
        return result;
    }

    public TrackedApplication require(String id) {
        for (TrackedApplication application : applications) {
            if (application.id.equals(id)) {
                return application;
            }
        }
        throw new IllegalArgumentException("Unknown application id: " + id);
    }

    public void updateStatus(String id, ApplicationStatus status) {
        require(id).status = status;
    }

    public void saveReminder(String id, String type, String dateTime, String note) {
        TrackedApplication application = require(id);
        application.reminders.add(new TrackedApplication.ReminderItem(type, dateTime, note));
    }

    private String searchableText(TrackedApplication application) {
        return (application.position + " "
                + application.company + " "
                + application.status.label + " "
                + application.deadline + " "
                + application.nextStep()).toLowerCase();
    }
}
