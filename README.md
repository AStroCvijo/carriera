# Carriera

Android prototip za HCI projekat Carriera.

Prototip pokriva dva dijela iz Figma hi-fi dizajna:
1. nalog i profil korisnika (home, login, register, onboarding, profil) - unos i editovanje korisnickih podataka
2. pracenje prijava, statusi, podsjetnici i priprema za intervju

## Sta prototip pokazuje

Nalog i profil:
- home ekran (odjavljen) sa Login/Register
- login (sa stanjem greske) i register (sa validacijom i stanjem greske)
- onboarding u 5 koraka: Basic info, Education, Experience, Skills, Job prefs (sa stanjima greske)
- CV manager (upload, ekstrakcija podataka, trenutni/novi CV)
- home ekran (prijavljen) sa pristupom modulima
- profil sa editovanjem svih sekcija (podaci se cuvaju u memoriji)
- demo nalog: example@gmail.com / password

Pracenje prijava:
- pracenje statusa prijave
- dodavanje podsjetnika
- priprema za intervju sa pitanjima i primjerom odgovora
- ekran detalja prijave
- filtere za prijave: All, Sent, Waiting, Interview

## Pokretanje

Otvori folder `carriera` u Android Studio i pokreni `app` konfiguraciju.

Iz terminala moze i:

```bash
./gradlew assembleDebug
```

APK se generise u:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Struktura

- `MainActivity` radi navigaciju i implementira oba navigatora
- `account/model` sadrzi UserProfile, Education, Experience
- `account/data` sadrzi AccountStore (demo nalog, login/register/logout)
- `account/screens` sadrzi home, login, register, onboarding, CV i profil ekrane
- `account/navigation` sadrzi AccountNavigator
- `applications/model` sadrzi podatke i statuse prijava
- `applications/data` sadrzi demo prijave
- `applications/screens` sadrzi ekrane iz Figma toka
- `applications/ui` sadrzi boje i helper-e za izgled (`AppViews`, `AppTheme`)
