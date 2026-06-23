package com.example.carriera.account.screens;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

public final class HomeScreen {
    private static final String ABOUT =
            "Carriera is a smart job search assistant. Upload your CV, get jobs ranked by "
                    + "match percentage, and clearly see which skills you're missing. The app generates "
                    + "a tailored CV and cover letter for each listing, tracks your applications, and "
                    + "prepares you for interviews.";

    private HomeScreen() {
    }

    public static View create(Activity activity, AccountNavigator navigator) {
        LinearLayout root = AppViews.column(activity);
        root.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 16));
        root.setBackgroundColor(AppTheme.BACKGROUND);

        root.addView(HomeViews.titleBar(activity, false, navigator), AppViews.lp(activity, -1, 46, 0, 0, 0, 18));
        root.addView(HomeViews.hero(activity), AppViews.lp(activity, -1, -2, 0, 0, 0, 16));

        root.addView(divider(activity), AppViews.lp(activity, -1, 1, 0, 4, 0, 12));
        root.addView(AppViews.bodyBold(activity, "What it's all about"));
        TextView about = AppViews.body(activity, ABOUT);
        about.setLineSpacing(AppViews.dp(activity, 3), 1f);
        root.addView(about, AppViews.lp(activity, -1, -2, 0, 8, 0, 12));
        root.addView(divider(activity), AppViews.lp(activity, -1, 1, 0, 0, 0, 0));

        root.addView(new View(activity), new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout buttons = AppViews.row(activity);
        Button login = AppViews.bigButton(activity, "Login");
        login.setOnClickListener(v -> navigator.showLogin());
        buttons.addView(login, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        buttons.addView(new View(activity), new LinearLayout.LayoutParams(AppViews.dp(activity, 16), 1));
        Button register = AppViews.bigButton(activity, "Register");
        register.setOnClickListener(v -> navigator.showRegister());
        buttons.addView(register, new LinearLayout.LayoutParams(0, AppViews.dp(activity, 46), 1));
        root.addView(buttons, AppViews.lp(activity, -1, -2, 0, 14, 0, 0));

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        scrollView.addView(root, new ScrollView.LayoutParams(-1, -1));
        return scrollView;
    }

    private static View divider(Activity activity) {
        View line = new View(activity);
        line.setBackgroundColor(Color.rgb(1, 22, 56));
        return line;
    }
}
