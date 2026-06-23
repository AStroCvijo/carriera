package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.example.carriera.R;
import com.example.carriera.account.model.Experience;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class ExperienceScreen {
    static final String[] EMPLOYMENT_TYPES = {"Full-time", "Part-time", "Internship", "Contract", "Freelance"};

    private ExperienceScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        if (profile.experience.isEmpty()) {
            profile.experience.add(new Experience());
        }
        Experience exp = profile.experience.get(0);

        LinearLayout content = AppViews.column(activity);

        EditText company = AppViews.field(activity, "company name", exp.company);
        content.addView(company, AppViews.lp(activity, -1, 46, 0, 8, 0, 12));

        EditText position = AppViews.field(activity, "position/title", exp.position);
        content.addView(position, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        Spinner employmentType = AppViews.dropdown(activity, "employment type", EMPLOYMENT_TYPES, exp.employmentType);
        content.addView(employmentType, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        LinearLayout locationRow = AppViews.row(activity);
        EditText location = AppViews.iconField(activity, "location", exp.location, R.drawable.ic_location);
        locationRow.addView(location, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        locationRow.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 12), 1));
        boolean[] remote = {exp.remote};
        LinearLayout remoteCheck = AppViews.checkRow(activity, "remote", remote);
        locationRow.addView(remoteCheck, new LinearLayout.LayoutParams(0, -2, 1));
        content.addView(locationRow, AppViews.lp(activity, -1, -2, 0, 0, 0, 12));

        LinearLayout dates = AppViews.row(activity);
        EditText start = AppViews.iconField(activity, "start date", exp.startDate, R.drawable.ic_calendar);
        dates.addView(start, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        dates.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 12), 1));
        EditText end = AppViews.iconField(activity, "end date", exp.endDate, R.drawable.ic_calendar);
        dates.addView(end, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        content.addView(dates, AppViews.lp(activity, -1, -2, 0, 0, 0, 12));

        boolean[] working = {exp.currentlyWorking};
        LinearLayout workingRow = AppViews.checkRow(activity, "currently work here", working);
        content.addView(workingRow, AppViews.lp(activity, -2, -2, 0, 0, 0, 12));

        EditText description = AppViews.multilineField(activity, "description", exp.description, 4);
        content.addView(description, AppViews.lp(activity, -1, -2, 0, 0, 0, 12));

        LinearLayout skillRow = AppViews.row(activity);
        for (String skill : exp.skills) {
            skillRow.addView(AppViews.chip(activity, skill), chipParams(activity));
        }
        Button addSkill = AppViews.plusChip(activity);
        addSkill.setOnClickListener(v -> SkillDialog.show(activity, "Add skill", skill -> {
            exp.skills.add(skill);
            activity.setContentView(create(activity, profile, navigator, onboarding, showError));
        }));
        skillRow.addView(addSkill, new LinearLayout.LayoutParams(AppViews.dp(activity, 48), AppViews.dp(activity, 40)));
        content.addView(skillRow, AppViews.lp(activity, -1, -2, 0, 0, 0, 14));

        Button addAnother = AppViews.addRowButton(activity, "Add another experience");
        addAnother.setOnClickListener(v -> {
            profile.experience.add(new Experience());
            Toast.makeText(activity, "Added experience " + profile.experience.size(), Toast.LENGTH_SHORT).show();
        });
        content.addView(addAnother, AppViews.lp(activity, -1, 46, 0, 0, 0, 10));

        TextView step = AppViews.body(activity, "3/5");
        step.setGravity(Gravity.END);
        content.addView(step, AppViews.lp(activity, -1, -2, 0, 6, 0, 0));

        Runnable save = () -> {
            exp.company = company.getText().toString();
            exp.position = position.getText().toString();
            exp.employmentType = AppViews.dropdownValue(employmentType);
            exp.location = location.getText().toString();
            exp.remote = remote[0];
            exp.startDate = start.getText().toString();
            exp.endDate = end.getText().toString();
            exp.currentlyWorking = working[0];
            exp.description = description.getText().toString();
        };

        Runnable next = () -> {
            save.run();
            if (exp.company.trim().isEmpty() || exp.position.trim().isEmpty()) {
                activity.setContentView(create(activity, profile, navigator, onboarding, true));
            } else {
                navigator.showSkills(onboarding);
            }
        };
        Runnable previous = () -> {
            save.run();
            navigator.showEducation(onboarding);
        };

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Previous", previous,
                "Next", next,
                showError ? "Invalid or missing information" : null);

        return AppViews.screenWithBottomActions(activity, "Experience", previous, content, bottom);
    }

    private static LinearLayout.LayoutParams chipParams(Activity activity) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, AppViews.dp(activity, 40));
        params.rightMargin = AppViews.dp(activity, 10);
        return params;
    }
}
