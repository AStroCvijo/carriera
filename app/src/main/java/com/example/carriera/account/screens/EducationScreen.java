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
import com.example.carriera.account.model.Education;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class EducationScreen {
    private static final String[] DEGREES = {"High school", "Associate", "Bachelor", "Master", "PhD"};

    private EducationScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        if (profile.education.isEmpty()) {
            profile.education.add(new Education());
        }
        Education edu = profile.education.get(0);

        LinearLayout content = AppViews.column(activity);

        EditText school = AppViews.field(activity, "university/school", edu.school);
        content.addView(school, AppViews.lp(activity, -1, 46, 0, 16, 0, 12));

        Spinner degree = AppViews.dropdown(activity, "degree", DEGREES, edu.degree);
        content.addView(degree, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        EditText fieldOfStudy = AppViews.field(activity, "field of study", edu.fieldOfStudy);
        content.addView(fieldOfStudy, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        LinearLayout dates = AppViews.row(activity);
        EditText start = AppViews.iconField(activity, "start date", edu.startDate, R.drawable.ic_calendar);
        dates.addView(start, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        dates.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 12), 1));
        EditText end = AppViews.iconField(activity, "end date", edu.endDate, R.drawable.ic_calendar);
        dates.addView(end, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        content.addView(dates, AppViews.lp(activity, -1, -2, 0, 0, 0, 12));

        EditText gpa = AppViews.field(activity, "current GPA", edu.gpa);
        content.addView(gpa, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));

        boolean[] enrolled = {edu.currentlyEnrolled};
        LinearLayout enrolledRow = AppViews.checkRow(activity, "Currently enrolled", enrolled);
        content.addView(enrolledRow, AppViews.lp(activity, -2, -2, 0, 0, 0, 18));

        Button addAnother = AppViews.addRowButton(activity, "Add another education");
        addAnother.setOnClickListener(v -> {
            profile.education.add(new Education());
            Toast.makeText(activity, "Added education " + profile.education.size(), Toast.LENGTH_SHORT).show();
        });
        content.addView(addAnother, AppViews.lp(activity, -1, 46, 0, 0, 0, 10));

        TextView step = AppViews.body(activity, "2/5");
        step.setGravity(Gravity.END);
        content.addView(step, AppViews.lp(activity, -1, -2, 0, 6, 0, 0));

        Runnable save = () -> {
            edu.school = school.getText().toString();
            edu.degree = AppViews.dropdownValue(degree);
            edu.fieldOfStudy = fieldOfStudy.getText().toString();
            edu.startDate = start.getText().toString();
            edu.endDate = end.getText().toString();
            edu.gpa = gpa.getText().toString();
            edu.currentlyEnrolled = enrolled[0];
        };

        Runnable next = () -> {
            save.run();
            if (edu.school.trim().isEmpty() || edu.degree.trim().isEmpty()) {
                activity.setContentView(create(activity, profile, navigator, onboarding, true));
            } else {
                navigator.showExperience(onboarding);
            }
        };
        Runnable previous = () -> {
            save.run();
            navigator.showBasicInfo(onboarding);
        };

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Previous", previous,
                "Next", next,
                showError ? "Invalid or missing information" : null);

        return AppViews.screenWithBottomActions(activity, "Education", previous, content, bottom);
    }
}
