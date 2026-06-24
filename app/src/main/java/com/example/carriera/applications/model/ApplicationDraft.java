package com.example.carriera.applications.model;

import com.example.carriera.jobs.model.RecommendedJob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationDraft {
    public final String applicationId;
    public final String position;
    public final String company;
    public final int matchPercent;
    public final String deadline;
    public final List<String> focusAreas;
    public boolean includeTailoredCv;
    public boolean includeCoverLetter;
    public String additionalNote;
    public String generatedCv;
    public String generatedCoverLetter;
    public boolean submitted;

    public ApplicationDraft(
            String applicationId,
            String position,
            String company,
            int matchPercent,
            String deadline,
            List<String> focusAreas,
            boolean includeTailoredCv,
            boolean includeCoverLetter,
            String additionalNote
    ) {
        this.applicationId = applicationId;
        this.position = position;
        this.company = company;
        this.matchPercent = matchPercent;
        this.deadline = deadline;
        this.focusAreas = focusAreas;
        this.includeTailoredCv = includeTailoredCv;
        this.includeCoverLetter = includeCoverLetter;
        this.additionalNote = additionalNote;
        this.generatedCv = "";
        this.generatedCoverLetter = "";
        this.submitted = false;
    }

    public static ApplicationDraft demo() {
        return new ApplicationDraft(
                "java-levi9",
                "Junior Java Developer",
                "Levi9",
                87,
                "25.05.2026.",
                Arrays.asList("React", "Teamwork", "Student projects"),
                true,
                true,
                "Emphasize my React project and teamwork experience."
        );
    }

    public static ApplicationDraft fromRecommendedJob(RecommendedJob job) {
        return new ApplicationDraft(
                job.id,
                job.title,
                job.company,
                job.matchPercent,
                job.deadline,
                focusAreasFrom(job),
                true,
                true,
                "Emphasize my React project and teamwork experience."
        );
    }

    private static List<String> focusAreasFrom(RecommendedJob job) {
        List<String> areas = new ArrayList<>();
        addFocusAreas(areas, job.skillsYouHave);
        if (areas.isEmpty()) {
            addFocusAreas(areas, job.requiredSkills);
        }
        if (areas.isEmpty()) {
            areas.add("Java");
            areas.add("Teamwork");
            areas.add("Student projects");
        }
        return areas;
    }

    private static void addFocusAreas(List<String> areas, String[] values) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                continue;
            }
            areas.add(value.trim());
            if (areas.size() == 3) {
                return;
            }
        }
    }

    public void generateSelectedDocuments() {
        if (includeTailoredCv) {
            generatedCv = buildCv();
        }
        if (includeCoverLetter) {
            generatedCoverLetter = buildCoverLetter();
        }
    }

    public void updateCv() {
        generatedCv = buildCv();
    }

    public void updateCoverLetter() {
        generatedCoverLetter = buildCoverLetter();
    }

    public boolean selectedDocumentsHaveContent() {
        return (includeTailoredCv || includeCoverLetter)
                && (!includeTailoredCv || hasText(generatedCv))
                && (!includeCoverLetter || hasText(generatedCoverLetter));
    }

    public String selectedDocumentsText() {
        if (includeTailoredCv && includeCoverLetter) {
            return "Your tailored CV and cover letter are ready for review.";
        }
        if (includeTailoredCv) {
            return "Your tailored CV is ready for review.";
        }
        if (includeCoverLetter) {
            return "Your cover letter is ready for review.";
        }
        return "Choose a document to generate before submitting.";
    }

    public String submittedAppliedDate() {
        return "21.05.2026.";
    }

    public String submittedNextStep() {
        return "Wait for company response";
    }

    private String buildCv() {
        return "Marko Petrovi\u0107 - " + position + "\n\n"
                + "Final year IT student with basic experience in Java, SQL and team projects.\n\n"
                + "Relevant skills\n"
                + skillsText() + "\n\n"
                + "Student project: Web application for tracking job applications.\n\n"
                + "Target company: " + company + "\n\n"
                + "Faculty of Technical Sciences, Information Technologies";
    }

    private String buildCoverLetter() {
        String note = cleanNote();
        String profileSentence;
        if (note.isEmpty()) {
            profileSentence = "My profile matches this role through my experience with Java, SQL and student projects.";
        } else {
            profileSentence = "My profile matches this role through my experience with Java, SQL, teamwork and the project focus you requested: "
                    + note;
        }
        return "Dear hiring team,\n\n"
                + "I am applying for the " + position + " position at " + company + ".\n\n"
                + profileSentence + "\n\n"
                + "I am interested in contributing to your team and continuing to develop as a junior engineer.\n\n"
                + "Sincerely,\n"
                + "Marko Petrovi\u0107";
    }

    private String skillsText() {
        if (focusAreas == null || focusAreas.isEmpty()) {
            return "Java, SQL, Git, OOP, Teamwork";
        }
        StringBuilder builder = new StringBuilder("Java, SQL, Git, OOP");
        for (String area : focusAreas) {
            if (area == null || area.trim().isEmpty()) {
                continue;
            }
            String trimmed = area.trim();
            if (builder.toString().contains(trimmed)) {
                continue;
            }
            builder.append(", ").append(trimmed);
        }
        return builder.toString();
    }

    private String cleanNote() {
        return additionalNote == null ? "" : additionalNote.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
