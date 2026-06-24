package com.example.carriera.applications.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carriera.R;

import java.util.ArrayList;
import java.util.List;

public final class AppViews {
    private static Typeface interRegular;
    private static Typeface interBold;
    private static Typeface comicReliefBold;
    private static Runnable homeAction;

    private AppViews() {
    }

    /** Wired once by MainActivity; the header logo and home shortcuts use it. */
    public static void setHomeAction(Runnable action) {
        homeAction = action;
    }

    public static void goHome() {
        if (homeAction != null) {
            homeAction.run();
        }
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

    // ---- Account / profile form helpers ----

    public static EditText field(Activity activity, String hint, String value) {
        EditText input = new EditText(activity);
        input.setText(value == null ? "" : value);
        input.setHint(hint);
        input.setSingleLine(true);
        input.setTextSize(13);
        input.setTypeface(interBold(activity));
        input.setTextColor(AppTheme.TEXT);
        input.setHintTextColor(AppTheme.MUTED);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setPadding(dp(activity, 16), 0, dp(activity, 16), 0);
        input.setBackground(round(activity, AppTheme.CARD, 16));
        return input;
    }

    public static EditText multilineField(Activity activity, String hint, String value, int minLines) {
        EditText input = new EditText(activity);
        input.setText(value == null ? "" : value);
        input.setHint(hint);
        input.setTextSize(13);
        input.setTypeface(interBold(activity));
        input.setTextColor(AppTheme.TEXT);
        input.setHintTextColor(AppTheme.MUTED);
        input.setMinLines(minLines);
        input.setGravity(Gravity.TOP);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setPadding(dp(activity, 16), dp(activity, 12), dp(activity, 16), dp(activity, 12));
        input.setBackground(round(activity, AppTheme.CARD, 16));
        return input;
    }

    public static EditText iconField(Activity activity, String hint, String value, int iconRes) {
        EditText input = field(activity, hint, value);
        input.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);
        input.setCompoundDrawablePadding(dp(activity, 10));
        return input;
    }

