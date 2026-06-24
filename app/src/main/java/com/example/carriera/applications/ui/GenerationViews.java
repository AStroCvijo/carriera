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
import com.example.carriera.applications.model.ApplicationDraft;

public final class GenerationViews {
    private static final int FIGMA_BACKGROUND = android.graphics.Color.rgb(222, 223, 247);
    private static final int FIGMA_HEADER = android.graphics.Color.rgb(46, 41, 78);
    private static final int FIGMA_CARD = android.graphics.Color.rgb(147, 149, 211);
    private static final int FIGMA_TEXT = android.graphics.Color.rgb(28, 28, 31);
    private static final int FIGMA_MUTED = android.graphics.Color.rgb(55, 56, 80);
    private static final int FIGMA_WARNING = android.graphics.Color.rgb(1, 22, 56);

    private GenerationViews() {
    }

    public static void scrollHome() {
        AppViews.goHome();
    }

    public static View screen(Activity activity, String title, Runnable backAction, LinearLayout content) {
        LinearLayout root = AppViews.column(activity);
        root.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 14), AppViews.dp(activity, 16));
        root.setBackgroundColor(FIGMA_BACKGROUND);

        root.addView(header(activity, title, backAction), AppViews.lp(activity, -1, 42, 2, 4, 2, 18));

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        scrollView.setClipToPadding(false);
        content.setPadding(AppViews.dp(activity, 2), 0, AppViews.dp(activity, 2), AppViews.dp(activity, 4));
        scrollView.addView(content, new ScrollView.LayoutParams(-1, -1));
        root.addView(scrollView, new LinearLayout.LayoutParams(-1, 0, 1));
        return root;
    }

    public static LinearLayout content(Activity activity) {
        LinearLayout content = AppViews.column(activity);
        content.setGravity(Gravity.TOP);
        return content;
    }

    public static View flexibleSpacer(Activity activity, int minDp) {
        View spacer = new View(activity);
        spacer.setMinimumHeight(AppViews.dp(activity, minDp));
        spacer.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1));
        return spacer;
    }

    public static View gap(Activity activity, int heightDp) {
        View gap = new View(activity);
        gap.setLayoutParams(new LinearLayout.LayoutParams(-1, AppViews.dp(activity, heightDp)));
        return gap;
    }

    public static LinearLayout jobCard(Activity activity, ApplicationDraft draft) {
        LinearLayout card = AppViews.column(activity);
        card.setPadding(AppViews.dp(activity, 18), AppViews.dp(activity, 12), AppViews.dp(activity, 18), AppViews.dp(activity, 10));
        card.setBackground(round(activity, FIGMA_CARD, 16));

        TextView position = text(activity, draft.position, 15, FIGMA_TEXT, true);
        card.addView(position);
        TextView company = text(activity, draft.company, 12, FIGMA_TEXT, false);
        card.addView(company, AppViews.lp(activity, -1, -2, 0, 3, 0, 0));

        LinearLayout meta = AppViews.row(activity);
        meta.setGravity(Gravity.CENTER_VERTICAL);
        TextView match = text(activity, "Match: " + draft.matchPercent + "%", 12, FIGMA_TEXT, false);
        meta.addView(match, new LinearLayout.LayoutParams(0, -2, 1));
        TextView deadline = text(activity, "Deadline: " + draft.deadline, 12, FIGMA_TEXT, false);
        deadline.setGravity(Gravity.RIGHT);
        meta.addView(deadline, new LinearLayout.LayoutParams(0, -2, 1));
        card.addView(meta, AppViews.lp(activity, -1, -2, 0, 12, 0, 0));
        return card;
    }

    public static LinearLayout checkRow(Activity activity, String value, boolean[] state) {
        LinearLayout row = AppViews.row(activity);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, 0, 0, 0);

        ImageView box = new ImageView(activity);
        box.setImageResource(state[0] ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox);
        row.addView(box, new LinearLayout.LayoutParams(AppViews.dp(activity, 20), AppViews.dp(activity, 20)));

        TextView label = text(activity, value, 13, FIGMA_TEXT, true);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(-2, -2);
        labelParams.leftMargin = AppViews.dp(activity, 8);
        row.addView(label, labelParams);

        row.setOnClickListener(v -> {
            state[0] = !state[0];
            box.setImageResource(state[0] ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox);
        });
        return row;
    }

    public static LinearLayout focusChips(Activity activity, ApplicationDraft draft) {
        LinearLayout row = AppViews.row(activity);
        row.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        int[] widths = {76, 76, 106};
        for (int i = 0; i < draft.focusAreas.size(); i++) {
            TextView chip = text(activity, draft.focusAreas.get(i), 11, FIGMA_TEXT, true);
            chip.setGravity(Gravity.CENTER);
            chip.setSingleLine(true);
            chip.setPadding(AppViews.dp(activity, 4), 0, AppViews.dp(activity, 4), 0);
            chip.setBackground(round(activity, FIGMA_CARD, 10));
            int width = i < widths.length ? widths[i] : 86;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppViews.dp(activity, width), AppViews.dp(activity, 22));
            if (i > 0) {
                params.leftMargin = AppViews.dp(activity, 12);
            }
            row.addView(chip, params);
        }
        return row;
    }

    public static EditText noteInput(Activity activity, String value) {
        EditText input = new EditText(activity);
        input.setText(value == null ? "" : value);
        input.setTextSize(11);
        input.setTypeface(font(activity, false));
        input.setTextColor(FIGMA_TEXT);
        input.setHintTextColor(FIGMA_MUTED);
        input.setGravity(Gravity.TOP | Gravity.START);
        input.setSingleLine(false);
        input.setMinLines(2);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setPadding(AppViews.dp(activity, 10), AppViews.dp(activity, 6), AppViews.dp(activity, 10), AppViews.dp(activity, 6));
        input.setBackground(round(activity, android.graphics.Color.WHITE, 10));
        return input;
    }

    public static EditText editablePreviewBox(Activity activity, String content, int textSize, int minLines) {
        EditText input = new EditText(activity);
        input.setText(content == null ? "" : content);
        input.setTextSize(textSize);
        input.setTypeface(font(activity, false));
        input.setTextColor(FIGMA_TEXT);
        input.setHintTextColor(FIGMA_MUTED);
        input.setGravity(Gravity.TOP | Gravity.START);
        input.setSingleLine(false);
        input.setMinLines(minLines);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setPadding(AppViews.dp(activity, 12), AppViews.dp(activity, 10), AppViews.dp(activity, 12), AppViews.dp(activity, 10));
        input.setBackground(round(activity, android.graphics.Color.WHITE, 10));
        input.setLineSpacing(AppViews.dp(activity, 2), 1f);
        input.setHorizontallyScrolling(false);
        return input;
    }

    public static TextView sectionLabel(Activity activity, String value) {
        return text(activity, value, 13, FIGMA_TEXT, true);
    }

    public static Button button(Activity activity, String value) {
        Button button = new Button(activity);
        button.setAllCaps(false);
        button.setText(value);
        button.setTextSize(12);
        button.setTypeface(font(activity, true));
        button.setTextColor(FIGMA_TEXT);
        button.setGravity(Gravity.CENTER);
        button.setSingleLine(true);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setMinWidth(0);
        button.setMinimumWidth(0);
        button.setPadding(AppViews.dp(activity, 8), 0, AppViews.dp(activity, 8), 0);
        button.setBackground(round(activity, FIGMA_CARD, 13));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            button.setStateListAnimator(null);
        }
        return button;
    }

    public static TextView errorBanner(Activity activity) {
        TextView warning = text(activity, "Generate or add content\nbefore saving.", 13, android.graphics.Color.WHITE, true);
        warning.setGravity(Gravity.CENTER);
        warning.setLineSpacing(AppViews.dp(activity, 1), 1f);
        warning.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0);
        warning.setCompoundDrawablePadding(AppViews.dp(activity, 8));
        warning.setPadding(AppViews.dp(activity, 8), AppViews.dp(activity, 5), AppViews.dp(activity, 8), AppViews.dp(activity, 5));
        warning.setBackground(round(activity, FIGMA_WARNING, 7));
        return warning;
    }

    public static LinearLayout submittedDocument(Activity activity, String value) {
        LinearLayout row = AppViews.row(activity);
        row.setGravity(Gravity.CENTER_VERTICAL);
        ImageView icon = new ImageView(activity);
        icon.setImageResource(R.drawable.ic_check);
        row.addView(icon, new LinearLayout.LayoutParams(AppViews.dp(activity, 20), AppViews.dp(activity, 20)));
        TextView label = text(activity, value, 13, FIGMA_TEXT, true);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(-2, -2);
        labelParams.leftMargin = AppViews.dp(activity, 8);
        row.addView(label, labelParams);
        return row;
    }

    public static LinearLayout horizontalSubmittedDocuments(Activity activity, ApplicationDraft draft) {
        LinearLayout row = AppViews.row(activity);
        row.setGravity(Gravity.CENTER_VERTICAL);
        if (draft.includeTailoredCv && draft.includeCoverLetter) {
            row.addView(submittedDocument(activity, "Tailored CV"), new LinearLayout.LayoutParams(0, AppViews.dp(activity, 30), 1));
            row.addView(submittedDocument(activity, "Cover letter"), new LinearLayout.LayoutParams(0, AppViews.dp(activity, 30), 1));
        } else if (draft.includeTailoredCv) {
            row.addView(submittedDocument(activity, "Tailored CV"), new LinearLayout.LayoutParams(-2, AppViews.dp(activity, 30)));
        } else if (draft.includeCoverLetter) {
            row.addView(submittedDocument(activity, "Cover letter"), new LinearLayout.LayoutParams(-2, AppViews.dp(activity, 30)));
        }
        return row;
    }

    public static TextView body(Activity activity, String value, int sp, boolean bold) {
        TextView text = text(activity, value, sp, FIGMA_TEXT, bold);
        text.setLineSpacing(AppViews.dp(activity, 2), 1f);
        return text;
    }

    private static FrameLayout header(Activity activity, String title, Runnable backAction) {
        FrameLayout header = new FrameLayout(activity);
        header.setPadding(AppViews.dp(activity, 7), 0, AppViews.dp(activity, 7), 0);
        header.setBackground(round(activity, FIGMA_HEADER, 10));

        TextView back = text(activity, "back", 16, android.graphics.Color.WHITE, true);
        back.setGravity(Gravity.CENTER_VERTICAL);
        back.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        back.setCompoundDrawablePadding(AppViews.dp(activity, 7));
        back.setOnClickListener(v -> backAction.run());
        header.addView(back, new FrameLayout.LayoutParams(AppViews.dp(activity, 92), -1, Gravity.LEFT | Gravity.CENTER_VERTICAL));

        TextView titleView = text(activity, title, 24, android.graphics.Color.WHITE, true);
        titleView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/comic_relief_bold.ttf"));
        titleView.setGravity(Gravity.CENTER);
        titleView.setSingleLine(true);
        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(-1, -1, Gravity.CENTER);
        titleParams.leftMargin = AppViews.dp(activity, 54);
        titleParams.rightMargin = AppViews.dp(activity, 54);
        header.addView(titleView, titleParams);

        ImageView logo = new ImageView(activity);
        logo.setImageResource(R.drawable.home_logo);
        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        logo.setOnClickListener(v -> AppViews.goHome());
        FrameLayout.LayoutParams logoParams = new FrameLayout.LayoutParams(AppViews.dp(activity, 22), AppViews.dp(activity, 22), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        logoParams.rightMargin = AppViews.dp(activity, 10);
        header.addView(logo, logoParams);
        return header;
    }

    private static TextView text(Activity activity, String value, int sp, int color, boolean bold) {
        TextView text = new TextView(activity);
        text.setText(value);
        text.setTextSize(sp);
        text.setTextColor(color);
        text.setIncludeFontPadding(true);
        text.setTypeface(font(activity, bold));
        return text;
    }

    private static Typeface font(Activity activity, boolean bold) {
        try {
            return Typeface.createFromAsset(activity.getAssets(), bold ? "fonts/inter_bold.ttf" : "fonts/inter_regular.ttf");
        } catch (RuntimeException ignored) {
            return Typeface.create("sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL);
        }
    }

    private static GradientDrawable round(Activity activity, int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(AppViews.dp(activity, radius));
        return drawable;
    }
}
