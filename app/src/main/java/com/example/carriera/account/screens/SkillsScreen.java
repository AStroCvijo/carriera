package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

import java.util.List;

public final class SkillsScreen {
    private SkillsScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        LinearLayout content = AppViews.column(activity);
        content.addView(new View(activity), AppViews.lp(activity, -1, 40, 0, 0, 0, 0));

        content.addView(AppViews.sectionPill(activity, "Technical skills"), AppViews.lp(activity, -1, -2, 0, 0, 0, 12));
        content.addView(skillRow(activity, profile.technicalSkills, () ->
                SkillDialog.show(activity, "Add technical skill", skill -> {
                    profile.technicalSkills.add(skill);
                    activity.setContentView(create(activity, profile, navigator, onboarding, showError));
                })), AppViews.lp(activity, -1, -2, 0, 0, 0, 24));

        content.addView(AppViews.sectionPill(activity, "Soft skills"), AppViews.lp(activity, -1, -2, 0, 0, 0, 12));
        content.addView(skillRow(activity, profile.softSkills, () ->
                SkillDialog.show(activity, "Add soft skill", skill -> {
                    profile.softSkills.add(skill);
                    activity.setContentView(create(activity, profile, navigator, onboarding, showError));
                })), AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        TextView step = AppViews.body(activity, "4/5");
        step.setGravity(Gravity.END);
        content.addView(step, AppViews.lp(activity, -1, -2, 0, 18, 0, 0));

        Runnable next = () -> navigator.showJobPrefs(onboarding);
        Runnable previous = () -> navigator.showExperience(onboarding);

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Previous", previous,
                "Next", next,
                showError ? "Invalid or missing information" : null);

        return AppViews.screenWithBottomActions(activity, "Skills", previous, content, bottom);
    }

    private static LinearLayout skillRow(Activity activity, List<String> skills, Runnable onAdd) {
        LinearLayout row = AppViews.row(activity);
        for (String skill : skills) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, AppViews.dp(activity, 40));
            params.rightMargin = AppViews.dp(activity, 10);
            row.addView(AppViews.chip(activity, skill), params);
        }
        Button add = AppViews.plusChip(activity);
        add.setOnClickListener(v -> onAdd.run());
        row.addView(add, new LinearLayout.LayoutParams(AppViews.dp(activity, 48), AppViews.dp(activity, 40)));
        return row;
    }
}
