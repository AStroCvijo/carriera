package com.example.carriera.applications.screens;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.carriera.applications.model.ApplicationDraft;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppViews;
import com.example.carriera.applications.ui.GenerationViews;

public final class CvPreviewScreen {
    private CvPreviewScreen() {
    }

    public static View create(Activity activity, ApplicationDraft draft, ApplicationNavigator navigator, boolean showValidationWarning) {
        LinearLayout content = GenerationViews.content(activity);
        content.addView(GenerationViews.jobCard(activity, draft), AppViews.lp(activity, -1, -2, 0, 0, 0, 24));
        content.addView(GenerationViews.sectionLabel(activity, "Generated CV:"), AppViews.lp(activity, -1, -2, 10, 0, 10, 10));

        EditText preview = GenerationViews.editablePreviewBox(activity, draft.generatedCv, 9, 8);
        preview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                draft.generatedCv = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        content.addView(preview, AppViews.lp(activity, -1, 190, 10, 0, 10, 0));

        content.addView(GenerationViews.flexibleSpacer(activity, 24));

        if (showValidationWarning) {
            content.addView(GenerationViews.errorBanner(activity), AppViews.lp(activity, -1, -2, 12, 0, 12, 12));
        }

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);
        Button update = GenerationViews.button(activity, "Update CV");
        update.setOnClickListener(v -> navigator.updateGeneratedCv(preview.getText().toString()));
        actions.addView(update, new LinearLayout.LayoutParams(AppViews.dp(activity, 96), AppViews.dp(activity, 34)));
        content.addView(actions, AppViews.lp(activity, -1, 34, 0, 0, 0, 8));

        return GenerationViews.screen(activity, "CV preview", navigator::showDocumentsGenerated, content);
    }
}
