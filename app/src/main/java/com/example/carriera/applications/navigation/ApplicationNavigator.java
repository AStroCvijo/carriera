package com.example.carriera.applications.navigation;

import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.ApplicationStatus;

public interface ApplicationNavigator {
    void showApplicationManager(ApplicationFilter filter);

    void showApplicationDetails(String applicationId);

    void showStatusEditor(String applicationId);

    void updateStatus(String applicationId, ApplicationStatus status);

    void showReminderEditor(String applicationId);

    void saveReminder(String applicationId, String type, String dateTime, String note);

    void showInterviewPrep(String applicationId);

    void showInterviewExample(String applicationId, String question);

    void showInterviewSimulation(String applicationId);
}
