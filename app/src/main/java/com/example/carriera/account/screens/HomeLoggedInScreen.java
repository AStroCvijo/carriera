package com.example.carriera.account.screens;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

public final class HomeLoggedInScreen {
    private HomeLoggedInScreen() {
    }

    public static View create(
            Activity activity,
            AccountNavigator navigator,
            Runnable onApplicationManager,
            Runnable onFindPositions,
            Runnable onPrepareInterview
    ) {
        LinearLayout root = AppViews.column(activity);
        root.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 16));
        root.setBackgroundColor(AppTheme.BACKGROUND);

        root.addView(HomeViews.titleBar(activity, true, navigator), AppViews.lp(activity, -1, 46, 0, 0, 0, 22));
        root.addView(HomeViews.hero(activity), AppViews.lp(activity, -1, -2, 0, 0, 0, 24));

        root.addView(menuButton(activity, "Application manager", onApplicationManager), AppViews.lp(activity, -1, 50, 0, 0, 0, 14));
        root.addView(menuButton(activity, "Find open positions", onFindPositions), AppViews.lp(activity, -1, 50, 0, 0, 0, 14));
        root.addView(menuButton(activity, "Prepare for interview", onPrepareInterview), AppViews.lp(activity, -1, 50, 0, 0, 0, 0));

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        scrollView.addView(root, new ScrollView.LayoutParams(-1, -1));
        return scrollView;
    }

    private static Button menuButton(Activity activity, String text, Runnable action) {
        Button button = AppViews.actionButton(activity, text);
        button.setTextSize(20);
        button.setTypeface(android.graphics.Typeface.createFromAsset(activity.getAssets(), "fonts/comic_relief_bold.ttf"));
        button.setOnClickListener(v -> action.run());
        return button;
    }
}
