package com.example.carriera.jobs.screens;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.jobs.model.RecommendedJob;

final class JobUi {
    private JobUi() {
    }

    static TextView text(Activity activity, String value, int sp, boolean bold) {
        TextView view = new TextView(activity);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(AppTheme.TEXT);
        view.setTypeface(Typeface.createFromAsset(activity.getAssets(), bold ? "fonts/inter_bold.ttf" : "fonts/inter_regular.ttf"));
        return view;
    }

    static TextView sectionTitle(Activity activity, String value) {
        return text(activity, value, 15, true);
    }

    static TextView matchBadge(Activity activity, int percent) {
        TextView badge = text(activity, percent + "%\nmatch", 14, true);
        badge.setGravity(Gravity.CENTER);
        badge.setBackground(AppViews.roundedBackground(activity, AppTheme.CARD, 18));
        return badge;
    }

    static TextView smallPill(Activity activity, String value) {
        TextView pill = text(activity, value, 8, true);
        pill.setGravity(Gravity.CENTER);
        pill.setBackground(AppViews.roundedBackground(activity, AppTheme.BACKGROUND, 10));
        return pill;
    }

    static TextView metaLine(Activity activity, String icon, String value) {
        String prefix = "date".equals(icon) ? "[ ]  " : "bag".equals(icon) ? "[]  " : "o  ";
        return text(activity, prefix + value, 11, true);
    }

    static LinearLayout twoColumnMeta(Activity activity, RecommendedJob job) {
        LinearLayout root = AppViews.column(activity);
        LinearLayout first = AppViews.row(activity);
        first.addView(metaLine(activity, "pin", job.location), new LinearLayout.LayoutParams(0, -2, 1));
        first.addView(metaLine(activity, "date", "Deadline: " + job.deadline), new LinearLayout.LayoutParams(0, -2, 1));
        root.addView(first);
        LinearLayout second = AppViews.row(activity);
        second.addView(metaLine(activity, "bag", job.workMode + "\n" + job.employmentType), new LinearLayout.LayoutParams(0, -2, 1));
        second.addView(text(activity, "Status: Available", 11, true), new LinearLayout.LayoutParams(0, -2, 1));
        root.addView(second, AppViews.lp(activity, -1, -2, 0, 6, 0, 0));
        return root;
    }

    static View divider(Activity activity) {
        View divider = new View(activity);
        divider.setBackgroundColor(AppTheme.PILL);
        return divider;
    }

    static LinearLayout chipWrap(Activity activity, String[] chips) {
        LinearLayout rows = AppViews.column(activity);
        LinearLayout row = null;
        for (int i = 0; i < chips.length; i++) {
            if (i % 4 == 0) {
                row = AppViews.row(activity);
                rows.addView(row, AppViews.lp(activity, -1, 30, 0, i == 0 ? 0 : 8, 0, 0));
            }
            TextView chip = AppViews.chip(activity, chips[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, AppViews.dp(activity, 28), 1);
            params.setMargins(AppViews.dp(activity, 4), 0, AppViews.dp(activity, 4), 0);
            row.addView(chip, params);
        }
        return rows;
    }

    static LinearLayout skillPanel(Activity activity, String[] skills) {
        LinearLayout panel = AppViews.column(activity);
        panel.setPadding(AppViews.dp(activity, 16), AppViews.dp(activity, 10), AppViews.dp(activity, 16), AppViews.dp(activity, 10));
        panel.setBackground(AppViews.roundedBackground(activity, AppTheme.CARD, 16));
        for (int i = 0; i < skills.length; i += 2) {
            LinearLayout row = AppViews.row(activity);
            row.addView(text(activity, skills[i], 12, false), new LinearLayout.LayoutParams(0, -2, 1));
            if (i + 1 < skills.length) {
                row.addView(text(activity, skills[i + 1], 12, false), new LinearLayout.LayoutParams(0, -2, 1));
            }
            panel.addView(row, AppViews.lp(activity, -1, -2, 0, i == 0 ? 0 : 8, 0, 0));
        }
        return panel;
    }

    static TextView warningBar(Activity activity, String value) {
        TextView warning = text(activity, "!        " + value, 14, true);
        warning.setTextColor(Color.WHITE);
        warning.setGravity(Gravity.CENTER);
        warning.setBackground(AppViews.roundedBackground(activity, AppTheme.WARNING, 8));
        return warning;
    }

    static View warningGraphic(Activity activity) {
        WarningGraphic view = new WarningGraphic(activity);
        view.setBackground(AppViews.roundedBackground(activity, AppTheme.WARNING, 12));
        return view;
    }

    static GradientDrawable outline(Activity activity, int color, int stroke, int radius) {
        GradientDrawable drawable = AppViews.roundedBackground(activity, color, radius);
        drawable.setStroke(AppViews.dp(activity, 1), stroke);
        return drawable;
    }

    static final class WarningGraphic extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        WarningGraphic(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float w = getWidth();
            float h = getHeight();
            paint.setColor(AppTheme.BACKGROUND);
            paint.setStyle(Paint.Style.FILL);
            android.graphics.Path path = new android.graphics.Path();
            path.moveTo(w / 2f, h * 0.18f);
            path.lineTo(w * 0.18f, h * 0.82f);
            path.lineTo(w * 0.82f, h * 0.82f);
            path.close();
            canvas.drawPath(path, paint);

            paint.setColor(AppTheme.WARNING);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(h * 0.42f);
            canvas.drawText("!", w / 2f, h * 0.68f, paint);
        }
    }

    static final class MatchRing extends View {
        private final int percent;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        MatchRing(Context context, int percent) {
            super(context);
            this.percent = percent;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int size = Math.min(getWidth(), getHeight());
            float stroke = size * 0.09f;
            RectF oval = new RectF(stroke, stroke, size - stroke, size - stroke);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(stroke);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(AppTheme.PILL);
            canvas.drawArc(oval, -90, 360, false, paint);
            paint.setColor(AppTheme.HEADER);
            canvas.drawArc(oval, -90, 360f * percent / 100f, false, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(AppTheme.TEXT);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(size * 0.24f);
            canvas.drawText(percent + "%", size / 2f, size * 0.48f, paint);
            paint.setTextSize(size * 0.11f);
            canvas.drawText("MATCH", size / 2f, size * 0.64f, paint);
        }
    }
}
