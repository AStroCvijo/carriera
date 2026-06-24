package com.example.carriera.jobs.model;

public class RecommendedJob {
    public final String id;
    public final String title;
    public final String company;
    public final String location;
    public final String workMode;
    public final String employmentType;
    public final String posted;
    public final String deadline;
    public final int matchPercent;
    public final String description;
    public final String[] requiredSkills;
    public final String[] skillsYouHave;
    public final String[] skillsToImprove;

    public RecommendedJob(
            String id,
            String title,
            String company,
            String location,
            String workMode,
            String employmentType,
            String posted,
            String deadline,
            int matchPercent,
            String description,
            String[] requiredSkills,
            String[] skillsYouHave,
            String[] skillsToImprove
    ) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.workMode = workMode;
        this.employmentType = employmentType;
        this.posted = posted;
        this.deadline = deadline;
        this.matchPercent = matchPercent;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.skillsYouHave = skillsYouHave;
        this.skillsToImprove = skillsToImprove;
    }
}
