package com.example.carriera.account.screens;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.carriera.R;
import com.example.carriera.account.navigation.AccountNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

/** Shared header + hero illustration used by the two home screens. */
final class HomeViews {
    private HomeViews() {
    }

    static FrameLayout titleBar(Activity activity, boolean loggedIn, AccountNavigator navigator) {
        FrameLayout bar = new FrameLayout(activity);
        bar.setBackground(rounded(AppTheme.HEADER, AppViews.dp(activity, 12)));

        Button title = new Button(activity);
        title.setAllCaps(false);
        title.setText("Carriera");
        title.setTextSize(24);
        title.setTextColor(AppTheme.WHITE);
        title.setTypeface(android.graphics.Typeface.createFromAsset(activity.getAssets(), "fonts/comic_relief_bold.ttf"));
        title.setBackgroundColor(Color.TRANSPARENT);
        title.setGravity(Gravity.CENTER);
        title.setSingleLine(true);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setClickable(false);
        title.setFocusable(false);
        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(-1, -1, Gravity.CENTER);
        int titleSideInset = AppViews.dp(activity, loggedIn ? 94 : 48);
        titleParams.leftMargin = titleSideInset;
        titleParams.rightMargin = titleSideInset;
        bar.addView(title, titleParams);

        if (loggedIn) {
            ImageView avatar = new ImageView(activity);
            avatar.setImageResource(R.drawable.avatar);
            avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
            avatar.setBackground(rounded(AppTheme.WHITE, AppViews.dp(activity, 8)));
            avatar.setClickable(true);
            avatar.setOnClickListener(v -> navigator.showProfile());
            FrameLayout.LayoutParams avatarParams = new FrameLayout.LayoutParams(AppViews.dp(activity, 36), AppViews.dp(activity, 36), Gravity.LEFT | Gravity.CENTER_VERTICAL);
            avatarParams.leftMargin = AppViews.dp(activity, 8);
            bar.addView(avatar, avatarParams);

            Button logout = AppViews.bigButton(activity, "Logout");
            logout.setTextSize(13);
            logout.setOnClickListener(v -> navigator.logout());
            FrameLayout.LayoutParams logoutParams = new FrameLayout.LayoutParams(AppViews.dp(activity, 78), AppViews.dp(activity, 30), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            logoutParams.rightMargin = AppViews.dp(activity, 8);
            bar.addView(logout, logoutParams);
        } else {
            ImageView logo = new ImageView(activity);
            logo.setImageResource(R.drawable.home_logo);
            logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
            FrameLayout.LayoutParams logoParams = new FrameLayout.LayoutParams(AppViews.dp(activity, 30), AppViews.dp(activity, 30), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            logoParams.rightMargin = AppViews.dp(activity, 10);
            bar.addView(logo, logoParams);
        }
        return bar;
    }

    static ImageView hero(Activity activity) {
        ImageView hero = new ImageView(activity);
        hero.setImageResource(R.drawable.hero);
        hero.setAdjustViewBounds(true);
        hero.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return hero;
    }

    private static GradientDrawable rounded(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }
}
