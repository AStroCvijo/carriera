package com.example.carriera.account.model;

import java.util.ArrayList;
import java.util.List;

public class Experience {
    public String company = "";
    public String position = "";
    public String employmentType = "";
    public String location = "";
    public boolean remote = false;
    public String startDate = "";
    public String endDate = "";
    public boolean currentlyWorking = false;
    public String description = "";
    public final List<String> skills = new ArrayList<>();

    public boolean isEmpty() {
        return company.trim().isEmpty()
                && position.trim().isEmpty();
    }

    public String summary() {
        StringBuilder builder = new StringBuilder();
        if (!position.trim().isEmpty()) {
            builder.append(position);
        }
        if (!company.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append(" @ ");
            }
            builder.append(company);
        }
        return builder.toString();
    }
}
