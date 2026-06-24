package com.example.carriera;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.example.carriera.account.data.AccountStore;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.account.screens.BasicInfoScreen;
import com.example.carriera.account.screens.CvManagerScreen;
import com.example.carriera.account.screens.EducationScreen;
import com.example.carriera.account.screens.ExperienceScreen;
import com.example.carriera.account.screens.HomeLoggedInScreen;
import com.example.carriera.account.screens.HomeScreen;
import com.example.carriera.account.screens.JobPrefsScreen;
import com.example.carriera.account.screens.LoginScreen;
import com.example.carriera.account.screens.ProfileScreen;
import com.example.carriera.account.screens.RegisterScreen;
import com.example.carriera.account.screens.SkillsScreen;
import com.example.carriera.applications.data.ApplicationStore;
import com.example.carriera.applications.model.ApplicationDraft;
import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.ApplicationStatus;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.screens.ApplicationSavedScreen;
import com.example.carriera.applications.screens.CoverLetterPreviewScreen;
import com.example.carriera.applications.screens.CvPreviewScreen;
import com.example.carriera.applications.screens.DocumentsGeneratedScreen;
import com.example.carriera.applications.screens.GenerateApplicationScreen;
import com.example.carriera.applications.screens.ApplicationDetailsScreen;
import com.example.carriera.applications.screens.ApplicationManagerScreen;
import com.example.carriera.applications.screens.InterviewExampleScreen;
import com.example.carriera.applications.screens.InterviewPrepScreen;
import com.example.carriera.applications.screens.InterviewSimulationScreen;
import com.example.carriera.applications.screens.ReminderScreen;
import com.example.carriera.applications.screens.StatusScreen;
import com.example.carriera.jobs.data.RecommendedJobStore;
import com.example.carriera.jobs.screens.JobDetailsScreen;
import com.example.carriera.jobs.screens.LearningResourcesScreen;
import com.example.carriera.jobs.screens.MatchAnalysisScreen;
import com.example.carriera.jobs.screens.RecommendedJobsScreen;

