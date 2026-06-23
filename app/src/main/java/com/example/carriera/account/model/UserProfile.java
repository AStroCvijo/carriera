package com.example.carriera.account.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Single in-memory user profile for the prototype. All the data the onboarding
 * flow and the profile editor collect lives here.
 */
public class UserProfile {
    // Account / register
    public String firstName = "";
    public String lastName = "";
    public String email = "";
    public String password = "";

    // Basic info
    public String location = "";
    public String dateOfBirth = "";
    public boolean profilePictureUploaded = false;
    public String shortBio = "";
    public String linkedinUrl = "";
    public String githubUrl = "";
    public String portfolioWebsite = "";

    // Education / experience
    public final List<Education> education = new ArrayList<>();
    public final List<Experience> experience = new ArrayList<>();

    // Skills
    public final List<String> technicalSkills = new ArrayList<>();
    public final List<String> softSkills = new ArrayList<>();

    // Job preferences
    public String desiredPositions = "";
    public String preferredEmploymentType = "";
    public String preferredLocation = "";
    public String availability = "";
    public String fieldOfStudy = "";
    public String salaryMin = "";
    public String salaryMax = "";
    public String currency = "";
    public String industryOfInterest = "";

    // CV
    public boolean cvUploaded = false;
    public String cvFileName = "";
    public String cvUri = "";

    public String fullName() {
        return (firstName + " " + lastName).trim();
    }

    public Education firstEducation() {
        return education.isEmpty() ? new Education() : education.get(0);
    }

    public Experience firstExperience() {
        return experience.isEmpty() ? new Experience() : experience.get(0);
    }
}
