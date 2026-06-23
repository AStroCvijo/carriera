package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class JobPrefsScreen {
    private static final String[] AVAILABILITY = {"Immediately", "In 2 weeks", "In 1 month", "In 3 months", "Flexible"};
    private static final String[] CURRENCIES = {"EUR", "USD", "RSD", "GBP"};
    private static final String[] INDUSTRIES = {"IT", "Finance", "Marketing", "Design", "Engineering", "Healthcare", "Other"};

    private JobPrefsScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        LinearLayout content = AppViews.column(activity);

        EditText positions = AppViews.field(activity, "desired positions", profile.desiredPositions);
        content.addView(positions, AppViews.lp(activity, -1, 46, 0, 16, 0, 12));

        Spinner employmentType = AppViews.dropdown(activity, "employment type", ExperienceScreen.EMPLOYMENT_TYPES, profile.preferredEmploymentType);
        content.addView(employmentType, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        EditText location = AppViews.field(activity, "preferred location", profile.preferredLocation);
        content.addView(location, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        Spinner availability = AppViews.dropdown(activity, "availability", AVAILABILITY, profile.availability);
        content.addView(availability, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        EditText fieldOfStudy = AppViews.field(activity, "field of study", profile.fieldOfStudy);
        content.addView(fieldOfStudy, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        LinearLayout salary = AppViews.row(activity);
        EditText salaryMin = AppViews.field(activity, "salary min", profile.salaryMin);
        salary.addView(salaryMin, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        salary.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 12), 1));
        EditText salaryMax = AppViews.field(activity, "salary max", profile.salaryMax);
        salary.addView(salaryMax, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        content.addView(salary, AppViews.lp(activity, -1, -2, 0, 0, 0, 12));

        Spinner currency = AppViews.dropdown(activity, "currency", CURRENCIES, profile.currency);
        content.addView(currency, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        Spinner industry = AppViews.dropdown(activity, "industry of interest", INDUSTRIES, profile.industryOfInterest);
        content.addView(industry, AppViews.lp(activity, -1, 46, 0, 0, 0, 10));

        TextView step = AppViews.body(activity, "5/5");
        step.setGravity(Gravity.END);
        content.addView(step, AppViews.lp(activity, -1, -2, 0, 6, 0, 0));

        Runnable save = () -> {
            profile.desiredPositions = positions.getText().toString();
            profile.preferredEmploymentType = AppViews.dropdownValue(employmentType);
            profile.preferredLocation = location.getText().toString();
            profile.availability = AppViews.dropdownValue(availability);
            profile.fieldOfStudy = fieldOfStudy.getText().toString();
            profile.salaryMin = salaryMin.getText().toString();
            profile.salaryMax = salaryMax.getText().toString();
            profile.currency = AppViews.dropdownValue(currency);
            profile.industryOfInterest = AppViews.dropdownValue(industry);
        };

        Runnable finish = () -> {
            save.run();
            if (profile.desiredPositions.trim().isEmpty()) {
                activity.setContentView(create(activity, profile, navigator, onboarding, true));
            } else if (onboarding) {
                navigator.showCvManager(true);
            } else {
                navigator.showProfile();
            }
        };
        Runnable previous = () -> {
            save.run();
            navigator.showSkills(onboarding);
        };

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Previous", previous,
                "Finish", finish,
                showError ? "Invalid or missing information" : null);

        return AppViews.screenWithBottomActions(activity, "Job prefs", previous, content, bottom);
    }
}
