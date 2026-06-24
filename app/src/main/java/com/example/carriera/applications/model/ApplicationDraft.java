package com.example.carriera.applications.model;

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

    private String buildCv() {
        return "Marko Petrovi\u0107 - Junior Java Developer\n\n"
                + "Final year IT student with basic experience in Java, SQL and team projects.\n\n"
                + "Relevant skills\n"
                + "Java, SQL, Git, OOP, Teamwork, React\n\n"
                + "Student project: Web application for tracking job applications.\n\n"
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
                + "I am applying for the Junior Java Developer position at Levi9.\n\n"
                + profileSentence + "\n\n"
                + "I am interested in contributing to your team and continuing to develop as a junior engineer.\n\n"
                + "Sincerely,\n"
                + "Marko Petrovi\u0107";
    }

    private String cleanNote() {
        return additionalNote == null ? "" : additionalNote.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
