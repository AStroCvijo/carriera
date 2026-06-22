package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class InterviewSimulationScreen {
    private static final String ANSWER_PLACEHOLDER = "Type your answer here...";
    private static final String[] QUESTIONS = {
            "Tell me about yourself.",
            "What is REST API?",
            "Explain one project from your CV.",
            "Why do you want this role?",
            "How do you organize your work?"
    };
    private static final String[] FEEDBACK = {
            "Mention your education first.\nAdd one concrete project example.\nKeep the answer under one minute.",
            "Start with a simple definition.\nMention client, server and HTTP methods.\nAdd one example from a project.",
            "Explain the goal, your role and result.\nMention technologies you used.\nKeep the story concrete.",
            "Connect your motivation to the company.\nMention learning and contribution.\nAvoid generic answers.",
            "Mention priorities, deadlines and communication.\nGive one example from team work.\nKeep it practical."
    };

    private InterviewSimulationScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        SimulationState state = new SimulationState();
        return render(activity, application, navigator, state);
    }

    private static View render(Activity activity, TrackedApplication application, ApplicationNavigator navigator, SimulationState state) {
        LinearLayout content = AppViews.column(activity);
        content.addView(AppViews.detailCard(activity, "Interview for", application.position + "\n" + application.company),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 22));
        content.addView(AppViews.detailCard(activity, "Question " + (state.questionIndex + 1) + "/5", QUESTIONS[state.questionIndex]),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 22));

        content.addView(AppViews.formLabel(activity, "Your answer"), AppViews.lp(activity, -1, -2, 0, 0, 0, 14));
        EditText answer = AppViews.input(activity, ANSWER_PLACEHOLDER, 5);
        content.addView(answer, AppViews.lp(activity, -1, -2, 0, 0, 0, 22));
        answer.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && ANSWER_PLACEHOLDER.contentEquals(answer.getText())) {
                answer.setText("");
            }
        });

        View feedback = AppViews.detailCard(activity, "AI Feedback",
                FEEDBACK[state.questionIndex]
        );
        feedback.setVisibility(state.feedbackVisible ? View.VISIBLE : View.GONE);
        content.addView(feedback, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);

        Button next = AppViews.bottomButton(activity, "Next");
        next.setOnClickListener(v -> {
            if (!state.feedbackVisible) {
                if (hasAnswer(answer)) {
                    state.feedbackVisible = true;
                    activity.setContentView(render(activity, application, navigator, state));
                }
                return;
            }

            if (state.questionIndex < QUESTIONS.length - 1) {
                state.questionIndex++;
                state.feedbackVisible = false;
                activity.setContentView(render(activity, application, navigator, state));
            } else {
                navigator.showInterviewPrep(application.id);
            }
        });
        actions.addView(next, new LinearLayout.LayoutParams(AppViews.dp(activity, 132), AppViews.dp(activity, 46)));

        return AppViews.screenWithBottomActions(activity, "Simulation", () -> navigator.showInterviewPrep(application.id), content, actions);
    }

    private static boolean hasAnswer(EditText answer) {
        String value = answer.getText().toString().trim();
        return !value.isEmpty() && !ANSWER_PLACEHOLDER.equals(value);
    }

    private static class SimulationState {
        int questionIndex = 0;
        boolean feedbackVisible = false;
    }
}
