package com.example.carriera;

import android.app.Activity;
import android.os.Bundle;

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

public class MainActivity extends Activity implements ApplicationNavigator {
    private ApplicationStore store;
    private String currentApplicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = ApplicationStore.createDemoStore();
        showApplicationManager(ApplicationFilter.ALL);
    }

    @Override
    public void showApplicationManager(ApplicationFilter filter) {
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
        if (currentApplicationId == null) {
            super.onBackPressed();
        } else {
            showApplicationManager(ApplicationFilter.ALL);
            currentApplicationId = null;
        }
    }
}
