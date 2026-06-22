package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class InterviewPrepScreen {
    private static final String[] QUESTIONS = {
            "Tell me about yourself.",
            "What is REST API?",
            "Explain one project from your CV.",
            "Why do you want this role?"
    };

    private InterviewPrepScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.addView(AppViews.detailCard(activity, "Interview for", application.position + "\n" + application.company),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 24));

        content.addView(AppViews.formLabel(activity, "Suggested questions"), AppViews.lp(activity, -1, -2, 0, 0, 0, 10));
        for (String question : QUESTIONS) {
            LinearLayout row = AppViews.questionCard(activity);
            row.addView(AppViews.bodyBold(activity, question), new LinearLayout.LayoutParams(0, -2, 1));

            Button details = AppViews.detailsButton(activity);
            details.setOnClickListener(v -> navigator.showInterviewExample(application.id, question));
            row.addView(details, new LinearLayout.LayoutParams(AppViews.dp(activity, 82), AppViews.dp(activity, 34)));
            content.addView(row, AppViews.lp(activity, -1, -2, 0, 6, 0, 8));
        }

        Button simulation = AppViews.simulationButton(activity, "Start\nSimulation");
        simulation.setOnClickListener(v -> navigator.showInterviewSimulation(application.id));

        LinearLayout bottom = AppViews.row(activity);
        bottom.setGravity(Gravity.CENTER);
        bottom.addView(simulation, new LinearLayout.LayoutParams(AppViews.dp(activity, 118), AppViews.dp(activity, 52)));

        return AppViews.screenWithBottomActions(activity, "Interview Prep", () -> navigator.showApplicationDetails(application.id), content, bottom);
    }
}
