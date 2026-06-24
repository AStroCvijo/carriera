package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.applications.model.ApplicationDraft;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.applications.ui.GenerationViews;

public final class DocumentsGeneratedScreen {
    private DocumentsGeneratedScreen() {
    }

    public static View create(
            Activity activity,
            ApplicationDraft draft,
            ApplicationNavigator navigator,
            boolean showValidationWarning
    ) {
        LinearLayout content = GenerationViews.content(activity);
        content.addView(GenerationViews.jobCard(activity, draft), AppViews.lp(activity, -1, -2, 0, 0, 0, 28));

        LinearLayout messageGroup = AppViews.column(activity);
        TextView success = GenerationViews.body(activity, "Documents generated successfully.", 15, true);
        success.setGravity(Gravity.CENTER);
        messageGroup.addView(success, AppViews.lp(activity, -1, -2, 8, 0, 8, 12));

        TextView ready = GenerationViews.body(activity, draft.selectedDocumentsText(), 13, true);
        ready.setGravity(Gravity.CENTER);
        messageGroup.addView(ready, AppViews.lp(activity, -1, -2, 22, 0, 22, 0));
        content.addView(messageGroup, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        content.addView(GenerationViews.flexibleSpacer(activity, 34));

        if (showValidationWarning) {
            content.addView(GenerationViews.errorBanner(activity), AppViews.lp(activity, -1, -2, 12, 0, 12, 12));
        }

        TextView note = GenerationViews.body(activity, "*you can review each document before saving the application", 11, false);
        note.setGravity(Gravity.CENTER);
        content.addView(note, AppViews.lp(activity, -1, -2, 8, 0, 8, 12));

        if (draft.includeTailoredCv && draft.includeCoverLetter) {
            LinearLayout reviewButtons = AppViews.row(activity);
            reviewButtons.setGravity(Gravity.CENTER);
            Button cv = GenerationViews.secondaryButton(activity, "View CV");
            cv.setOnClickListener(v -> navigator.showCvPreview());
            reviewButtons.addView(cv, new LinearLayout.LayoutParams(AppViews.dp(activity, 112), AppViews.dp(activity, 42)));
            reviewButtons.addView(GenerationViews.gap(activity, 12), new LinearLayout.LayoutParams(AppViews.dp(activity, 12), 1));
            Button letter = GenerationViews.secondaryButton(activity, "View cover letter");
            letter.setOnClickListener(v -> navigator.showCoverLetterPreview());
            reviewButtons.addView(letter, new LinearLayout.LayoutParams(AppViews.dp(activity, 152), AppViews.dp(activity, 42)));
            content.addView(reviewButtons, AppViews.lp(activity, -1, 42, 0, 0, 0, 16));
        } else if (draft.includeTailoredCv) {
            content.addView(secondaryButtonRow(activity, "View CV", navigator::showCvPreview, 112), AppViews.lp(activity, -1, 42, 0, 0, 0, 16));
        } else if (draft.includeCoverLetter) {
            content.addView(secondaryButtonRow(activity, "View cover letter", navigator::showCoverLetterPreview, 152), AppViews.lp(activity, -1, 42, 0, 0, 0, 16));
        }

        content.addView(primaryButtonRow(activity, "Submit application", navigator::submitGeneratedApplication, 190), AppViews.lp(activity, -1, 46, 0, 0, 0, 10));
        return GenerationViews.screen(activity, "Application", navigator::showGenerateApplication, content);
    }

    private static LinearLayout primaryButtonRow(Activity activity, String text, Runnable action, int width) {
        return buttonRow(activity, text, action, width, 46, true);
    }

    private static LinearLayout secondaryButtonRow(Activity activity, String text, Runnable action, int width) {
        return buttonRow(activity, text, action, width, 42, false);
    }

    private static LinearLayout buttonRow(Activity activity, String text, Runnable action, int width, int height, boolean primary) {
        LinearLayout row = AppViews.row(activity);
        row.setGravity(Gravity.CENTER);
        Button button = primary ? GenerationViews.primaryButton(activity, text) : GenerationViews.secondaryButton(activity, text);
        button.setOnClickListener(v -> action.run());
        row.addView(button, new LinearLayout.LayoutParams(AppViews.dp(activity, width), AppViews.dp(activity, height)));
        return row;
    }
}
