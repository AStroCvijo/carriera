package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.carriera.R;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class CvManagerScreen {
    private CvManagerScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator, boolean onboarding, boolean showError) {
        LinearLayout content = AppViews.column(activity);
        content.addView(new View(activity), AppViews.lp(activity, -1, 150, 0, 0, 0, 0));

        if (!profile.cvUploaded) {
            Button upload = AppViews.uploadButton(activity, "Upload CV", R.drawable.ic_upload);
            upload.setOnClickListener(v -> {
                profile.cvUploaded = true;
                profile.cvFileName = "my_cv.pdf";
                activity.setContentView(create(activity, profile, navigator, onboarding, false));
            });
            content.addView(upload, AppViews.lp(activity, -1, 50, 0, 0, 0, 0));
        } else {
            content.addView(AppViews.successCard(activity,
                    "All information from CV extracted and user profile updated"),
                    AppViews.lp(activity, -1, -2, 0, 0, 0, 22));

            LinearLayout row = AppViews.row(activity);
            Button current = AppViews.uploadButton(activity, "Current CV", R.drawable.ic_download);
            current.setOnClickListener(v -> Toast.makeText(activity, profile.cvFileName, Toast.LENGTH_SHORT).show());
            row.addView(current, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 50), 1));
            row.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 16), 1));
            Button upload = AppViews.uploadButton(activity, "Upload CV", R.drawable.ic_upload);
            upload.setOnClickListener(v -> Toast.makeText(activity, "New CV uploaded", Toast.LENGTH_SHORT).show());
            row.addView(upload, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 50), 1));
            content.addView(row, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));
        }

        Runnable back = () -> {
            if (onboarding) {
                navigator.showJobPrefs(true);
            } else {
                navigator.showProfile();
            }
        };
        Runnable next = () -> {
            if (onboarding) {
                navigator.finishOnboarding();
            } else {
                navigator.showProfile();
            }
        };

        LinearLayout bottom = AppViews.bottomBar(activity,
                "Back", back,
                "Next", next,
                showError ? "Invalid format or file too large" : null);

        return AppViews.screenWithBottomActions(activity, "CV manager", back, content, bottom);
    }
}
