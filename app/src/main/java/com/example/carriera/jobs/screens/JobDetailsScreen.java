package com.example.carriera.jobs.screens;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.jobs.model.RecommendedJob;

public final class JobDetailsScreen {
    private JobDetailsScreen() {
    }

    public static View create(
            Activity activity,
            RecommendedJob job,
            UserProfile profile,
            ApplicationNavigator navigator,
            AccountNavigator accountNavigator
    ) {
        LinearLayout content = AppViews.column(activity);
        boolean canAnalyzeMatch = profile.cvUploaded && isProfileComplete(profile);

        LinearLayout top = AppViews.row(activity);
        LinearLayout title = AppViews.column(activity);
        title.addView(JobUi.text(activity, job.title, 18, true));
        title.addView(JobUi.text(activity, job.company, 13, true), AppViews.lp(activity, -1, -2, 0, 6, 0, 0));
        top.addView(title, new LinearLayout.LayoutParams(0, -2, 1));
        top.addView(JobUi.matchBadge(activity, job.matchPercent), new LinearLayout.LayoutParams(AppViews.dp(activity, 62), AppViews.dp(activity, 62)));
        content.addView(top, AppViews.lp(activity, -1, -2, 0, 0, 0, 8));

        content.addView(JobUi.twoColumnMeta(activity, job), AppViews.lp(activity, -1, -2, 0, 0, 0, 20));
        content.addView(JobUi.divider(activity), AppViews.lp(activity, -1, 1, 0, 0, 0, 12));

        content.addView(JobUi.sectionTitle(activity, "About the position"));
        content.addView(AppViews.card(activity, job.description), AppViews.lp(activity, -1, -2, 0, 8, 0, 20));

        content.addView(JobUi.sectionTitle(activity, "Required skills"));
        content.addView(JobUi.chipWrap(activity, job.requiredSkills), AppViews.lp(activity, -1, -2, 0, 8, 0, 12));
        content.addView(JobUi.divider(activity), AppViews.lp(activity, -1, 1, 0, 0, 0, 8));

        if (canAnalyzeMatch) {
            Button analyze = AppViews.actionButton(activity, "Analyze match");
            analyze.setOnClickListener(v -> navigator.showMatchAnalysis(job.id));
            content.addView(analyze, AppViews.lp(activity, -1, 44, 8, 0, 8, 8));
        }

        if (!profile.cvUploaded) {
            content.addView(JobUi.warningBar(activity, "CV missing"), AppViews.lp(activity, -1, 34, 0, 0, 0, 12));
            Button upload = AppViews.bottomButton(activity, "Upload CV");
            upload.setOnClickListener(v -> accountNavigator.showCvManager(false));
            content.addView(upload, AppViews.lp(activity, -1, 44, 66, 0, 66, 0));
        } else if (!isProfileComplete(profile)) {
            content.addView(JobUi.warningBar(activity, "profile incompleted"), AppViews.lp(activity, -1, 34, 0, 0, 0, 12));
            Button complete = AppViews.bottomButton(activity, "Complete profile");
            complete.setOnClickListener(v -> accountNavigator.showBasicInfo(false));
            content.addView(complete, AppViews.lp(activity, -1, 44, 46, 0, 46, 0));
        } else {
            Button generate = AppViews.bottomButton(activity, "Generate application");
            generate.setOnClickListener(v -> navigator.showGenerateApplication(job.id));
            content.addView(generate, AppViews.lp(activity, -1, 44, 8, 0, 8, 8));

            TextView save = JobUi.text(activity, "Save job", 10, true);
            save.setGravity(android.view.Gravity.CENTER);
            save.setPaintFlags(save.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            content.addView(save, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));
        }

        return AppViews.screen(activity, "Job details", () -> navigator.showRecommendedJobs("All"), content);
    }

    private static boolean isProfileComplete(UserProfile profile) {
        return !profile.technicalSkills.isEmpty()
                && profile.location != null
                && !profile.location.trim().isEmpty()
                && profile.desiredPositions != null
                && !profile.desiredPositions.trim().isEmpty();
    }
}