public class MainActivity extends Activity implements ApplicationNavigator, AccountNavigator {
    private static final int REQUEST_CV = 1001;
    private ApplicationStore store;
    private AccountStore accountStore;
    private RecommendedJobStore jobStore;
    private String currentApplicationId;
    private ApplicationDraft currentDraft;
    private Runnable currentBack;
    private boolean cvOnboarding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = ApplicationStore.createDemoStore();
        accountStore = AccountStore.createDemoStore();
        jobStore = RecommendedJobStore.createDemoStore();
        com.example.carriera.applications.ui.AppViews.setHomeAction(() -> {
            if (accountStore.isLoggedIn()) {
                showHomeLoggedIn();
            } else {
                showHome();
            }
        });
        showHome();
    }

    // ---- Account / profile flow ----

    @Override
    public void showHome() {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = null;
        setContentView(HomeScreen.create(this, this));
    }

    @Override
    public void showLogin() {
        currentBack = this::showHome;
        setContentView(LoginScreen.create(this, this, false));
    }

    @Override
    public void showLoginError() {
        currentBack = this::showHome;
        setContentView(LoginScreen.create(this, this, true));
    }

    @Override
    public void showRegister() {
        currentBack = this::showHome;
        setContentView(RegisterScreen.create(this, this, false));
    }

    private void showRegisterError() {
        currentBack = this::showHome;
        setContentView(RegisterScreen.create(this, this, true));
    }

    @Override
    public void doLogin(String email, String password) {
        if (accountStore.login(email, password)) {
            showHomeLoggedIn();
        } else {
            showLoginError();
        }
    }

    @Override
    public void doRegister(String firstName, String lastName, String email, String password, String confirmPassword) {
        boolean valid = !firstName.trim().isEmpty()
                && !lastName.trim().isEmpty()
                && email.contains("@")
                && !password.isEmpty()
                && password.equals(confirmPassword);
        if (!valid) {
            showRegisterError();
            return;
        }
        accountStore.register(firstName, lastName, email, password);
        showBasicInfo(true);
    }

    @Override
    public void showHomeLoggedIn() {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = null;
        setContentView(HomeLoggedInScreen.create(
                this,
                this,
                () -> showApplicationManager(ApplicationFilter.ALL),
                () -> showRecommendedJobs("All"),
                () -> Toast.makeText(this, "Open an application to prepare for its interview", Toast.LENGTH_SHORT).show()));
    }

    @Override
    public void logout() {
        accountStore.logout();
        showHome();
    }

    @Override
    public void showProfile() {
        currentBack = this::showHomeLoggedIn;
        setContentView(ProfileScreen.create(this, accountStore.profile(), this));
    }

    @Override
    public void showBasicInfo(boolean onboarding) {
        currentBack = onboarding ? this::showRegister : this::showProfile;
        setContentView(BasicInfoScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void showEducation(boolean onboarding) {
        currentBack = () -> showBasicInfo(onboarding);
        setContentView(EducationScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void showExperience(boolean onboarding) {
        currentBack = () -> showEducation(onboarding);
        setContentView(ExperienceScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void showSkills(boolean onboarding) {
        currentBack = () -> showExperience(onboarding);
        setContentView(SkillsScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void showJobPrefs(boolean onboarding) {
        currentBack = () -> showSkills(onboarding);
        setContentView(JobPrefsScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void showCvManager(boolean onboarding) {
        currentBack = onboarding ? () -> showJobPrefs(true) : this::showProfile;
        setContentView(CvManagerScreen.create(this, accountStore.profile(), this, onboarding, false));
    }

    @Override
    public void uploadCv(boolean onboarding) {
        cvOnboarding = onboarding;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        });
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        try {
            startActivityForResult(intent, REQUEST_CV);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No file picker available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openCv() {
        UserProfile profile = accountStore.profile();
        if (profile.cvUri == null || profile.cvUri.isEmpty()) {
            Toast.makeText(this, "No CV uploaded yet", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(profile.cvUri));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app can open this file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CV) {
            return;
        }
        if (resultCode != RESULT_OK || data == null || data.getData() == null) {
            return; // user cancelled
        }
        Uri uri = data.getData();
        String name = queryDisplayName(uri);
        long size = querySize(uri);
        String lower = name == null ? "" : name.toLowerCase();
        boolean validType = lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx");
        boolean validSize = size <= 5 * 1024 * 1024; // 5 MB limit
        UserProfile profile = accountStore.profile();
        if (!validType || !validSize) {
            setContentView(com.example.carriera.account.screens.CvManagerScreen.create(this, profile, this, cvOnboarding, true));
            return;
        }
        try {
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (SecurityException ignored) {
        }
        profile.cvUploaded = true;
        profile.cvUri = uri.toString();
        profile.cvFileName = name;
        setContentView(com.example.carriera.account.screens.CvManagerScreen.create(this, profile, this, cvOnboarding, false));
    }

    private String queryDisplayName(Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index >= 0) {
                    return cursor.getString(index);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private long querySize(Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (index >= 0 && !cursor.isNull(index)) {
                    return cursor.getLong(index);
                }
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    @Override
    public void finishOnboarding() {
        showHomeLoggedIn();
    }

    // ---- Application tracking flow ----

    @Override
    public void showApplicationManager(ApplicationFilter filter) {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = this::showHomeLoggedIn;
        setContentView(ApplicationManagerScreen.create(this, store, filter, this));
    }

    @Override
    public void showRecommendedJobs(String filter) {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = this::showHomeLoggedIn;
        setContentView(RecommendedJobsScreen.create(this, jobStore, filter, this));
    }

    @Override
    public void showRecommendedJobDetails(String jobId) {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = () -> showRecommendedJobs("All");
        setContentView(JobDetailsScreen.create(this, jobStore.require(jobId), accountStore.profile(), this, this));
    }

    @Override
    public void showMatchAnalysis(String jobId) {
        UserProfile profile = accountStore.profile();
        if (!profile.cvUploaded || !isProfileComplete(profile)) {
            Toast.makeText(this, "Complete your CV and profile before match analysis", Toast.LENGTH_SHORT).show();
            showRecommendedJobDetails(jobId);
            return;
        }
        currentApplicationId = null;
        currentDraft = null;
        currentBack = () -> showRecommendedJobDetails(jobId);
        setContentView(MatchAnalysisScreen.create(this, jobStore.require(jobId), this));
    }

    @Override
    public void showMatchAnalysisError(String jobId) {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = () -> showRecommendedJobDetails(jobId);
        setContentView(MatchAnalysisScreen.error(this, jobId, this));
    }

    @Override
    public void showLearningResources(String jobId) {
        currentApplicationId = null;
        currentDraft = null;
        currentBack = () -> showMatchAnalysis(jobId);
        setContentView(LearningResourcesScreen.create(this, jobId, this));
    }

    @Override
    public void showApplicationDetails(String applicationId) {
        currentApplicationId = applicationId;
        setContentView(ApplicationDetailsScreen.create(this, store.require(applicationId), this));
    }

    @Override
    public void showStatusEditor(String applicationId) {
        currentApplicationId = applicationId;
        setContentView(StatusScreen.create(this, store.require(applicationId), this));
    }

    @Override
    public void updateStatus(String applicationId, ApplicationStatus status) {
        store.updateStatus(applicationId, status);
        showApplicationDetails(applicationId);
    }

    @Override
    public void showReminderEditor(String applicationId) {
        currentApplicationId = applicationId;
        setContentView(ReminderScreen.create(this, store.require(applicationId), this));
    }

    @Override
    public void saveReminder(String applicationId, String type, String dateTime, String note) {
        store.saveReminder(applicationId, type, dateTime, note);
        showApplicationDetails(applicationId);
    }

    @Override
    public void showInterviewPrep(String applicationId) {
        currentApplicationId = applicationId;
        setContentView(InterviewPrepScreen.create(this, store.require(applicationId), this));
    }

    @Override
    public void showInterviewExample(String applicationId, String question) {
        currentApplicationId = applicationId;
        setContentView(InterviewExampleScreen.create(this, store.require(applicationId), question, this));
    }

    @Override
    public void showInterviewSimulation(String applicationId) {
        currentApplicationId = applicationId;
        setContentView(InterviewSimulationScreen.create(this, store.require(applicationId), this));
    }

    private void startGenerateApplication() {
        currentDraft = ApplicationDraft.demo();
        showGenerateApplication();
    }

    @Override
    public void showGenerateApplication() {
        currentApplicationId = null;
        ensureDraft();
        currentBack = this::showHomeLoggedIn;
        setContentView(GenerateApplicationScreen.create(this, currentDraft, this));
    }

    @Override
    public void generateDocuments(boolean includeTailoredCv, boolean includeCoverLetter, String additionalNote) {
        ensureDraft();
        currentDraft.includeTailoredCv = includeTailoredCv;
        currentDraft.includeCoverLetter = includeCoverLetter;
        currentDraft.additionalNote = additionalNote;
        currentDraft.generateSelectedDocuments();
        showDocumentsGenerated();
    }

    @Override
    public void showDocumentsGenerated() {
        showDocumentsGenerated(false);
    }

    private void showDocumentsGenerated(boolean showValidationWarning) {
        currentApplicationId = null;
        ensureDraft();
        currentBack = this::showGenerateApplication;
        setContentView(DocumentsGeneratedScreen.create(this, currentDraft, this, showValidationWarning));
    }

    @Override
    public void showCvPreview() {
        currentApplicationId = null;
        ensureDraft();
        currentBack = this::showDocumentsGenerated;
        setContentView(CvPreviewScreen.create(this, currentDraft, this, false));
    }

    @Override
    public void showCoverLetterPreview() {
        currentApplicationId = null;
        ensureDraft();
        currentBack = this::showDocumentsGenerated;
        setContentView(CoverLetterPreviewScreen.create(this, currentDraft, this, false));
    }

    @Override
    public void updateGeneratedCv(String content) {
        ensureDraft();
        String trimmed = content == null ? "" : content.trim();
        if (trimmed.isEmpty()) {
            currentDraft.generatedCv = "";
            currentApplicationId = null;
            currentBack = this::showDocumentsGenerated;
            setContentView(CvPreviewScreen.create(this, currentDraft, this, true));
            return;
        }
        currentDraft.generatedCv = trimmed;
        showDocumentsGenerated();
    }

    @Override
    public void updateGeneratedCoverLetter(String content) {
        ensureDraft();
        String trimmed = content == null ? "" : content.trim();
        if (trimmed.isEmpty()) {
            currentDraft.generatedCoverLetter = "";
            currentApplicationId = null;
            currentBack = this::showDocumentsGenerated;
            setContentView(CoverLetterPreviewScreen.create(this, currentDraft, this, true));
            return;
        }
        currentDraft.generatedCoverLetter = trimmed;
        showDocumentsGenerated();
    }

    @Override
    public void submitGeneratedApplication() {
        ensureDraft();
        if (!currentDraft.includeTailoredCv && !currentDraft.includeCoverLetter) {
            showDocumentsGenerated(true);
            return;
        }
        if (currentDraft.includeTailoredCv && isEmpty(currentDraft.generatedCv)) {
            currentApplicationId = null;
            currentBack = this::showDocumentsGenerated;
            setContentView(CvPreviewScreen.create(this, currentDraft, this, true));
            return;
        }
        if (currentDraft.includeCoverLetter && isEmpty(currentDraft.generatedCoverLetter)) {
            currentApplicationId = null;
            currentBack = this::showDocumentsGenerated;
            setContentView(CoverLetterPreviewScreen.create(this, currentDraft, this, true));
            return;
        }
        store.saveGeneratedApplication(currentDraft);
        currentDraft.submitted = true;
        currentApplicationId = null;
        currentBack = this::showDocumentsGenerated;
        setContentView(ApplicationSavedScreen.create(this, currentDraft, this));
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public void openGeneratedApplicationTracking() {
        ensureDraft();
        showApplicationDetails(currentDraft.applicationId);
    }

    private void ensureDraft() {
        if (currentDraft == null) {
            currentDraft = ApplicationDraft.demo();
        }
    }

    private boolean isProfileComplete(UserProfile profile) {
        return !profile.technicalSkills.isEmpty()
                && profile.location != null
                && !profile.location.trim().isEmpty()
                && profile.desiredPositions != null
                && !profile.desiredPositions.trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        if (currentApplicationId != null) {
            showApplicationManager(ApplicationFilter.ALL);
        } else if (currentBack != null) {
            currentBack.run();
        } else {
            super.onBackPressed();
        }
    }
}
