package com.example.carriera.account.model;

public class Education {
    public String school = "";
    public String degree = "";
    public String fieldOfStudy = "";
    public String startDate = "";
    public String endDate = "";
    public String gpa = "";
    public boolean currentlyEnrolled = false;

    public boolean isEmpty() {
        return school.trim().isEmpty()
                && degree.trim().isEmpty()
                && fieldOfStudy.trim().isEmpty();
    }

    public String summary() {
        StringBuilder builder = new StringBuilder();
        if (!degree.trim().isEmpty()) {
            builder.append(degree);
        }
        if (!fieldOfStudy.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(fieldOfStudy);
        }
        if (!school.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(school);
        }
        return builder.toString();
    }
}
