package com.example.carriera.jobs.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.jobs.model.RecommendedJob;

public final class MatchAnalysisScreen {
    private MatchAnalysisScreen() {
    }

    public static View create(Activity activity, RecommendedJob job, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);

        LinearLayout hero = AppViews.row(activity);
        hero.addView(new JobUi.MatchRing(activity, job.matchPercent), new LinearLayout.LayoutParams(AppViews.dp(activity, 106), AppViews.dp(activity, 106)));
        LinearLayout copy = AppViews.column(activity);
        copy.addView(JobUi.text(activity, "Strong match for\nyour profile", 18, true));
        copy.addView(JobUi.text(activity, "Your education and technical skills meet most requirements for this position.", 12, true), AppViews.lp(activity, -1, -2, 0, 8, 0, 0));
        hero.addView(copy, new LinearLayout.LayoutParams(0, -2, 1));
        content.addView(hero, AppViews.lp(activity, -1, -2, 0, 14, 0, 20));

        content.addView(JobUi.divider(activity), AppViews.lp(activity, -1, 1, 0, 0, 0, 8));
        content.addView(JobUi.sectionTitle(activity, "Skills you have"));
        content.addView(JobUi.skillPanel(activity, job.skillsYouHave), AppViews.lp(activity, -1, -2, 0, 8, 0, 12));

        content.addView(JobUi.sectionTitle(activity, "Skills to improve"));
        content.addView(JobUi.skillPanel(activity, job.skillsToImprove), AppViews.lp(activity, -1, -2, 0, 8, 0, 12));

        content.addView(JobUi.sectionTitle(activity, "Why this job fits you"));
        content.addView(AppViews.card(activity, "You already have 5 of 7 required skills. Improving Docker and Spring Security could increase your match score and stronger fit."), AppViews.lp(activity, -1, -2, 0, 8, 0, 12));

        Button resources = AppViews.actionButton(activity, "Learning resources");
        resources.setOnClickListener(v -> navigator.showLearningResources(job.id));
        content.addView(resources, AppViews.lp(activity, -1, 42, 72, 0, 16, 10));

        Button generate = AppViews.bottomButton(activity, "Generate application");
        generate.setOnClickListener(v -> navigator.showGenerateApplication(job.id));
        content.addView(generate, AppViews.lp(activity, -1, 44, 72, 0, 16, 0));

        return AppViews.screen(activity, "Match analysis", () -> navigator.showRecommendedJobDetails(job.id), content);
    }

    public static View error(Activity activity, String jobId, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.setGravity(Gravity.CENTER_HORIZONTAL);

        content.addView(JobUi.warningGraphic(activity), AppViews.lp(activity, 210, 118, 0, 80, 0, 54));

        TextView title = JobUi.text(activity, "Something went wrong", 18, true);
        title.setGravity(Gravity.CENTER);
        content.addView(title, AppViews.lp(activity, -1, -2, 0, 0, 0, 18));

        TextView body = JobUi.text(activity, "We couldn't analyze this job match right now.\nPlease try again.", 12, false);
        body.setGravity(Gravity.CENTER);
        content.addView(body, AppViews.lp(activity, -1, -2, 0, 0, 0, 56));

        Button retry = AppViews.actionButton(activity, "Try again");
        retry.setOnClickListener(v -> navigator.showMatchAnalysis(jobId));
        content.addView(retry, AppViews.lp(activity, -1, 44, 18, 0, 18, 12));

        Button back = AppViews.bottomButton(activity, "Go back");
        back.setOnClickListener(v -> navigator.showRecommendedJobDetails(jobId));
        content.addView(back, AppViews.lp(activity, -1, 44, 18, 0, 18, 0));

        return AppViews.screen(activity, "Match analysis", () -> navigator.showRecommendedJobDetails(jobId), content);
    }
}
