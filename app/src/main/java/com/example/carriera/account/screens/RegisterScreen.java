package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class RegisterScreen {
    private RegisterScreen() {
    }

    public static View create(Activity activity, AccountNavigator navigator, boolean showError) {
        LinearLayout content = AppViews.column(activity);
        content.addView(new View(activity), AppViews.lp(activity, -1, 40, 0, 0, 0, 0));

        EditText firstName = AppViews.field(activity, "first name", "");
        content.addView(firstName, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText lastName = AppViews.field(activity, "last name", "");
        content.addView(lastName, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText email = AppViews.field(activity, "email", "");
        content.addView(email, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText password = AppViews.passwordField(activity, "password", "");
        content.addView(password, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText confirm = AppViews.passwordField(activity, "confirm password", "");
        content.addView(confirm, AppViews.lp(activity, -1, 46, 0, 0, 0, 0));

        LinearLayout bottom = AppViews.bottomBar(
                activity,
                "Back to home", navigator::showHome,
                "Next", () -> navigator.doRegister(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        confirm.getText().toString()),
                showError ? "Invalid or missing information" : null
        );

        return AppViews.screenWithBottomActions(activity, "Register", navigator::showHome, content, bottom);
    }
}
