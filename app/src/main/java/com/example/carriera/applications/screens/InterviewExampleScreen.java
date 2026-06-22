package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class InterviewExampleScreen {
    private InterviewExampleScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, String question, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.addView(AppViews.detailCard(activity, "Interview for", application.position + "\n" + application.company),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 20));
        content.addView(AppViews.detailCard(activity, "Question", question),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 20));
        content.addView(AppViews.detailCard(activity, "Suggested structure",
                "1. Short introduction\n"
                        + "2. Education\n"
                        + "3. Relevant project\n"
                        + "4. Motivation for the role"
        ), AppViews.lp(activity, -1, -2, 0, 0, 0, 20));
        content.addView(AppViews.detailCard(activity, "Example answer", exampleAnswer(question)),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);

        Button start = AppViews.bottomButton(activity, "Start");
        start.setOnClickListener(v -> navigator.showInterviewSimulation(application.id));
        actions.addView(start, new LinearLayout.LayoutParams(AppViews.dp(activity, 132), AppViews.dp(activity, 46)));

        return AppViews.screenWithBottomActions(activity, "Example", () -> navigator.showInterviewPrep(application.id), content, actions);
    }

    private static String exampleAnswer(String question) {
        if ("Tell me about yourself.".equals(question)) {
            return "I am a final-year software engineering student.\n"
                    + "I have worked on Java and web projects.\n"
                    + "I want to improve my skills in a real team.";
        }
        return "I would start with a short definition, then connect it to one project from my CV and explain how I used it in practice.";
    }
}
