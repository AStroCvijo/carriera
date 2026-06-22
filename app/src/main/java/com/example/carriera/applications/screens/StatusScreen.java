package com.example.carriera.applications.screens;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carriera.applications.model.ApplicationStatus;
import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

public final class StatusScreen {
    private StatusScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.addView(AppViews.detailCard(activity, "Application", application.position + "\n" + application.company),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 36));

        TextView label = AppViews.statusLabel(activity, "Current Status");
        content.addView(label, AppViews.lp(activity, -1, -2, 0, 0, 0, 34));

        final ApplicationStatus[] selectedStatus = {application.status};
        Spinner statusSpinner = statusSpinner(activity, application.status);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus[0] = ApplicationStatus.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        content.addView(statusSpinner, AppViews.lp(activity, -1, 42, 64, 0, 64, 0));

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);

        Button save = AppViews.bottomButton(activity, "Save");
        save.setOnClickListener(v -> navigator.updateStatus(application.id, selectedStatus[0]));
        actions.addView(save, new LinearLayout.LayoutParams(AppViews.dp(activity, 132), AppViews.dp(activity, 46)));

        return AppViews.screenWithBottomActions(activity, "Status", () -> navigator.showApplicationDetails(application.id), content, actions);
    }

    private static Spinner statusSpinner(Activity activity, ApplicationStatus selected) {
        String[] labels = new String[ApplicationStatus.values().length];
        int selectedIndex = 0;
        for (int i = 0; i < ApplicationStatus.values().length; i++) {
            labels[i] = ApplicationStatus.values()[i].label;
            if (ApplicationStatus.values()[i] == selected) {
                selectedIndex = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, labels) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                styleText(activity, view);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                styleText(activity, view);
                view.setBackgroundColor(AppTheme.PILL);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = new Spinner(activity);
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedIndex);
        spinner.setPadding(AppViews.dp(activity, 14), 0, AppViews.dp(activity, 38), 0);
        spinner.setBackgroundResource(com.example.carriera.R.drawable.bg_status_dropdown);
        return spinner;
    }

    private static void styleText(Activity activity, TextView view) {
        view.setTextSize(15);
        view.setTextColor(AppTheme.TEXT);
        view.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/inter_bold.ttf"));
    }
}
