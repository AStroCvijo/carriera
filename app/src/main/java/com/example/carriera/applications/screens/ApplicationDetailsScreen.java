package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class ApplicationDetailsScreen {
    private ApplicationDetailsScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);

        int cardBottom = application.hasReminder() ? 12 : 18;

        content.addView(AppViews.detailCard(activity,
                application.position,
                application.company
                        + "\n\nStatus: " + application.status.label
                        + "\nApplied: " + application.appliedDate
                        + "\nDeadline: " + application.deadline + " 2026"
        ), AppViews.lp(activity, -1, -2, 0, 0, 0, cardBottom));

        content.addView(AppViews.detailCard(activity, "Notes", application.notes), AppViews.lp(activity, -1, -2, 0, 0, 0, cardBottom));
        content.addView(AppViews.detailCard(activity, "Communication", application.communication), AppViews.lp(activity, -1, -2, 0, 0, 0, cardBottom));
        content.addView(AppViews.detailCard(activity, "Next Step", application.detailedNextStep()), AppViews.lp(activity, -1, -2, 0, 0, 0, cardBottom));

        for (int i = 0; i < application.reminders.size(); i++) {
            TrackedApplication.ReminderItem reminder = application.reminders.get(i);
            String reminderText = reminder.type + " | " + reminder.dateTime;
            if (reminder.note != null && !reminder.note.trim().isEmpty()) {
                reminderText += "\n" + reminder.note;
            }
            content.addView(AppViews.compactDetailCard(activity,
                    application.reminders.size() == 1 ? "Reminder" : "Reminder " + (i + 1),
                    reminderText
            ), AppViews.lp(activity, -1, -2, 0, 0, 0, 8));
        }

        LinearLayout actions = AppViews.row(activity);
        Button status = AppViews.actionButton(activity, "Change Status");
        status.setOnClickListener(v -> navigator.showStatusEditor(application.id));
        actions.addView(status, new LinearLayout.LayoutParams(AppViews.dp(activity, 148), AppViews.dp(activity, 52)));

        View spacer = new View(activity);
        actions.addView(spacer, new LinearLayout.LayoutParams(0, 1, 1));

        Button reminder = AppViews.actionButton(activity, "Set Reminder");
        reminder.setOnClickListener(v -> navigator.showReminderEditor(application.id));
        actions.addView(reminder, new LinearLayout.LayoutParams(AppViews.dp(activity, 148), AppViews.dp(activity, 52)));

        Button interview = AppViews.actionButton(activity, "Prepare Interview");
        interview.setOnClickListener(v -> navigator.showInterviewPrep(application.id));

        LinearLayout bottomActions = AppViews.column(activity);
        bottomActions.addView(actions, AppViews.lp(activity, -1, 52, 0, 0, 0, 10));
        bottomActions.addView(interview, AppViews.lp(activity, -2, 52, 0, 0, 0, 0));

        return AppViews.screenWithBottomActions(activity, "Details", () -> navigator.showApplicationManager(ApplicationFilter.ALL), content, bottomActions);
    }
}
