package com.example.carriera.applications.screens;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carriera.applications.data.ApplicationStore;
import com.example.carriera.applications.model.ApplicationFilter;
import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

import java.util.List;

public final class ApplicationManagerScreen {
    private ApplicationManagerScreen() {
    }

    public static View create(Activity activity, ApplicationStore store, ApplicationFilter filter, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);

        EditText search = AppViews.searchInput(activity);
        content.addView(search, AppViews.lp(activity, -1, 50, 0, 0, 0, 18));

        LinearLayout filters = AppViews.row(activity);
        for (ApplicationFilter item : ApplicationFilter.values()) {
            Button button = AppViews.pill(activity, item.label, item == filter);
            button.setOnClickListener(v -> navigator.showApplicationManager(item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, AppViews.dp(activity, 32), 1);
            params.setMargins(AppViews.dp(activity, 6), 0, AppViews.dp(activity, 6), 0);
            filters.addView(button, params);
        }
        content.addView(filters, AppViews.lp(activity, -1, 38, 0, 2, 0, 20));

        content.addView(AppViews.label(activity, "Applications"));
        LinearLayout results = AppViews.column(activity);
        content.addView(results);
        renderResults(activity, results, store, filter, "", navigator);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                renderResults(activity, results, store, filter, s.toString(), navigator);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return AppViews.screen(activity, "App Manager", AppViews::goHome, content);
    }

    private static void renderResults(
            Activity activity,
            LinearLayout results,
            ApplicationStore store,
            ApplicationFilter filter,
            String query,
            ApplicationNavigator navigator
    ) {
        results.removeAllViews();
        List<TrackedApplication> applications = store.search(filter, query);
        if (applications.isEmpty()) {
            results.addView(AppViews.card(activity, "No results..."), AppViews.lp(activity, -1, 34, 0, 6, 0, 0));
            return;
        }

        for (TrackedApplication application : applications) {
            results.addView(applicationCard(activity, application, navigator), AppViews.lp(activity, -1, -2, 0, 6, 0, 12));
        }
    }

    private static View applicationCard(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        LinearLayout card = AppViews.column(activity);
        card.setPadding(AppViews.dp(activity, 14), AppViews.dp(activity, 10), AppViews.dp(activity, 14), AppViews.dp(activity, 10));
        card.setBackground(AppViews.roundedBackground(activity, AppTheme.CARD, 16));

        LinearLayout copy = AppViews.column(activity);
        copy.addView(AppViews.cardTitle(activity, application.position));
        copy.addView(AppViews.body(activity, application.company));
        card.addView(copy);

        TextView meta = AppViews.body(activity,
                "Status: " + application.status.label
                        + "\nDeadline: " + application.deadline
                        + "\nNext step: " + application.nextStep());
        meta.setGravity(Gravity.START);
        card.addView(meta, AppViews.lp(activity, -1, -2, 0, 18, 0, 0));

        LinearLayout bottom = AppViews.row(activity);
        TextView spacer = AppViews.body(activity, "");
        bottom.addView(spacer, new LinearLayout.LayoutParams(0, 1, 1));

        Button details = AppViews.detailsButton(activity);
        details.setOnClickListener(v -> navigator.showApplicationDetails(application.id));
        bottom.addView(details, new LinearLayout.LayoutParams(AppViews.dp(activity, 96), AppViews.dp(activity, 38)));
        card.addView(bottom, AppViews.lp(activity, -1, 38, 0, 0, 0, 0));
        return card;
    }
}
