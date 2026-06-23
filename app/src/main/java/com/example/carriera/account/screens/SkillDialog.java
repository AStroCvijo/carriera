package com.example.carriera.account.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;

import com.example.carriera.applications.ui.AppViews;

/** Small input dialog used to add a skill chip. */
final class SkillDialog {
    interface OnSkill {
        void onSkill(String skill);
    }

    private SkillDialog() {
    }

    static void show(Activity activity, String title, OnSkill callback) {
        EditText input = new EditText(activity);
        input.setHint("e.g. Java");
        int padding = AppViews.dp(activity, 16);
        input.setPadding(padding, padding, padding, padding);

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String value = input.getText().toString().trim();
                    if (!value.isEmpty()) {
                        callback.onSkill(value);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
