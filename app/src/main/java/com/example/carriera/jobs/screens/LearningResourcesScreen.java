package com.example.carriera.jobs.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

public final class LearningResourcesScreen {
    private LearningResourcesScreen() {
    }

    public static View create(Activity activity, String jobId, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.setPadding(0, 0, 0, AppViews.dp(activity, 8));
        content.addView(JobUi.sectionTitle(activity, "Recommended for your missing skills"));
        content.addView(JobUi.text(activity, "Improve your readiness", 11, false), AppViews.lp(activity, -1, -2, 0, 4, 0, 20));

        addGroup(activity, content, "Docker", new String[][]{
                {"Course", "Docker for Beginners", "2 hours", "Beginner", "Open"},
                {"Tutorial", "Build your first container", "30 min", "", "Open"},
                {"Project", "Containerize a Spring Boot app", "30 min", "", "Start"}
        });
        content.addView(JobUi.divider(activity), AppViews.lp(activity, -1, 1, 0, 8, 0, 12));
        addGroup(activity, content, "Spring Security", new String[][]{
                {"Course", "Spring Security Fundamentals", "2 hours", "Beginner", "Open"},
                {"Documentation", "Authentication guide", "", "", "Open"},
                {"Project", "Secure a REST API", "", "", "Start"}
        });

        Button previous = AppViews.bottomButton(activity, "Previous");
        previous.setOnClickListener(v -> navigator.showMatchAnalysis(jobId));
        content.addView(previous, AppViews.lp(activity, 118, 44, 0, 18, 0, 0));
        return AppViews.screen(activity, "Learning resources", () -> navigator.showMatchAnalysis(jobId), content);
    }

    private static void addGroup(Activity activity, LinearLayout content, String title, String[][] rows) {
        content.addView(JobUi.sectionTitle(activity, title), AppViews.lp(activity, -1, -2, 0, 0, 0, 8));
        for (String[] row : rows) {
            content.addView(resourceRow(activity, row[0], row[1], row[2], row[3], row[4]), AppViews.lp(activity, -1, 58, 0, 0, 0, 8));
        }
    }

    private static View resourceRow(Activity activity, String type, String title, String duration, String level, String action) {
        LinearLayout row = AppViews.row(activity);
        row.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 6), AppViews.dp(activity, 10), AppViews.dp(activity, 6));
        row.setBackground(AppViews.roundedBackground(activity, AppTheme.CARD, 16));

        LinearLayout copy = AppViews.column(activity);
        TextView typeView = JobUi.smallPill(activity, type);
        copy.addView(typeView, AppViews.lp(activity, 90, 18, 0, 0, 0, 3));
        TextView titleView = JobUi.text(activity, title, 11, true);
        titleView.setSingleLine(true);
        copy.addView(titleView);
        String meta = duration + (level == null || level.isEmpty() ? "" : "    " + level);
        TextView metaView = JobUi.text(activity, meta.trim(), 8, false);
        metaView.setSingleLine(true);
        copy.addView(metaView);
        row.addView(copy, new LinearLayout.LayoutParams(0, -2, 1));

        Button button = AppViews.pill(activity, action, true);
        button.setTextSize(10);
        button.setGravity(Gravity.CENTER);
        row.addView(button, new LinearLayout.LayoutParams(AppViews.dp(activity, 58), AppViews.dp(activity, 30)));
        return row;
    }
}
