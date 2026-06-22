package com.example.carriera.applications.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.carriera.R;

public final class AppViews {
    private static Typeface interRegular;
    private static Typeface interBold;
    private static Typeface comicReliefBold;

    private AppViews() {
    }

    public static ScrollView screen(Activity activity, String title, Runnable backAction, LinearLayout content) {
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(activity, 14), dp(activity, 14), dp(activity, 14), dp(activity, 16));
        root.setBackgroundColor(AppTheme.BACKGROUND);

        root.addView(header(activity, title, backAction), lp(activity, -1, 54, 0, 0, 0, 34));
        root.addView(content, lp(activity, -1, -2, 0, 0, 0, 0));

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        scrollView.addView(root, new ScrollView.LayoutParams(-1, -2));
        return scrollView;
    }

    public static View screenWithBottomActions(
            Activity activity,
            String title,
            Runnable backAction,
            LinearLayout content,
            LinearLayout bottomActions
    ) {
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(activity, 14), dp(activity, 14), dp(activity, 14), dp(activity, 16));
        root.setBackgroundColor(AppTheme.BACKGROUND);

        root.addView(header(activity, title, backAction), lp(activity, -1, 54, 0, 0, 0, 34));

        ScrollView contentScroll = new ScrollView(activity);
        contentScroll.setFillViewport(false);
        contentScroll.addView(content, new ScrollView.LayoutParams(-1, -2));
        root.addView(contentScroll, new LinearLayout.LayoutParams(-1, 0, 1));

        root.addView(bottomActions, lp(activity, -1, -2, 0, 0, 0, 14));

        return root;
    }

    public static LinearLayout column(Activity activity) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    public static LinearLayout row(Activity activity) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        return layout;
    }

    public static TextView title(Activity activity, String text) {
        TextView view = text(activity, text, 24, AppTheme.WHITE, true);
        view.setTypeface(comicReliefBold(activity));
        view.setGravity(Gravity.CENTER);
        view.setSingleLine(true);
        return view;
    }

    public static TextView label(Activity activity, String text) {
        TextView view = text(activity, text, 11, AppTheme.TEXT, true);
        view.setPadding(0, dp(activity, 8), 0, dp(activity, 6));
        return view;
    }

    public static TextView body(Activity activity, String text) {
        return text(activity, text, 12, AppTheme.TEXT, false);
    }

    public static TextView bodyBold(Activity activity, String text) {
        return text(activity, text, 12, AppTheme.TEXT, true);
    }

    public static TextView cardTitle(Activity activity, String text) {
        return text(activity, text, 14, AppTheme.TEXT, true);
    }

    public static TextView card(Activity activity, String text) {
        TextView view = body(activity, text);
        view.setLineSpacing(dp(activity, 2), 1f);
        view.setPadding(dp(activity, 12), dp(activity, 10), dp(activity, 12), dp(activity, 10));
        view.setBackground(round(activity, AppTheme.CARD, 10));
        return view;
    }

    public static LinearLayout detailCard(Activity activity, String title, String body) {
        LinearLayout card = column(activity);
        card.setPadding(dp(activity, 18), dp(activity, 14), dp(activity, 18), dp(activity, 14));
        card.setBackground(round(activity, AppTheme.CARD, 22));

        TextView titleView = text(activity, title, 16, AppTheme.TEXT, true);
        card.addView(titleView);

        if (body != null && !body.trim().isEmpty()) {
            TextView bodyView = text(activity, body, 14, AppTheme.TEXT, false);
            bodyView.setLineSpacing(dp(activity, 2), 1f);
            card.addView(bodyView, lp(activity, -1, -2, 0, 8, 0, 0));
        }
        return card;
    }

    public static LinearLayout compactDetailCard(Activity activity, String title, String body) {
        LinearLayout card = column(activity);
        card.setPadding(dp(activity, 18), dp(activity, 10), dp(activity, 18), dp(activity, 10));
        card.setBackground(round(activity, AppTheme.CARD, 18));

        TextView titleView = text(activity, title, 15, AppTheme.TEXT, true);
        card.addView(titleView);

        if (body != null && !body.trim().isEmpty()) {
            TextView bodyView = text(activity, body, 12, AppTheme.TEXT, false);
            bodyView.setLineSpacing(dp(activity, 1), 1f);
            card.addView(bodyView, lp(activity, -1, -2, 0, 6, 0, 0));
        }
        return card;
    }

    public static EditText searchInput(Activity activity) {
        EditText input = new EditText(activity);
        input.setSingleLine(true);
        input.setHint("Search applications...");
        input.setTextSize(12);
        input.setTypeface(interBold(activity));
        input.setTextColor(AppTheme.TEXT);
        input.setHintTextColor(AppTheme.MUTED);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);
        input.setCompoundDrawablePadding(dp(activity, 10));
        input.setPadding(dp(activity, 14), 0, dp(activity, 14), 0);
        input.setBackground(round(activity, AppTheme.CARD, 18));
        return input;
    }

    public static EditText input(Activity activity, String text, int minLines) {
        EditText input = new EditText(activity);
        input.setText(text);
        input.setTextSize(12);
        input.setTypeface(interRegular(activity));
        input.setTextColor(AppTheme.TEXT);
        input.setMinLines(minLines);
        input.setGravity(Gravity.TOP);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setPadding(dp(activity, 12), dp(activity, 10), dp(activity, 12), dp(activity, 10));
        input.setBackground(round(activity, AppTheme.CARD, 10));
        return input;
    }

    public static Button pill(Activity activity, String text, boolean selected) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(text);
        button.setTextSize(12);
        button.setTypeface(interBold(activity));
        button.setTextColor(selected ? AppTheme.WHITE : AppTheme.TEXT);
        button.setPadding(dp(activity, 4), 0, dp(activity, 4), 0);
        button.setBackground(round(activity, selected ? AppTheme.HEADER : AppTheme.PILL, 12));
        return button;
    }

    public static Button darkButton(Activity activity, String text) {
        Button button = pill(activity, text, true);
        return button;
    }

    public static Button detailsButton(Activity activity) {
        Button button = pill(activity, "Details", true);
        button.setTextSize(14);
        button.setTypeface(interBold(activity));
        button.setBackground(round(activity, AppTheme.HEADER, 22));
        return button;
    }

    public static LinearLayout questionCard(Activity activity) {
        LinearLayout card = row(activity);
        card.setPadding(dp(activity, 12), dp(activity, 10), dp(activity, 12), dp(activity, 10));
        card.setBackground(round(activity, AppTheme.CARD, 12));
        return card;
    }

    public static Button actionButton(Activity activity, String text) {
        Button button = pill(activity, text, true);
        button.setTextSize(15);
        button.setTypeface(interBold(activity));
        button.setSingleLine(true);
        button.setPadding(dp(activity, 14), 0, dp(activity, 14), 0);
        button.setBackground(round(activity, AppTheme.HEADER, 20));
        return button;
    }

    public static Button bottomButton(Activity activity, String text) {
        Button button = pill(activity, text, false);
        button.setTextSize(15);
        button.setTypeface(interBold(activity));
        button.setSingleLine(true);
        button.setPadding(dp(activity, 12), 0, dp(activity, 12), 0);
        button.setBackground(round(activity, AppTheme.PILL, 16));
        return button;
    }

    public static Button simulationButton(Activity activity, String text) {
        Button button = bottomButton(activity, text);
        button.setTextSize(13);
        button.setSingleLine(false);
        button.setGravity(Gravity.CENTER);
        button.setBackground(round(activity, AppTheme.PILL, 12));
        return button;
    }

    public static TextView statusLabel(Activity activity, String text) {
        TextView view = text(activity, text, 16, AppTheme.TEXT, false);
        return view;
    }

    public static TextView formLabel(Activity activity, String text) {
        return text(activity, text, 14, AppTheme.TEXT, true);
    }

    public static TextView warning(Activity activity, String text) {
        TextView view = text(activity, text, 14, AppTheme.WHITE, true);
        view.setGravity(Gravity.CENTER);
        view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0);
        view.setCompoundDrawablePadding(dp(activity, 10));
        view.setPadding(dp(activity, 8), dp(activity, 5), dp(activity, 8), dp(activity, 5));
        view.setBackground(round(activity, AppTheme.WARNING, 8));
        return view;
    }

    public static LinearLayout.LayoutParams lp(Activity activity, int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height < 0 ? height : dp(activity, height));
        params.setMargins(dp(activity, left), dp(activity, top), dp(activity, right), dp(activity, bottom));
        return params;
    }

    public static int dp(Activity activity, int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }

    public static GradientDrawable roundedBackground(Activity activity, int color, int radius) {
        return round(activity, color, radius);
    }

    private static FrameLayout header(Activity activity, String title, Runnable backAction) {
        FrameLayout header = new FrameLayout(activity);
        header.setPadding(dp(activity, 8), 0, dp(activity, 8), 0);
        header.setBackground(round(activity, AppTheme.HEADER, 10));

        TextView back = text(activity, "back", 18, AppTheme.WHITE, true);
        back.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        back.setCompoundDrawablePadding(dp(activity, 8));
        back.setGravity(Gravity.CENTER_VERTICAL);
        back.setOnClickListener(v -> backAction.run());
        FrameLayout.LayoutParams backParams = new FrameLayout.LayoutParams(dp(activity, 110), -1, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        header.addView(back, backParams);

        TextView titleView = title(activity, title);
        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(-1, -1, Gravity.CENTER);
        titleParams.leftMargin = dp(activity, 52);
        titleParams.rightMargin = dp(activity, 52);
        header.addView(titleView, titleParams);

        ImageView icon = new ImageView(activity);
        icon.setImageResource(R.drawable.ic_assistant);
        icon.setPadding(dp(activity, 2), dp(activity, 2), dp(activity, 2), dp(activity, 2));
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(dp(activity, 34), dp(activity, 34), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        iconParams.rightMargin = dp(activity, 8);
        header.addView(icon, iconParams);
        return header;
    }

    private static TextView text(Activity activity, String value, int sp, int color, boolean bold) {
        TextView text = new TextView(activity);
        text.setText(value);
        text.setTextSize(sp);
        text.setTextColor(color);
        text.setTypeface(bold ? interBold(activity) : interRegular(activity));
        return text;
    }

    private static Typeface interRegular(Activity activity) {
        if (interRegular == null) {
            interRegular = font(activity, "fonts/inter_regular.ttf", Typeface.NORMAL);
        }
        return interRegular;
    }

    private static Typeface interBold(Activity activity) {
        if (interBold == null) {
            interBold = font(activity, "fonts/inter_bold.ttf", Typeface.BOLD);
        }
        return interBold;
    }

    private static Typeface comicReliefBold(Activity activity) {
        if (comicReliefBold == null) {
            comicReliefBold = font(activity, "fonts/comic_relief_bold.ttf", Typeface.BOLD);
        }
        return comicReliefBold;
    }

    private static Typeface font(Activity activity, String path, int fallbackStyle) {
        try {
            return Typeface.createFromAsset(activity.getAssets(), path);
        } catch (RuntimeException ignored) {
            return Typeface.create("sans-serif", fallbackStyle);
        }
    }

    private static GradientDrawable round(Activity activity, int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(activity, radius));
        return drawable;
    }

    private static GradientDrawable outline(Activity activity, int color, int stroke, int radius) {
        GradientDrawable drawable = round(activity, color, radius);
        drawable.setStroke(dp(activity, 1), stroke);
        return drawable;
    }
}
