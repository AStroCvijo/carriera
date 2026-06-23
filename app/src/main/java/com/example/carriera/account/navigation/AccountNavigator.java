package com.example.carriera.account.navigation;

/**
 * Navigation for the account / profile part of the app (the part designed in
 * Figma: home, login, register, onboarding and profile editing).
 */
public interface AccountNavigator {
    void showHome();

    void showLogin();

    void showLoginError();

    void showRegister();

    void doLogin(String email, String password);

    void doRegister(String firstName, String lastName, String email, String password, String confirmPassword);

    void showHomeLoggedIn();

    void logout();

    void showProfile();

    // Onboarding / edit flow. onboarding == true keeps the 5-step wizard going
    // (Next moves forward, Finish leads to the CV step then home). When false the
    // screen was opened from the profile for editing and saving returns to it.
    void showBasicInfo(boolean onboarding);

    void showEducation(boolean onboarding);

    void showExperience(boolean onboarding);

    void showSkills(boolean onboarding);

    void showJobPrefs(boolean onboarding);

    void showCvManager(boolean onboarding);

    void finishOnboarding();
}
