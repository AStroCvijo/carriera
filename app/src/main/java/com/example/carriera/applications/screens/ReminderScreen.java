package com.example.carriera.applications.screens;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carriera.applications.model.TrackedApplication;
import com.example.carriera.applications.navigation.ApplicationNavigator;
import com.example.carriera.applications.ui.AppTheme;
import com.example.carriera.applications.ui.AppViews;

import java.util.Calendar;
import java.util.Locale;

public final class ReminderScreen {
    private static final String NOTE_PLACEHOLDER = "Type your note here...";
    private static final String[] REMINDER_TYPES = {
            "Follow-up email",
            "Interview reminder",
            "Deadline reminder"
    };

    private ReminderScreen() {
    }

    public static View create(Activity activity, TrackedApplication application, ApplicationNavigator navigator) {
        LinearLayout content = AppViews.column(activity);
        content.addView(AppViews.detailCard(activity, "Application", application.position + "\n" + application.company),
                AppViews.lp(activity, -1, -2, 0, 0, 0, 28));

        content.addView(AppViews.formLabel(activity, "Reminder Type"), AppViews.lp(activity, -1, -2, 0, 0, 0, 18));
        Spinner type = reminderTypeSpinner(activity, null);
        content.addView(type, AppViews.lp(activity, -1, 42, 108, 0, 108, 28));

        content.addView(AppViews.formLabel(activity, "Date and Time"), AppViews.lp(activity, -1, -2, 0, 0, 0, 18));
        EditText dateTime = dateTimeInput(activity, defaultFutureDateTime());
        content.addView(dateTime, AppViews.lp(activity, -1, 42, 108, 0, 108, 28));

        EditText note = AppViews.input(activity, NOTE_PLACEHOLDER, 4);
        note.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && NOTE_PLACEHOLDER.contentEquals(note.getText())) {
                note.setText("");
            }
        });
        content.addView(note, AppViews.lp(activity, -1, -2, 0, 0, 0, 0));

        TextView invalidDate = AppViews.warning(activity, "Invalid date and time");
        invalidDate.setVisibility(isPastDateTime(dateTime.getText().toString()) ? View.VISIBLE : View.GONE);
        dateTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invalidDate.setVisibility(isPastDateTime(s.toString()) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        LinearLayout actions = AppViews.row(activity);
        actions.setGravity(Gravity.CENTER);

        Button add = AppViews.bottomButton(activity, "Add");
        add.setOnClickListener(v -> {
            if (isPastDateTime(dateTime.getText().toString())) {
                invalidDate.setVisibility(View.VISIBLE);
                return;
            }
            navigator.saveReminder(
                    application.id,
                    type.getSelectedItem().toString(),
                    dateTime.getText().toString(),
                    NOTE_PLACEHOLDER.contentEquals(note.getText()) ? "" : note.getText().toString()
            );
        });
        actions.addView(add, new LinearLayout.LayoutParams(AppViews.dp(activity, 132), AppViews.dp(activity, 46)));

        LinearLayout bottom = AppViews.column(activity);
        bottom.addView(invalidDate, AppViews.lp(activity, -1, 32, 0, 0, 0, 10));
        bottom.addView(actions, AppViews.lp(activity, -1, 46, 0, 0, 0, 0));

        return AppViews.screenWithBottomActions(activity, "Add Reminder", () -> navigator.showApplicationDetails(application.id), content, bottom);
    }

    private static Spinner reminderTypeSpinner(Activity activity, String selectedType) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, REMINDER_TYPES) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                styleSpinnerText(activity, view);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                styleSpinnerText(activity, view);
                view.setBackgroundColor(AppTheme.PILL);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = new Spinner(activity);
        spinner.setAdapter(adapter);
        spinner.setPadding(AppViews.dp(activity, 14), 0, AppViews.dp(activity, 38), 0);
        spinner.setBackgroundResource(com.example.carriera.R.drawable.bg_status_dropdown);
        int selectedIndex = 0;
        if (selectedType != null) {
            for (int i = 0; i < REMINDER_TYPES.length; i++) {
                if (REMINDER_TYPES[i].equals(selectedType)) {
                    selectedIndex = i;
                    break;
                }
            }
        }
        spinner.setSelection(selectedIndex);
        return spinner;
    }

    private static EditText dateTimeInput(Activity activity, String initialValue) {
        EditText input = AppViews.input(activity, initialValue, 1);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_NULL);
        input.setFocusable(false);
        input.setClickable(true);
        input.setGravity(Gravity.CENTER);
        input.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/inter_bold.ttf"));
        input.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.example.carriera.R.drawable.ic_calendar, 0);
        input.setCompoundDrawablePadding(AppViews.dp(activity, 8));
        input.setPadding(AppViews.dp(activity, 10), 0, AppViews.dp(activity, 10), 0);
        input.setOnClickListener(v -> openDateTimePicker(activity, input));
        input.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openDateTimePicker(activity, input);
                return true;
            }
            return true;
        });
        return input;
    }

    private static void openDateTimePicker(Activity activity, EditText target) {
        Calendar calendar = parseDateTime(target.getText().toString());
        DatePickerDialog dateDialog = new DatePickerDialog(activity, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timeDialog = new TimePickerDialog(activity, (timeView, hourOfDay, minute) -> {
                target.setText(String.format(Locale.US, "%02d.%02d.%04d, %02d:%02d",
                        dayOfMonth,
                        month + 1,
                        year,
                        hourOfDay,
                        minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timeDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    private static Calendar parseDateTime(String value) {
        Calendar calendar = Calendar.getInstance();
        try {
            String[] parts = value.split(",");
            String[] date = parts[0].trim().split("\\.");
            String[] time = parts.length > 1 ? parts[1].trim().split(":") : new String[]{"10", "00"};
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(date[2]));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        } catch (RuntimeException ignored) {
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 0);
        }
        return calendar;
    }

    private static String defaultFutureDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return String.format(Locale.US, "%02d.%02d.%04d, %02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
    }

    private static boolean isPastDateTime(String value) {
        Calendar selected = parseDateTime(value);
        Calendar now = Calendar.getInstance();
        selected.set(Calendar.SECOND, 0);
        selected.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return selected.before(now);
    }

    private static void styleSpinnerText(Activity activity, TextView view) {
        view.setTextSize(12);
        view.setTextColor(AppTheme.TEXT);
        view.setSingleLine(true);
        view.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/inter_bold.ttf"));
    }
}