    public static Spinner dropdown(Activity activity, String hint, String[] options, String value) {
        List<String> items = new ArrayList<>();
        items.add(hint);
        for (String option : options) {
            items.add(option);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                styleSpinnerText(activity, view, position == 0);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                styleSpinnerText(activity, view, position == 0);
                view.setBackgroundColor(AppTheme.PILL);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = new Spinner(activity);
        spinner.setAdapter(adapter);
        spinner.setPadding(dp(activity, 40), 0, dp(activity, 14), 0);
        spinner.setBackgroundResource(R.drawable.bg_dropdown_field);
        if (value != null) {
            int index = items.indexOf(value);
            if (index > 0) {
                spinner.setSelection(index);
            }
        }
        return spinner;
    }

    public static String dropdownValue(Spinner spinner) {
        int position = spinner.getSelectedItemPosition();
        return position <= 0 ? "" : String.valueOf(spinner.getSelectedItem());
    }

    private static void styleSpinnerText(Activity activity, TextView view, boolean placeholder) {
        view.setTextSize(13);
        view.setTextColor(placeholder ? AppTheme.MUTED : AppTheme.TEXT);
        view.setTypeface(interBold(activity));
    }

    public static EditText passwordField(Activity activity, String hint, String value) {
        EditText input = iconField(activity, hint, value, R.drawable.ic_eye_off);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return input;
    }

    public static Button uploadButton(Activity activity, String text, int iconRes) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(text);
        button.setTextSize(14);
        button.setTypeface(interBold(activity));
        button.setTextColor(AppTheme.TEXT);
        button.setGravity(Gravity.CENTER);
        button.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);
        button.setCompoundDrawablePadding(dp(activity, 12));
        button.setPadding(dp(activity, 16), 0, dp(activity, 16), 0);
        button.setBackground(round(activity, AppTheme.CARD, 16));
        return button;
    }

    public static Button addRowButton(Activity activity, String text) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(text);
        button.setTextSize(14);
        button.setTypeface(interBold(activity));
        button.setTextColor(AppTheme.TEXT);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_plus, 0, 0, 0);
        button.setCompoundDrawablePadding(dp(activity, 14));
        button.setPadding(dp(activity, 18), 0, dp(activity, 16), 0);
        button.setBackground(round(activity, AppTheme.CARD, 16));
        return button;
    }

    public static Button bigButton(Activity activity, String text) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(text);
        button.setTextSize(15);
        button.setTypeface(interBold(activity));
        button.setTextColor(AppTheme.TEXT);
        button.setGravity(Gravity.CENTER);
        button.setSingleLine(true);
        button.setPadding(dp(activity, 8), 0, dp(activity, 8), 0);
        button.setBackground(round(activity, AppTheme.PILL, 14));
        return button;
    }

    public static Button menuRow(Activity activity, String text, int iconRes) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(text);
        button.setTextSize(16);
        button.setTypeface(interBold(activity));
        button.setTextColor(AppTheme.TEXT);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        button.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);
        button.setCompoundDrawablePadding(dp(activity, 16));
        button.setPadding(dp(activity, 20), 0, dp(activity, 16), 0);
        button.setBackground(round(activity, AppTheme.PILL, 18));
        return button;
    }

    public static TextView sectionPill(Activity activity, String text) {
        TextView view = text(activity, text, 15, AppTheme.TEXT, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(activity, 12), dp(activity, 10), dp(activity, 12), dp(activity, 10));
        view.setBackground(round(activity, AppTheme.CARD, 16));
        return view;
    }

    public static TextView chip(Activity activity, String value) {
        TextView view = text(activity, value, 13, AppTheme.TEXT, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(activity, 14), dp(activity, 8), dp(activity, 14), dp(activity, 8));
        view.setBackground(round(activity, AppTheme.PILL, 14));
        return view;
    }

    public static Button plusChip(Activity activity) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText("+");
        button.setTextSize(20);
        button.setTypeface(interBold(activity));
        button.setTextColor(AppTheme.TEXT);
        button.setGravity(Gravity.CENTER);
        button.setPadding(0, 0, 0, 0);
        button.setBackground(round(activity, AppTheme.PILL, 14));
        return button;
    }

    public static LinearLayout checkRow(Activity activity, String text, boolean[] state) {
        LinearLayout row = row(activity);
        row.setPadding(dp(activity, 14), dp(activity, 8), dp(activity, 16), dp(activity, 8));
        row.setBackground(round(activity, AppTheme.PILL, 16));

        ImageView box = new ImageView(activity);
        box.setImageResource(state[0] ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox);
        row.addView(box, new LinearLayout.LayoutParams(dp(activity, 22), dp(activity, 22)));

        TextView label = text(activity, text, 14, AppTheme.TEXT, true);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(-2, -2);
        labelParams.leftMargin = dp(activity, 10);
        row.addView(label, labelParams);

        row.setOnClickListener(v -> {
            state[0] = !state[0];
            box.setImageResource(state[0] ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox);
        });
        return row;
    }

    public static LinearLayout successCard(Activity activity, String text) {
        LinearLayout card = row(activity);
        card.setPadding(dp(activity, 18), dp(activity, 16), dp(activity, 18), dp(activity, 16));
        card.setBackground(round(activity, AppTheme.CARD, 16));

        ImageView icon = new ImageView(activity);
        icon.setImageResource(R.drawable.ic_check);
        card.addView(icon, new LinearLayout.LayoutParams(dp(activity, 26), dp(activity, 26)));

        TextView label = text(activity, text, 14, AppTheme.TEXT, true);
        label.setLineSpacing(dp(activity, 2), 1f);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(0, -2, 1);
        labelParams.leftMargin = dp(activity, 14);
        card.addView(label, labelParams);
        return card;
    }

    public static LinearLayout bottomBar(
            Activity activity,
            String leftText,
            Runnable leftAction,
            String rightText,
            Runnable rightAction,
            String error
    ) {
        LinearLayout container = column(activity);
        if (error != null) {
            container.addView(warning(activity, error), lp(activity, -1, -2, 0, 0, 0, 12));
        }
        LinearLayout buttons = row(activity);
        Button left = bigButton(activity, leftText);
        left.setOnClickListener(v -> leftAction.run());
        buttons.addView(left, new LinearLayout.LayoutParams(0, dp(activity, 46), 1));
        buttons.addView(new View(activity), new LinearLayout.LayoutParams(dp(activity, 16), 1));
        Button right = bigButton(activity, rightText);
        right.setOnClickListener(v -> rightAction.run());
        buttons.addView(right, new LinearLayout.LayoutParams(0, dp(activity, 46), 1));
        container.addView(buttons);
        return container;
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
        titleView.setSingleLine(true);
        titleView.setTextSize(title.length() > 16 ? 18 : 24);
        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(-1, -1, Gravity.CENTER);
        titleParams.leftMargin = dp(activity, 112);
        titleParams.rightMargin = dp(activity, 52);
        header.addView(titleView, titleParams);

        ImageView icon = new ImageView(activity);
        icon.setImageResource(R.drawable.home_logo);
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icon.setPadding(dp(activity, 2), dp(activity, 2), dp(activity, 2), dp(activity, 2));
        icon.setOnClickListener(v -> goHome());
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
