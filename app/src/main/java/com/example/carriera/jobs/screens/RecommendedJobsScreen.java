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
import com.example.carriera.jobs.data.RecommendedJobStore;
import com.example.carriera.jobs.model.RecommendedJob;

import java.util.List;

public final class RecommendedJobsScreen {
    private static final String[] FILTERS = {"All", "High match", "Remote", "Newest"};

    private RecommendedJobsScreen() {
    }

    public static View create(Activity activity, RecommendedJobStore store, String filter, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        LinearLayout filters = AppViews.row(activity);

        for (String item : FILTERS) {
            Button button = AppViews.pill(activity, item, item.equals(filter));
            button.setOnClickListener(v -> navigator.showRecommendedJobs(item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, AppViews.dp(activity, 34), 1);
            params.setMargins(AppViews.dp(activity, 4), 0, AppViews.dp(activity, 4), 0);
            filters.addView(button, params);
        }
        content.addView(filters, AppViews.lp(activity, -1, 34, 0, 0, 0, 12));

        List<RecommendedJob> jobs = store.filtered(filter);
        if (jobs.isEmpty()) {
            content.addView(emptyState(activity, navigator), AppViews.lp(activity, -1, -2, 0, 70, 0, 0));
        } else {
            for (RecommendedJob job : jobs) {
                content.addView(jobCard(activity, job, navigator), AppViews.lp(activity, -1, -2, 0, 0, 0, 10));
            }
            content.addView(pagination(activity), AppViews.lp(activity, -1, 48, 0, 8, 0, 0));
        }

        return AppViews.screen(activity, "Recommended jobs", AppViews::goHome, content);
    }

    private static View emptyState(Activity activity, ApplicationNavigator navigator) {
        LinearLayout box = AppViews.column(activity);
        box.setGravity(Gravity.CENTER_HORIZONTAL);

        box.addView(JobUi.warningGraphic(activity), AppViews.lp(activity, 210, 118, 0, 0, 0, 30));

        TextView title = JobUi.text(activity, "No jobs found", 18, true);
        title.setGravity(Gravity.CENTER);
        box.addView(title, AppViews.lp(activity, -1, -2, 0, 0, 0, 16));

        TextView body = JobUi.text(activity, "We couldn't find any recommended jobs that match\nyour filters.", 12, false);
        body.setGravity(Gravity.CENTER);
        box.addView(body, AppViews.lp(activity, -1, -2, 0, 0, 0, 46));

        Button reset = AppViews.actionButton(activity, "Reset filters");
        reset.setOnClickListener(v -> navigator.showRecommendedJobs("All"));
        box.addView(reset, AppViews.lp(activity, -1, 44, 46, 0, 46, 12));

        Button profile = AppViews.bottomButton(activity, "Update profile");
        profile.setOnClickListener(v -> AppViews.goHome());
        box.addView(profile, AppViews.lp(activity, -1, 44, 46, 0, 46, 0));
        return box;
    }

    private static View jobCard(Activity activity, RecommendedJob job, ApplicationNavigator navigator) {
        LinearLayout card = AppViews.column(activity);
        card.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 12), AppViews.dp(activity, 14), AppViews.dp(activity, 10));
        card.setBackground(JobUi.outline(activity, AppTheme.BACKGROUND, AppTheme.PILL, 18));

        LinearLayout top = AppViews.row(activity);
        LinearLayout copy = AppViews.column(activity);
        copy.addView(JobUi.text(activity, job.title, 18, true));
        copy.addView(JobUi.text(activity, job.company, 13, true), AppViews.lp(activity, -1, -2, 0, 8, 0, 0));
        top.addView(copy, new LinearLayout.LayoutParams(0, -2, 1));
        top.addView(JobUi.matchBadge(activity, job.matchPercent), new LinearLayout.LayoutParams(AppViews.dp(activity, 58), AppViews.dp(activity, 58)));
        card.addView(top);

        card.addView(JobUi.metaLine(activity, "pin", job.location + "    " + job.workMode), AppViews.lp(activity, -1, -2, 0, 8, 0, 0));
        card.addView(JobUi.metaLine(activity, "bag", job.employmentType), AppViews.lp(activity, -1, -2, 0, 4, 0, 0));
        card.addView(JobUi.metaLine(activity, "date", "Posted:" + job.posted + "    Deadline: " + job.deadline), AppViews.lp(activity, -1, -2, 0, 4, 0, 8));

        LinearLayout bottom = AppViews.row(activity);
        TextView open = JobUi.text(activity, "Open listing", 9, true);
        open.setPaintFlags(open.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);
        bottom.addView(open, new LinearLayout.LayoutParams(0, -2, 1));
        Button details = AppViews.pill(activity, "View details", false);
        details.setTextSize(11);
        details.setOnClickListener(v -> navigator.showRecommendedJobDetails(job.id));
        bottom.addView(details, new LinearLayout.LayoutParams(AppViews.dp(activity, 112), AppViews.dp(activity, 30)));
        card.addView(bottom);
        return card;
    }

    private static View pagination(Activity activity) {
        LinearLayout row = AppViews.row(activity);
        Button prev = AppViews.bottomButton(activity, "Previous");
        Button next = AppViews.bottomButton(activity, "Next");
        row.addView(prev, new LinearLayout.LayoutParams(AppViews.dp(activity, 122), AppViews.dp(activity, 48)));
        row.addView(new View(activity), new LinearLayout.LayoutParams(0, 1, 1));
        row.addView(next, new LinearLayout.LayoutParams(AppViews.dp(activity, 122), AppViews.dp(activity, 48)));
        return row;
    }
}
