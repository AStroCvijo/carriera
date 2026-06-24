package com.example.carriera.applications.navigation;

import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.ApplicationStatus;

public interface ApplicationNavigator {
    void showApplicationManager(ApplicationFilter filter);

    void showRecommendedJobs(String filter);

    void showRecommendedJobDetails(String jobId);

    void showMatchAnalysis(String jobId);

    void showMatchAnalysisError(String jobId);

    void showLearningResources(String jobId);

    void showApplicationDetails(String applicationId);

    void showStatusEditor(String applicationId);

    void updateStatus(String applicationId, ApplicationStatus status);

    void showReminderEditor(String applicationId);

    void saveReminder(String applicationId, String type, String dateTime, String note);

    void showInterviewPrep(String applicationId);

    void showInterviewExample(String applicationId, String question);

    void showInterviewSimulation(String applicationId);

    void showGenerateApplication();

    void generateDocuments(boolean includeTailoredCv, boolean includeCoverLetter, String additionalNote);

    void showDocumentsGenerated();

    void showCvPreview();

    void showCoverLetterPreview();

    void updateGeneratedCv(String content);

    void updateGeneratedCoverLetter(String content);

    void submitGeneratedApplication();

    void openGeneratedApplicationTracking();
}
