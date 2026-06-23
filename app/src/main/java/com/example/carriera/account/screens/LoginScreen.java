package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppViews;

public final class LoginScreen {
    private LoginScreen() {
    }

    public static View create(Activity activity, AccountNavigator navigator, boolean showError) {
        LinearLayout content = AppViews.column(activity);
        content.addView(new View(activity), AppViews.lp(activity, -1, 70, 0, 0, 0, 0));

        EditText email = AppViews.field(activity, "email", "");
        content.addView(email, AppViews.lp(activity, -1, 46, 0, 0, 0, 14));

        EditText password = AppViews.passwordField(activity, "password", "");
        content.addView(password, AppViews.lp(activity, -1, 46, 0, 0, 0, 8));

        TextView forgot = AppViews.bodyBold(activity, "forgot password?");
        content.addView(forgot);

        LinearLayout bottom = AppViews.bottomBar(
                activity,
                "Register", navigator::showRegister,
                "Login", () -> navigator.doLogin(email.getText().toString(), password.getText().toString()),
                showError ? "Incorrect email or password" : null
        );

        return AppViews.screenWithBottomActions(activity, "Login", navigator::showHome, content, bottom);
    }
}
