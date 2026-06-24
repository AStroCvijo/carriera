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

public final class ApplicationSavedScreen {
    private ApplicationSavedScreen() {
    }

    public static View create(Activity activity, ApplicationDraft draft, ApplicationNavigator navigator) {
        LinearLayout content = GenerationViews.content(activity);

        LinearLayout card = AppViews.column(activity);
        card.setPadding(AppViews.dp(activity, 18), AppViews.dp(activity, 14), AppViews.dp(activity, 18), AppViews.dp(activity, 12));
        card.setBackground(AppViews.roundedBackground(activity, android.graphics.Color.rgb(147, 149, 211), 16));
        TextView title = GenerationViews.body(activity, "Application saved successfully", 16, true);
        card.addView(title);
        card.addView(GenerationViews.body(activity,
                "Position: " + draft.position
                        + "\nCompany: " + draft.company
                        + "\nStatus: Sent"
                        + "\nDate: 21.05.2026."
                        + "\nNext step: Wait for company response",
                12,
                false), AppViews.lp(activity, -1, -2, 0, 10, 0, 0));
        content.addView(card, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        content.addView(GenerationViews.flexibleSpacer(activity, 46));

        content.addView(GenerationViews.sectionLabel(activity, "Submitted documents:"), AppViews.lp(activity, -1, -2, 10, 0, 10, 14));
        content.addView(GenerationViews.horizontalSubmittedDocuments(activity, draft), AppViews.lp(activity, -1, 32, 10, 0, 10, 0));

        content.addView(GenerationViews.flexibleSpacer(activity, 52));

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);
        Button openTracking = GenerationViews.button(activity, "Open application tracking");
        openTracking.setOnClickListener(v -> navigator.openGeneratedApplicationTracking());
        actions.addView(openTracking, new LinearLayout.LayoutParams(AppViews.dp(activity, 196), AppViews.dp(activity, 34)));
        content.addView(actions, AppViews.lp(activity, -1, 34, 0, 0, 0, 8));

        return GenerationViews.screen(activity, "Application", navigator::showDocumentsGenerated, content);
    }
}
