package com.example.carriera.applications.screens;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.ApplicationDraft;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.applications.ui.GenerationViews;

public final class GenerateApplicationScreen {
    private GenerateApplicationScreen() {
    }

    public static View create(Activity activity, ApplicationDraft draft, ApplicationNavigator navigator) {
        LinearLayout content = GenerationViews.content(activity);
        content.addView(GenerationViews.jobCard(activity, draft), AppViews.lp(activity, -1, -2, 0, 0, 0, 24));

        content.addView(GenerationViews.sectionLabel(activity, "Documents to generate:"), AppViews.lp(activity, -1, -2, 10, 0, 10, 12));
        boolean[] includeCv = {draft.includeTailoredCv};
        boolean[] includeLetter = {draft.includeCoverLetter};
        LinearLayout documentRows = AppViews.row(activity);
        documentRows.setGravity(Gravity.CENTER_VERTICAL);
        documentRows.addView(GenerationViews.checkRow(activity, "Tailored CV", includeCv), new LinearLayout.LayoutParams(0, AppViews.dp(activity, 30), 1));
        documentRows.addView(GenerationViews.checkRow(activity, "Cover letter", includeLetter), new LinearLayout.LayoutParams(0, AppViews.dp(activity, 30), 1));
        content.addView(documentRows, AppViews.lp(activity, -1, 30, 10, 0, 10, 28));

        content.addView(GenerationViews.sectionLabel(activity, "Focus areas:"), AppViews.lp(activity, -1, -2, 10, 0, 10, 12));
        content.addView(GenerationViews.focusChips(activity, draft), AppViews.lp(activity, -1, 24, 10, 0, 10, 28));

        content.addView(GenerationViews.sectionLabel(activity, "Additional note:"), AppViews.lp(activity, -1, -2, 10, 0, 10, 12));
        EditText note = GenerationViews.noteInput(activity, draft.additionalNote);
        content.addView(note, AppViews.lp(activity, -1, 58, 10, 0, 10, 0));

        content.addView(GenerationViews.flexibleSpacer(activity, 26));

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);
        Button generate = GenerationViews.button(activity, "Generate documents");
        generate.setOnClickListener(v -> navigator.generateDocuments(
                includeCv[0],
                includeLetter[0],
                note.getText().toString()
        ));
        actions.addView(generate, new LinearLayout.LayoutParams(AppViews.dp(activity, 164), AppViews.dp(activity, 34)));
        content.addView(actions, AppViews.lp(activity, -1, 34, 0, 8, 0, 8));

        return GenerationViews.screen(activity, "Application", GenerationViews::scrollHome, content);
    }
}
