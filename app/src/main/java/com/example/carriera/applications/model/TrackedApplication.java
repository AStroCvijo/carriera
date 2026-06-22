package com.example.carriera.applications.model;

import java.util.ArrayList;
import java.util.List;

public class TrackedApplication {
    public final String id;
    public final String position;
    public final String company;
    public final String appliedDate;
    public final String deadline;
    public final String notes;
    public final String communication;
    public ApplicationStatus status;
    public final List<ReminderItem> reminders = new ArrayList<>();

    public TrackedApplication(
            String id,
            String position,
            String company,
            ApplicationStatus status,
            String appliedDate,
            String deadline,
            String notes,
            String communication
    ) {
        this.id = id;
        this.position = position;
        this.company = company;
        this.status = status;
        this.appliedDate = appliedDate;
        this.deadline = deadline;
        this.notes = notes;
        this.communication = communication;
    }

    public String nextStep() {
        switch (status) {
            case PLANNED:
                return "Finish and send application";
            case SENT:
                return "Wait for response";
            case WAITING_RESPONSE:
                return "Send follow-up";
            case INTERVIEW_SCHEDULED:
                return "Prepare for interview";
            case ACCEPTED:
                return "Confirm next steps";
            case REJECTED:
            default:
                return "Archive application";
        }
    }

    public String detailedNextStep() {
        switch (status) {
            case PLANNED:
                return "Finish CV and cover letter\nbefore the deadline.";
            case SENT:
                return "Wait for response from HR.";
            case WAITING_RESPONSE:
                return "Send follow-up if there is\nno response in 3 days.";
            case INTERVIEW_SCHEDULED:
                return "Open interview preparation\nand review suggested questions.";
            case ACCEPTED:
                return "Confirm next steps with HR\nand prepare documents.";
            case REJECTED:
            default:
                return "Archive application and\nreview feedback if available.";
        }
    }

    public boolean hasReminder() {
        return !reminders.isEmpty();
    }

    public ReminderItem latestReminder() {
        if (reminders.isEmpty()) {
            return null;
        }
        return reminders.get(reminders.size() - 1);
    }

    public static class ReminderItem {
        public final String type;
        public final String dateTime;
        public final String note;

        public ReminderItem(String type, String dateTime, String note) {
            this.type = type;
            this.dateTime = dateTime;
            this.note = note;
        }
    }
}
