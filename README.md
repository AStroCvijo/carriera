# Carriera

Android prototip za HCI projekat Carriera.

Ova verzija implementira samo dio clana 4 iz lo-fi podjele: pracenje prijava, statusi, podsjetnici i priprema za intervju.

## Sta prototip pokazuje

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

- `MainActivity` samo pokrece modul i radi navigaciju
- `applications/model` sadrzi podatke i statuse prijava
- `applications/data` sadrzi demo prijave
- `applications/screens` sadrzi ekrane iz Figma toka
- `applications/ui` sadrzi boje i helper-e za izgled
