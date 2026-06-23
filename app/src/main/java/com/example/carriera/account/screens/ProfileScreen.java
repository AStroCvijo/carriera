package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carriera.R;
import com.example.carriera.account.model.UserProfile;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class ProfileScreen {
    private ProfileScreen() {
    }

    public static View create(Activity activity, UserProfile profile, AccountNavigator navigator) {
        LinearLayout content = AppViews.column(activity);

        ImageView avatar = new ImageView(activity);
        avatar.setImageResource(R.drawable.avatar);
        avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        avatar.setBackgroundColor(android.graphics.Color.WHITE);
        LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(AppViews.dp(activity, 92), AppViews.dp(activity, 92));
        avatarParams.gravity = Gravity.CENTER_HORIZONTAL;
        avatarParams.bottomMargin = AppViews.dp(activity, 22);
        content.addView(avatar, avatarParams);

        EditText firstName = AppViews.field(activity, "first name", profile.firstName);
        content.addView(firstName, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));
        EditText lastName = AppViews.field(activity, "last name", profile.lastName);
        content.addView(lastName, AppViews.lp(activity, -1, 46, 0, 0, 0, 12));
        EditText email = AppViews.field(activity, "email", profile.email);
        content.addView(email, AppViews.lp(activity, -1, 46, 0, 0, 0, 20));

        Runnable save = () -> {
            profile.firstName = firstName.getText().toString();
            profile.lastName = lastName.getText().toString();
            profile.email = email.getText().toString();
        };

        content.addView(menu(activity, "Basic information", R.drawable.ic_info, save, () -> navigator.showBasicInfo(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 12));
        content.addView(menu(activity, "Education", R.drawable.ic_education, save, () -> navigator.showEducation(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 12));
        content.addView(menu(activity, "Experience", R.drawable.ic_experience, save, () -> navigator.showExperience(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 12));
        content.addView(menu(activity, "Skills", R.drawable.ic_skills, save, () -> navigator.showSkills(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 12));
        content.addView(menu(activity, "Job preference", R.drawable.ic_jobpref, save, () -> navigator.showJobPrefs(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 12));
        content.addView(menu(activity, "CV manager", R.drawable.ic_cv, save, () -> navigator.showCvManager(false)),
                AppViews.lp(activity, -1, 52, 0, 0, 0, 0));

        return AppViews.screen(activity, "Profile", navigator::showHomeLoggedIn, content);
    }

    private static Button menu(Activity activity, String text, int icon, Runnable save, Runnable open) {
        Button button = AppViews.menuRow(activity, text, icon);
        button.setOnClickListener(v -> {
            save.run();
            open.run();
        });
        return button;
    }
}
