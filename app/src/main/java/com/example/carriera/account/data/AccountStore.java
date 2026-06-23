package com.example.carriera.account.data;

import com.example.carriera.account.model.Education;
import com.example.carriera.account.model.Experience;
import com.example.carriera.account.model.UserProfile;

/**
 * In-memory account store for the prototype. Keeps one registered user and the
 * current login state. No real persistence or authentication.
 */
public class AccountStore {
    private final UserProfile profile = new UserProfile();
    private boolean loggedIn = false;

    public static AccountStore createDemoStore() {
        AccountStore store = new AccountStore();
        UserProfile profile = store.profile;
        profile.firstName = "John";
        profile.lastName = "Smith";
        profile.email = "example@gmail.com";
        profile.password = "password";

        profile.location = "Novi Sad, Serbia";
        profile.dateOfBirth = "12 May 2003";
        profile.profilePictureUploaded = true;
        profile.shortBio = "Final year IT student looking for a first internship.";
        profile.linkedinUrl = "linkedin.com/in/johnsmith";
        profile.githubUrl = "github.com/johnsmith";
        profile.portfolioWebsite = "johnsmith.dev";

        Education education = new Education();
        education.school = "Faculty of Technical Sciences";
        education.degree = "Bachelor";
        education.fieldOfStudy = "Software Engineering";
        education.startDate = "2021";
        education.endDate = "2025";
        education.gpa = "8.7";
        education.currentlyEnrolled = true;
        profile.education.add(education);

        Experience experience = new Experience();
        experience.company = "TechStart";
        experience.position = "Frontend Intern";
        experience.employmentType = "Internship";
        experience.location = "Remote";
        experience.remote = true;
        experience.startDate = "2024";
        experience.endDate = "2024";
        experience.description = "Built UI components in React.";
        experience.skills.add("C/C++");
        profile.experience.add(experience);

        profile.technicalSkills.add("C/C++");
        profile.softSkills.add("problem solving");

        profile.desiredPositions = "Junior Java Developer";
        profile.preferredEmploymentType = "Internship";
        profile.preferredLocation = "Novi Sad";
        profile.availability = "Immediately";
        profile.fieldOfStudy = "Software Engineering";
        profile.industryOfInterest = "IT";

        profile.cvUploaded = true;
        profile.cvFileName = "john_smith_cv.pdf";
        return store;
    }

    public UserProfile profile() {
        return profile;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean login(String email, String password) {
        boolean match = email != null
                && password != null
                && email.trim().equalsIgnoreCase(profile.email)
                && password.equals(profile.password);
        if (match) {
            loggedIn = true;
        }
        return match;
    }

    public void register(String firstName, String lastName, String email, String password) {
        profile.firstName = firstName.trim();
        profile.lastName = lastName.trim();
        profile.email = email.trim();
        profile.password = password;
        loggedIn = true;
    }

    public void logout() {
        loggedIn = false;
    }
}
