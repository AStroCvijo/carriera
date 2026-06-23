package com.example.carriera;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carriera.account.data.AccountStore;
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
import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.ApplicationStatus;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.screens.ApplicationDetailsScreen;
import com.example.carriera.applications.screens.ApplicationManagerScreen;
import com.example.carriera.applications.screens.InterviewExampleScreen;
import com.example.carriera.applications.screens.InterviewPrepScreen;
import com.example.carriera.applications.screens.InterviewSimulationScreen;
import com.example.carriera.applications.screens.ReminderScreen;
import com.example.carriera.applications.screens.StatusScreen;

public class MainActivity extends Activity implements ApplicationNavigator, AccountNavigator {
    private ApplicationStore store;
    private AccountStore accountStore;
    private String currentApplicationId;
    private Runnable currentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = ApplicationStore.createDemoStore();
        accountStore = AccountStore.createDemoStore();
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
        currentBack = null;
        setContentView(HomeLoggedInScreen.create(
                this,
                this,
                () -> showApplicationManager(ApplicationFilter.ALL),
                () -> Toast.makeText(this, "Find open positions is not part of this prototype", Toast.LENGTH_SHORT).show(),
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
    public void finishOnboarding() {
        showHomeLoggedIn();
    }

    // ---- Application tracking flow ----

    @Override
    public void showApplicationManager(ApplicationFilter filter) {
        currentApplicationId = null;
        currentBack = this::showHomeLoggedIn;
        setContentView(ApplicationManagerScreen.create(this, store, filter, this));
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
