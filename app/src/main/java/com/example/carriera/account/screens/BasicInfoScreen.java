package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.R;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class BasicInfoScreen {
    private BasicInfoScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        LinearLayout content = AppViews.column(activity);

        EditText location = AppViews.field(activity, "location", profile.location);
        content.addView(location, AppViews.lp(activity, -1, 46, 0, 16, 0, 14));

        EditText dob = AppViews.field(activity, "Date of Birth", profile.dateOfBirth);
        content.addView(dob, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        Button picture = AppViews.uploadButton(activity,
                profile.profilePictureUploaded ? "Profile picture added" : "Upload profile picture",
                R.drawable.ic_upload);
        picture.setOnClickListener(v -> {
            profile.profilePictureUploaded = true;
            picture.setText("Profile picture added");
        });
        content.addView(picture, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText bio = AppViews.multilineField(activity, "short bio", profile.shortBio, 4);
        content.addView(bio, AppViews.lp(activity, -1, -2, 0, 0, 0, 14));

        EditText linkedin = AppViews.field(activity, "Linkedin url", profile.linkedinUrl);
        content.addView(linkedin, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText github = AppViews.field(activity, "GitHub url", profile.githubUrl);
        content.addView(github, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText portfolio = AppViews.field(activity, "Portfolio website", profile.portfolioWebsite);
        content.addView(portfolio, AppViews.lp(activity, -1, 46, 0, 0, 0, 10));

        TextView step = AppViews.body(activity, "1/5");
        step.setGravity(Gravity.END);
        content.addView(step, AppViews.lp(activity, -1, -2, 0, 6, 0, 0));

        Runnable save = () -> {
            profile.location = location.getText().toString();
            profile.dateOfBirth = dob.getText().toString();
            profile.shortBio = bio.getText().toString();
            profile.linkedinUrl = linkedin.getText().toString();
            profile.githubUrl = github.getText().toString();
            profile.portfolioWebsite = portfolio.getText().toString();
        };

        Runnable next = () -> {
            save.run();
            if (profile.location.trim().isEmpty() || profile.dateOfBirth.trim().isEmpty()) {
                activity.setContentView(create(activity, profile, navigator, onboarding, true));
            } else {
                navigator.showEducation(onboarding);
            }
        };
        Runnable previous = () -> {
            save.run();
            if (onboarding) {
                navigator.showRegister();
            } else {
                navigator.showProfile();
            }
        };

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Previous", previous,
                "Next", next,
                showError ? "Invalid or missing information" : null);

        return AppViews.screenWithBottomActions(activity, "Basic info", previous, content, bottom);
    }
}
